package com.example.capston.oauth;

import com.example.capston.aspect.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class OauthController {
    private final OauthService oauthService;

    @Log
    @PostMapping("/app/accounts/auth")
    public ResponseEntity<?> callback(@RequestBody IdToken idToken) throws JsonProcessingException {
        GetSocialOAuthRes getSocialOauthRes =oauthService.oAuthLogin(idToken.getId_token());
        return new ResponseEntity<>(getSocialOauthRes, HttpStatus.OK);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class IdToken{
        String id_token;
    }
}
