package com.example.capston.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetSocialOAuthRes {

    private String jwtToken;
    private GoogleConfirm googleConfirm;
    private String redirectUrl;
}