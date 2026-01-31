package com.kuzmin.BigFood.service;

import com.kuzmin.BigFood.model.DishType;
import com.kuzmin.BigFood.model.Recipe;
import com.kuzmin.BigFood.model.RecipeDishType;
import com.kuzmin.BigFood.model.User;
import com.kuzmin.BigFood.repository.DishTypeRepository;
import com.kuzmin.BigFood.repository.RecipeDishTypeRepository;
import com.kuzmin.BigFood.repository.RecipeRepository;
import com.kuzmin.BigFood.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final RecipeDishTypeRepository recipeDishTypeRepository;
    private final DishTypeRepository dishTypeRepository;

    public RecipeService(
            RecipeRepository recipeRepository,
            UserRepository userRepository,
            RecipeDishTypeRepository recipeDishTypeRepository,
            DishTypeRepository dishTypeRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.recipeDishTypeRepository = recipeDishTypeRepository;
        this.dishTypeRepository = dishTypeRepository;
    }

    /** * Получение рецептов с пагинацией */
    public Page<Recipe> getPage(int page, int size) {
        return recipeRepository.findAll(PageRequest.of(page, size));
    }

    /** * Получить рецепт по id */
    public Recipe getById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Неверный ID: " + id));
    }

    /** * Сохранить рецепт (create / update) */
    @Transactional
    public void save(
            Recipe recipe,
            List<Long> dishTypeIds,
            Authentication authentication
    ) {

        // 1. Автор (CREATE / UPDATE)
        if (recipe.getId() == null) {
            // CREATE
            User currentUser = getCurrentUser(authentication);
            recipe.setAuthor(currentUser);
        } else {
            // UPDATE — сохраняем старого автора
            Recipe existingRecipe = recipeRepository.findById(recipe.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));

            recipe.setAuthor(existingRecipe.getAuthor());
        }

        // 2. Сохраняем рецепт
        Recipe savedRecipe = recipeRepository.save(recipe);

        // 3. Обновляем типы блюда (через таблицу-связку)
        recipeDishTypeRepository.deleteByRecipe(savedRecipe);

        if (dishTypeIds != null && !dishTypeIds.isEmpty()) {
            for (Long typeId : dishTypeIds) {
                DishType type = dishTypeRepository.findById(typeId)
                        .orElseThrow(() -> new IllegalArgumentException("DishType not found: " + typeId));

                RecipeDishType link = new RecipeDishType()
                        .setRecipe(savedRecipe)
                        .setDishType(type);

                recipeDishTypeRepository.save(link);
            }
        }
    }

    public List<Long> getDishTypeIdsByRecipe(Long recipeId) {
        return recipeDishTypeRepository.findByRecipeId(recipeId)
                .stream()
                .map(rdt -> rdt.getDishType().getId())
                .toList();
    }

    /** * Получить id пользователя */
    private User getCurrentUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        String username = (principal instanceof UserDetails)
                ? ((UserDetails) principal).getUsername()
                : principal.toString();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    /** * Удалить рецепт */
    @Transactional
    public void delete(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));

        recipeDishTypeRepository.deleteByRecipe(recipe);
        recipeRepository.delete(recipe);
    }

    public List<Recipe> findByNationalCuisineId(Long nationalCuisineId) {
        return recipeRepository.findByNationalCuisineId(nationalCuisineId);
    }

    public List<Recipe> findByAuthorId(Long authorId) {
        return recipeRepository.findByAuthorId(authorId);
    }
}