package com.example.RecipeProject.controller;

import com.example.RecipeProject.entity.Recipe;
import com.example.RecipeProject.service.RecipeService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/topFive")
    public ResponseEntity<Map<String, Object>> getTopRecipes(@RequestParam(defaultValue = "5") int limit) {

    List<Recipe> recipes = recipeService.getTopRecipes(limit);

    Map<String, Object> response = new HashMap<>();
    response.put("data", recipes);

    return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {

        Recipe saved = recipeService.createRecipe(recipe);
        return ResponseEntity.ok(saved);
    }
}