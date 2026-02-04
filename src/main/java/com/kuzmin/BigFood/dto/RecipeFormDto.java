package com.kuzmin.BigFood.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecipeFormDto {

    private Long id;
    private String name;
    private String description;
    private Integer cookingTime;
    private Integer serving;
    private Long nationalCuisineId;

    private List<Long> dishTypeIds;
    private List<RecipeIngredientDto> ingredients = new ArrayList<>();
    private List<CookingStepDto> cookingSteps = new ArrayList<>();

}
