package com.kuzmin.BigFood.service;

import com.kuzmin.BigFood.model.CookingStep;
import com.kuzmin.BigFood.repository.CookingStepRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CookingStepService {

    private final CookingStepRepository cookingStepRepository;

    public CookingStepService(CookingStepRepository cookingStepRepository) {
        this.cookingStepRepository = cookingStepRepository;
    }

    public List<CookingStep> getCookingStepsByRecipeId(Long recipeId) {
        return cookingStepRepository.findByRecipeIdOrderByCookingStepNumber(recipeId);
    }
}
