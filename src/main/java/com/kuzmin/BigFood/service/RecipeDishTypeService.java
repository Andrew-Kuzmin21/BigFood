package com.kuzmin.BigFood.service;

import com.kuzmin.BigFood.model.RecipeDishType;
import com.kuzmin.BigFood.repository.RecipeDishTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeDishTypeService {

    private final RecipeDishTypeRepository recipeDishTypeRepository;

    public RecipeDishTypeService(RecipeDishTypeRepository recipeDishTypeRepository) {
        this.recipeDishTypeRepository = recipeDishTypeRepository;
    }

    /**
     * Типы блюда
     */
//    public RecipeDishType getDishTypeIdsByRecipe(Long recipeId) {
//        return recipeDishTypeRepository.findById(recipeId).orElse(null);
//    }
    public List<Long> getDishTypeIdsByRecipe(Long recipeId) {
        return recipeDishTypeRepository.findByRecipeId(recipeId)
                .stream()
                .map(rdt -> rdt.getDishType().getId())
                .toList();
    }

    /**
     * Получить тип блюда по ID
     */
    public RecipeDishType getById(Long id) {
        return recipeDishTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Тип блюда не найден: " + id));
    }
}
