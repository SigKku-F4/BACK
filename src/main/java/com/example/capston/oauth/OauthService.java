package com.example.capston.oauth;

import com.example.capston.aspect.Log;
import com.example.capston.domain.user.entity.User;
import com.example.capston.domain.user.repository.UserRepository;
import com.example.capston.jwt.JwtProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OauthService {

    private final SocialOauth socialOauth;
    private final JwtProvider jwtProvider;

    private final UserRepository userRepository;

    private final RestTemplate restTemplate;

    @Log
    public GetSocialOAuthRes oAuthLogin(String code) throws JsonProcessingException {
        JSONParser jsonParser = new JSONParser();
        try {

            String url = "https://oauth2.googleapis.com/tokeninfo?id_token="+code;

            ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);

            String name;
            String email;

            JSONObject jsonObj = (JSONObject)jsonParser.parse(forEntity.getBody());

            name = jsonObj.get("name").toString();
            email = jsonObj.get("email").toString();

            String jwtToken = jwtProvider.createToken(email, List.of("ROLE_USER"));

            Optional<User> optionalUser = userRepository.findByEmail(email);


            if(optionalUser.isEmpty()){
                User user = User.tempCreate(email, name);
                userRepository.save(user);
                return new GetSocialOAuthRes(jwtToken, GoogleConfirm.NEW_USER, "/signup");
            }
            else{
                if(optionalUser.get().getConfirm()){
                    return new GetSocialOAuthRes(jwtToken, GoogleConfirm.CONFIRM_USER,"/user/{date}");
                }
                else{
                    return new GetSocialOAuthRes(jwtToken, GoogleConfirm.EXIST_USER,"/signup");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
