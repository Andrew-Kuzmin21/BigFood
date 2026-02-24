package com.kuzmin.BigFood.service;

import com.kuzmin.BigFood.dto.CookingStepDto;
import com.kuzmin.BigFood.dto.RecipeFormDto;
import com.kuzmin.BigFood.dto.RecipeIngredientDto;
import com.kuzmin.BigFood.model.*;
import com.kuzmin.BigFood.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeDishTypeRepository recipeDishTypeRepository;
    private final DishTypeRepository dishTypeRepository;
    private final NationalCuisineRepository nationalCuisineRepository;
    private final UnitRepository unitRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final MediaRepository mediaRepository;
    private final MediaService mediaService;

    public RecipeService(
            RecipeRepository recipeRepository,
            RecipeDishTypeRepository recipeDishTypeRepository,
            DishTypeRepository dishTypeRepository,
            NationalCuisineRepository nationalCuisineRepository,
            UnitRepository unitRepository,
            IngredientRepository ingredientRepository,
            RecipeIngredientRepository recipeIngredientRepository,
            MediaRepository mediaRepository,
            MediaService mediaService) {
        this.recipeRepository = recipeRepository;
        this.recipeDishTypeRepository = recipeDishTypeRepository;
        this.dishTypeRepository = dishTypeRepository;
        this.nationalCuisineRepository = nationalCuisineRepository;
        this.unitRepository = unitRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.mediaRepository = mediaRepository;
        this.mediaService = mediaService;
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
    public void save(RecipeFormDto form, List<MultipartFile> stepFiles, List<Integer> stepFileIndexes, List<Long> deleteMediaIds, User currentUser) {

        // ===== 0. Очистка пустых шагов =====
        if (form.getCookingSteps() != null) {
            form.setCookingSteps(
                    form.getCookingSteps().stream()
                            .filter(s -> s.getDescription() != null && !s.getDescription().isBlank())
                            .toList()
            );
        }

        // ===== 1. Recipe (create / update) =====
        Recipe recipe = (form.getId() == null)
                ? new Recipe().setAuthor(currentUser)
                : recipeRepository.findById(form.getId())
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));

        recipe.setName(form.getName());
        recipe.setDescription(form.getDescription());
        recipe.setCookingTime(form.getCookingTime());
        recipe.setServing(form.getServing());

        if (form.getNationalCuisineId() != null) {
            recipe.setNationalCuisine(
                    nationalCuisineRepository.getReferenceById(form.getNationalCuisineId())
            );
        }

        recipeRepository.save(recipe);

        // ===== 2. МЕДИА РЕЦЕПТА =====
        //mediaRepository.deleteByRecipeIdAndCookingStepIsNull(recipe.getId());
        if (deleteMediaIds != null && !deleteMediaIds.isEmpty()) {
            mediaRepository.deleteAllById(deleteMediaIds);
        }


        mediaService.saveRecipeMedia(
                recipe,
                form.getMedia(),
                form.getMainMediaIndex()
        );

        // ===== 3. Dish types =====
        recipeDishTypeRepository.deleteByRecipe(recipe);

        if (form.getDishTypeIds() != null) {
            for (Long id : form.getDishTypeIds()) {
                recipeDishTypeRepository.save(
                        new RecipeDishType()
                                .setRecipe(recipe)
                                .setDishType(dishTypeRepository.getReferenceById(id))
                );
            }
        }

        // ===== 4. Ingredients =====
        recipeIngredientRepository.deleteByRecipe(recipe);
        recipe.getIngredients().clear();

        if (form.getIngredients() != null) {
            for (RecipeIngredientDto dto : form.getIngredients()) {
                if (dto.getIngredientId() == null) continue;

                RecipeIngredients ri = new RecipeIngredients()
                        .setRecipe(recipe)
                        .setIngredient(ingredientRepository.getReferenceById(dto.getIngredientId()))
                        .setUnit(unitRepository.getReferenceById(dto.getUnitId()))
                        .setAmount(dto.getAmount());

                recipeIngredientRepository.save(ri);
            }
        }

        // ===== 5. Cooking steps + их медиа =====
        Map<Long, CookingStep> existingMap = recipe.getCookingSteps()
                .stream()
                .collect(Collectors.toMap(CookingStep::getId, s -> s));

        List<CookingStep> updatedSteps = new ArrayList<>();
        int number = 1;

        for (CookingStepDto dto : form.getCookingSteps()) {

            CookingStep step;

            if (dto.getId() != null && existingMap.containsKey(dto.getId())) {
                step = existingMap.get(dto.getId());
            } else {
                step = new CookingStep();
                step.setRecipe(recipe);
            }

            step.setNumber(number++);
            step.setTitle(dto.getTitle());
            step.setDescription(dto.getDescription());

            updatedSteps.add(step);
        }

        recipe.getCookingSteps().clear();
        recipe.getCookingSteps().addAll(updatedSteps);
        for (CookingStepDto dto : form.getCookingSteps()) {
            System.out.println("DTO ID = " + dto.getId());
        }
//        cookingStepRepository.saveAll(updatedSteps);

        // ===== 4. Сохранение файлов шагов =====
        if (stepFiles != null && stepFileIndexes != null) {

            for (int i = 0; i < stepFiles.size(); i++) {

                MultipartFile file = stepFiles.get(i);

                if (file.isEmpty()) continue;

                int stepIndex = stepFileIndexes.get(i);

                if (stepIndex < updatedSteps.size()) {

                    CookingStep step = updatedSteps.get(stepIndex);

                    mediaService.saveStepMedia(step, List.of(file));
                }
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

    public Recipe findByIdWithStepsAndMedia(Long id) {
        return recipeRepository.findByIdWithStepsAndMedia(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
    }

}