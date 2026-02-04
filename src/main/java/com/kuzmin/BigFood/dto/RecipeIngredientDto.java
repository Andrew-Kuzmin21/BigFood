package com.kuzmin.BigFood.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RecipeIngredientDto {
    private Long ingredientId;

    @NotNull(message = "Укажите количество")
    @Positive(message = "Количество должно быть больше 0")
    private BigDecimal amount;
    private Long unitId;
}

