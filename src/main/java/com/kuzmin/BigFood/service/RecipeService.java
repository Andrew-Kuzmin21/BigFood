package com.kuzmin.BigFood.service;

import com.kuzmin.BigFood.dto.RecipeFormDto;
import com.kuzmin.BigFood.dto.RecipeIngredientDto;
import com.kuzmin.BigFood.model.*;
import com.kuzmin.BigFood.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeDishTypeRepository recipeDishTypeRepository;
    private final DishTypeRepository dishTypeRepository;
    private final NationalCuisineRepository nationalCuisineRepository;
    private final UnitRepository unitRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    public RecipeService(
            RecipeRepository recipeRepository,
            RecipeDishTypeRepository recipeDishTypeRepository,
            DishTypeRepository dishTypeRepository,
            NationalCuisineRepository nationalCuisineRepository,
            UnitRepository unitRepository,
            IngredientRepository ingredientRepository,
            RecipeIngredientRepository recipeIngredientRepository) {
        this.recipeRepository = recipeRepository;
        this.recipeDishTypeRepository = recipeDishTypeRepository;
        this.dishTypeRepository = dishTypeRepository;
        this.nationalCuisineRepository = nationalCuisineRepository;
        this.unitRepository = unitRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
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
    public void save(RecipeFormDto form, User currentUser) {

        // ===== 1. Recipe (create / update) =====
        Recipe recipe;

        if (form.getId() == null) {
            recipe = new Recipe();
            recipe.setAuthor(currentUser);
        } else {
            recipe = recipeRepository.findById(form.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));
        }

        recipe.setName(form.getName());
        recipe.setDescription(form.getDescription());
        recipe.setCookingTime(form.getCookingTime());
        recipe.setServing(form.getServing());

        if (form.getNationalCuisineId() != null) {
            recipe.setNationalCuisine(
                    nationalCuisineRepository.getReferenceById(form.getNationalCuisineId())
            );
        }

        Recipe savedRecipe = recipeRepository.save(recipe);

        // ===== 2. Dish types =====
        recipeDishTypeRepository.deleteByRecipe(savedRecipe);

        if (form.getDishTypeIds() != null) {
            for (Long id : form.getDishTypeIds()) {
                recipeDishTypeRepository.save(
                        new RecipeDishType()
                                .setRecipe(savedRecipe)
                                .setDishType(dishTypeRepository.getReferenceById(id))
                );
            }
        }

        // ===== 3. Ingredients =====
        recipeIngredientRepository.deleteByRecipe(savedRecipe);
        savedRecipe.getIngredients().clear();

        if (form.getIngredients() != null) {
            for (RecipeIngredientDto dto : form.getIngredients()) {
                if (dto.getIngredientId() == null) continue;

                RecipeIngredients ri = new RecipeIngredients()
                        .setRecipe(savedRecipe)
                        .setIngredient(ingredientRepository.getReferenceById(dto.getIngredientId()))
                        .setUnit(unitRepository.getReferenceById(dto.getUnitId()))
                        .setAmount(dto.getAmount());

                recipeIngredientRepository.save(ri);
            }
        }
    }

    public List<Long> getDishTypeIdsByRecipe(Long recipeId) {
        return recipeDishTypeRepository.findByRecipeId(recipeId)
                .stream()
                .map(rdt -> rdt.getDishType().getId())
                .toList();
    }

    public String getDishTypePath(Recipe recipe) {
        if (recipe.getDishTypes() == null || recipe.getDishTypes().isEmpty()) {
            return "—";
        }

        // Берем тип (последний в цепочке)
        DishType leaf = recipe.getDishTypes().get(
                recipe.getDishTypes().size() - 1
        ).getDishType();

        List<String> names = new ArrayList<>();

        while (leaf != null) {
            names.add(leaf.getName());
            leaf = leaf.getParent();
        }

        Collections.reverse(names);

        return String.join(", ", names);
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