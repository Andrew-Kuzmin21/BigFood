package com.kuzmin.BigFood.controller;
import com.kuzmin.BigFood.model.Recipe;
import com.kuzmin.BigFood.service.DishTypeService;
import com.kuzmin.BigFood.service.NationalCuisineService;
import com.kuzmin.BigFood.service.RecipeDishTypeService;
import com.kuzmin.BigFood.service.RecipeService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** * Контроллер для работы с рецептами. */
@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final NationalCuisineService nationalCuisineService;
    private final RecipeDishTypeService recipeDishTypeService;
    private final DishTypeService dishTypeService;
    public RecipeController(
            RecipeService recipeService,
            NationalCuisineService nationalCuisineService,
            RecipeDishTypeService recipeDishTypeService,
            DishTypeService dishTypeService) {
        this.recipeService = recipeService;
        this.nationalCuisineService = nationalCuisineService;
        this.recipeDishTypeService = recipeDishTypeService;
        this.dishTypeService = dishTypeService;
    }

    /** * Страница Рецептов */
    @GetMapping
    public String listRecipes( Model model, Authentication authentication, @RequestParam(defaultValue = "0") int page ) {
//        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        boolean isAuthenticated =
                authentication != null &&
                        authentication.getPrincipal() instanceof UserDetails;

        if (isAuthenticated) {
            var authorities = authentication.getAuthorities();
            model.addAttribute("showCustomerButtons", authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_User")));
            model.addAttribute("showMechanicButtons", authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_Mechanic")));
            model.addAttribute("showPartsManagerButtons", authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_PartsManager")));
            model.addAttribute("showAdminButtons", authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_Admin")));
        } else {
            model.addAttribute("showAnonymousButton", true);
        }

        Page<Recipe> recipePage = recipeService.getPage(page, 10);
        model.addAttribute("recipePage", recipePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("recipeService", recipeService);
        return "recipes";
    }

    /** * Форма создания рецепта */
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("nationalCuisines", nationalCuisineService.getAll());
        // model.addAttribute("dishTypes", recipeDishTypeService.getAll());
         model.addAttribute("dishTypes", dishTypeService.getAll());
         return "recipe_form";
    }

    /** * Форма редактирования рецепта */
    @GetMapping("/{id}/edit")
    public String showEditForm( @PathVariable Long id, Model model ) {
        model.addAttribute("recipe", recipeService.getById(id));
        model.addAttribute("nationalCuisines", nationalCuisineService.getAll());
        model.addAttribute("dishTypes", dishTypeService.getAll());
        model.addAttribute("selectedDishTypeIds", recipeDishTypeService.getDishTypeIdsByRecipe(id)
        );

        return "recipe_form";
    }

    /** * Сохранение рецепта */
    @PostMapping
    public String saveRecipe(@ModelAttribute Recipe recipe, @RequestParam(required = false)List<Long> dishTypeIds, Authentication authentication) {
        recipeService.save(recipe, dishTypeIds, authentication);
        return "redirect:/recipes";
    }

    /** * Удаление рецепта */
    @PostMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable Long id) {
        recipeService.delete(id);
        return "redirect:/recipes";
    }

}