package com.example.RecipeProject.service;
import com.example.RecipeProject.entity.Recipe;
import com.example.RecipeProject.repository.RecipeRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.util.*;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
    public List<Recipe> getTopRecipes(int limit) {

        Pageable pageable = PageRequest.of(0, limit);
        return recipeRepository.findAllByOrderByRatingDesc(pageable);
    }
    public Recipe createRecipe(Recipe recipe) {
    if (recipe.getTitle() == null || recipe.getTitle().isBlank()) {
        throw new RuntimeException("title is required");
    }
    if (recipe.getCuisine() == null || recipe.getCuisine().isBlank()) {
        throw new RuntimeException("cuisine is required");
    }
    if (recipe.getPrepTime() == null) {
        throw new RuntimeException("prepTime is required");
    }
    if (recipe.getCookTime() == null) {
        throw new RuntimeException("cookTime is required");
    }
    recipe.setTotalTime(recipe.getPrepTime() + recipe.getCookTime());

    return recipeRepository.save(recipe);
}

    public void loadRecipesFromJson() {

        try {
            ObjectMapper mapper = new ObjectMapper();

            InputStream inputStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream("US_recipes_null.json");

            JsonNode root = mapper.readTree(inputStream);
            Iterator<Map.Entry<String, JsonNode>> fields = root.fields();

            while (fields.hasNext()) {

                Map.Entry<String, JsonNode> entry = fields.next();
                JsonNode node = entry.getValue();

                Recipe recipe = new Recipe();

                recipe.setTitle(node.get("title").asText());
                recipe.setCuisine(node.get("cuisine").asText());
                recipe.setRating((float) node.get("rating").asDouble());
                recipe.setPrepTime(node.get("prep_time").asInt());
                recipe.setCookTime(node.get("cook_time").asInt());
                recipe.setTotalTime(node.get("total_time").asInt());
                recipe.setDescription(node.get("description").asText());
                recipe.setNutrients(node.get("nutrients").toString());

                recipe.setServes(node.get("serves").asText());

                recipeRepository.save(recipe);
            }

            System.out.println("Data Loaded Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
