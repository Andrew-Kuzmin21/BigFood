package com.kuzmin.BigFood.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RecipeIngredientDto {
    private Long ingredientId;
    private BigDecimal amount;
    private Long unitId;
}

