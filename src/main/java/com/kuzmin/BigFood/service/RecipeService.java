package com.kuzmin.BigFood.service;

import com.kuzmin.BigFood.model.Recipe;
import com.kuzmin.BigFood.model.User;
import com.kuzmin.BigFood.repository.RecipeRepository;
import com.kuzmin.BigFood.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public RecipeService(RecipeRepository recipeRepository, UserService userService, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    /**
     * Получение рецептов с пагинацией
     */
    public Page<Recipe> getPage(int page, int size) {
        return recipeRepository.findAll(PageRequest.of(page, size));
    }

    /**
     * Получить рецепт по id
     */
    public Recipe getById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Неверный ID: " + id));
    }

    /**
     * Сохранить рецепт (create / update)
     */
    public void save(Recipe recipe) {
        if (recipe.getId() == null) {
            // CREATE
            User currentUser = getCurrentUser();
            recipe.setAuthor(currentUser);
        } else {
            // UPDATE
            Recipe existingRecipe = recipeRepository.findById(recipe.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));

            recipe.setAuthor(existingRecipe.getAuthor());
        }
        recipeRepository.save(recipe);
    }

    /**
     * Получить id пользователя
     */
    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + username));
    }

    /**
     * Удалить рецепт
     */
    public void delete(Long id) {
        recipeRepository.deleteById(id);
    }

    public List<Recipe> findByNationalCuisineId(Long nationalCuisineId) {
        return recipeRepository.findByNationalCuisineId(nationalCuisineId);
    }

    public List<Recipe> findByAuthorId(Long authorId) {
        return recipeRepository.findByAuthorId(authorId);
    }
}


//@Service
//public class RecipeService {
//
//    private final RecipeRepository recipeRepository;
//
//    public RecipeService(RecipeRepository recipeRepository) {
//        this.recipeRepository = recipeRepository;
//    }
//
//    public List<Recipe> getAll() {
//        List<Recipe> recipes = new ArrayList<>();
//        recipeRepository.findAll().forEach(recipes::add);
//        return recipes;
//    }
//
//    public List<Recipe> findByNationalCuisineId(Long nationalCuisineId) {
//        return recipeRepository.findByNationalCuisineId(nationalCuisineId);
//    }
//
//    public List<Recipe> findByAuthorId(Long authorId) {
//        return recipeRepository.findByAuthorId(authorId);
//    }
//}
