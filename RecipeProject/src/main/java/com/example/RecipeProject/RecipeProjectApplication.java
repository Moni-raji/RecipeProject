package com.example.RecipeProject;

import com.example.RecipeProject.service.RecipeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecipeProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipeProjectApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RecipeService recipeService) {
        return args -> {
            recipeService.loadRecipesFromJson();
        };
    }
}