package com.kuzmin.BigFood.mapper;

import com.kuzmin.BigFood.dto.RecipeFormDto;
import com.kuzmin.BigFood.dto.RecipeIngredientDto;
import com.kuzmin.BigFood.model.Recipe;
import com.kuzmin.BigFood.model.RecipeIngredients;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeMapper {

    public static RecipeFormDto toForm(Recipe recipe) {

        RecipeFormDto form = new RecipeFormDto();

        form.setId(recipe.getId());
        form.setName(recipe.getName());
        form.setDescription(recipe.getDescription());
        form.setCookingTime(recipe.getCookingTime());
        form.setServing(recipe.getServing());

        if (recipe.getNationalCuisine() != null) {
            form.setNationalCuisineId(recipe.getNationalCuisine().getId());
        }

        // Dish types
        if (recipe.getDishTypes() != null) {
            form.setDishTypeIds(
                    recipe.getDishTypes().stream()
                            .map(rdt -> rdt.getDishType().getId())
                            .toList()
            );
        }

        // Ingredients
        if (recipe.getIngredients() != null) {
            List<RecipeIngredientDto> ingredients =
                    recipe.getIngredients().stream()
                            .map(RecipeMapper::toIngredientDto)
                            .collect(Collectors.toList());

            form.setIngredients(ingredients);
        }

        return form;
    }

    private static RecipeIngredientDto toIngredientDto(RecipeIngredients ri) {
        RecipeIngredientDto dto = new RecipeIngredientDto();

        dto.setIngredientId(ri.getIngredient().getId());
        dto.setUnitId(ri.getUnit().getId());
        dto.setAmount(ri.getAmount());

        return dto;
    }
}
