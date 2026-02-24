package com.kuzmin.BigFood.mapper;

import com.kuzmin.BigFood.dto.RecipeFormDto;
import com.kuzmin.BigFood.dto.RecipeIngredientDto;
import com.kuzmin.BigFood.dto.CookingStepDto;
import com.kuzmin.BigFood.dto.MediaDto;
import com.kuzmin.BigFood.model.CookingStep;
import com.kuzmin.BigFood.model.Recipe;
import com.kuzmin.BigFood.model.RecipeIngredients;

import java.util.Comparator;
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

        form.setExistingMedia(
                recipe.getMedia().stream()
                        .filter(m -> m.getCookingStep() == null)
                        .map(m -> {
                            MediaDto dto = new MediaDto();
                            dto.setId(m.getId());
                            dto.setUrl(m.getUrl());
                            dto.setMain(m.isMain());
                            return dto;
                        })
                        .toList()
        );


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

        // Cooking Steps
        form.setCookingSteps(
                recipe.getCookingSteps().stream()
                        .sorted(Comparator.comparing(CookingStep::getNumber))
                        .map(cs -> {
                            CookingStepDto dto = new CookingStepDto();
                            dto.setId(cs.getId());
                            dto.setTitle(cs.getTitle());
                            dto.setDescription(cs.getDescription());

                            dto.setExistingMedia(
                                    cs.getMedia().stream()
                                            .map(m -> {
                                                MediaDto md = new MediaDto();
                                                md.setId(m.getId());
                                                md.setUrl(m.getUrl());
                                                return md;
                                            })
                                            .toList()
                            );

                            return dto;
                        })
                        .toList()
        );

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
