package com.kuzmin.BigFood.controller;
import com.kuzmin.BigFood.model.Recipe;
import com.kuzmin.BigFood.service.NationalCuisineService;
import com.kuzmin.BigFood.service.RecipeService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/** * Контроллер для работы с рецептами. */
@Controller
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recipeService;
    private final NationalCuisineService nationalCuisineService;
    public RecipeController(RecipeService recipeService, NationalCuisineService nationalCuisineService) {
        this.recipeService = recipeService;
        this.nationalCuisineService = nationalCuisineService;
    }

    /** * Список рецептов с пагинацией */
    @GetMapping
    public String listRecipes( Model model, @RequestParam(defaultValue = "0") int page ) {
        Page<Recipe> recipePage = recipeService.getPage(page, 10);
        model.addAttribute("recipePage", recipePage);
        model.addAttribute("currentPage", page);
        return "recipes";
    }

    /** * Форма создания рецепта */
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("nationalCuisines", nationalCuisineService.getAll());
        return "recipe_form"; }

    /** * Форма редактирования рецепта */
    @GetMapping("/{id}/edit")
    public String showEditForm( @PathVariable Long id, Model model ) {
        model.addAttribute("recipe", recipeService.getById(id));
        model.addAttribute("nationalCuisines", nationalCuisineService.getAll());
        return "recipe_form";
    }

    /** * Сохранение рецепта */
    @PostMapping
    public String saveRecipe(@ModelAttribute Recipe recipe) {
        recipeService.save(recipe);
        return "redirect:/recipes";
    }

    /** * Удаление рецепта */
    @PostMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable Long id) {
        recipeService.delete(id);
        return "redirect:/recipes";
    }

}