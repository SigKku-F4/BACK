package com.example.capston.domain.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CapPageRequest {

    Integer page = 0;

    Integer size = 10;
}
