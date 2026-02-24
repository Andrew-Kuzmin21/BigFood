package com.kuzmin.BigFood.controller;
import com.kuzmin.BigFood.dto.CookingStepDto;
import com.kuzmin.BigFood.dto.RecipeFormDto;
import com.kuzmin.BigFood.dto.RecipeIngredientDto;
import com.kuzmin.BigFood.mapper.RecipeMapper;
import com.kuzmin.BigFood.model.Recipe;
import com.kuzmin.BigFood.model.User;
import com.kuzmin.BigFood.service.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/** * Контроллер для работы с рецептами. */
@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final NationalCuisineService nationalCuisineService;
    private final DishTypeService dishTypeService;
    private final IngredientService ingredientService;
    private final UnitService unitService;
    public RecipeController(
            RecipeService recipeService,
            NationalCuisineService nationalCuisineService,
            DishTypeService dishTypeService,
            IngredientService ingredientService,
            UnitService unitService) {
        this.recipeService = recipeService;
        this.nationalCuisineService = nationalCuisineService;
        this.dishTypeService = dishTypeService;
        this.ingredientService = ingredientService;
        this.unitService = unitService;
    }

    /** * Страница Рецептов */
    @GetMapping
    public String listRecipes( Model model, Authentication authentication, @RequestParam(defaultValue = "0") int page ) {
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

        Page<Recipe> recipePage = recipeService.getPage(page, 20);
        model.addAttribute("recipePage", recipePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("recipeService", recipeService);
        return "recipes";
    }

    /** * Форма создания рецепта */
    @GetMapping("/new")
    public String showAddForm(Model model) {
        RecipeFormDto form = new RecipeFormDto();
        if (form.getIngredients() == null) {
            form.setIngredients(new ArrayList<>());
        }

        // 3 пустые строки ингредиентов по умолчанию
        for (int i = 0; i < 3; i++) {
            form.getIngredients().add(new RecipeIngredientDto());
        }

        // 3 пустые строки шагов приготовления по умолчанию
        form.setCookingSteps(new ArrayList<>());
        for (int i = 1; i <= 3; i++) {
            form.getCookingSteps().add(new CookingStepDto());
        }

        model.addAttribute("form", form);
        model.addAttribute("nationalCuisines", nationalCuisineService.getAll());
        model.addAttribute("dishTypes", dishTypeService.getAll());
        model.addAttribute("ingredientsList", ingredientService.getAllIngredients());
        model.addAttribute("units", unitService.getAll());

        return "recipe-form";
    }

    /** * Форма редактирования рецепта */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
//        Recipe recipe = recipeService.getById(id);
        Recipe recipe = recipeService.findByIdWithStepsAndMedia(id);
        RecipeFormDto form = RecipeMapper.toForm(recipe);


        model.addAttribute("form", form);
        model.addAttribute("nationalCuisines", nationalCuisineService.getAll());
        model.addAttribute("dishTypes", dishTypeService.getAll());
        model.addAttribute("ingredientsList", ingredientService.getAllIngredients());
        model.addAttribute("units", unitService.getAll());

        return "recipe-form";
    }

    /** * Сохранение рецепта */
    @PostMapping("/save")
    public String saveRecipe(
            @Valid @ModelAttribute("form") RecipeFormDto form,
            BindingResult result,
            @RequestParam(required = false) List<MultipartFile> stepFiles,
            @RequestParam(required = false) List<Integer> stepFileIndexes,
            @RequestParam(required = false) List<Long> deleteMediaIds,
            @AuthenticationPrincipal User currentUser,
            Model model
    ) {
        if (form.getMedia() != null && form.getMedia().size() > 10) {
            throw new IllegalArgumentException("Слишком много файлов");
        }
        System.out.println(">>> SAVE RECIPE HIT: files = " + (form.getMedia() == null ? "null" : form.getMedia().size()));
        if (result.hasErrors()) {
            model.addAttribute("nationalCuisines", nationalCuisineService.getAll());
            model.addAttribute("dishTypes", dishTypeService.getAll());
            model.addAttribute("ingredientsList", ingredientService.getAllIngredients());
            model.addAttribute("units", unitService.getAll());
            return "recipe-form";
        }

        recipeService.save(form, stepFiles, stepFileIndexes, deleteMediaIds, currentUser);
        return "redirect:/recipes";
    }


    /** * Удаление рецепта */
    @PostMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable Long id) {
        recipeService.delete(id);
        return "redirect:/recipes";
    }

}