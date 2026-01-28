package com.kuzmin.BigFood.service;

import com.kuzmin.BigFood.model.RecipeCategories;
import com.kuzmin.BigFood.repository.RecipeCategoriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeCategoriesService {

    private final RecipeCategoriesRepository recipeCategoriesRepository;

    public RecipeCategoriesService(RecipeCategoriesRepository recipeCategoriesRepository) {
        this.recipeCategoriesRepository = recipeCategoriesRepository;
    }

    public List<RecipeCategories> findByRecipeId(Long recipeId) {
        return recipeCategoriesRepository.findByRecipeId(recipeId);
    }

    public List<RecipeCategories> findByCategoryId(Long categoryId) {
        return recipeCategoriesRepository.findByCategoryId(categoryId);
    }

}
