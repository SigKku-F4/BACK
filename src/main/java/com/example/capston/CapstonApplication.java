package com.example.capston;

import com.example.capston.domain.foodList.entity.FoodList;
import com.example.capston.domain.foodList.repository.FoodListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CapstonApplication {

    public static void main(String[] args) {
        SpringApplication.run(CapstonApplication.class, args);
    }

}
