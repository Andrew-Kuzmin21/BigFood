package com.kuzmin.BigFood.controller;

import com.kuzmin.BigFood.model.RegistrationForm;
import com.kuzmin.BigFood.model.User;
import com.kuzmin.BigFood.repository.UserRepository;
import com.kuzmin.BigFood.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Контроллер регистрации пользователей.
 */
@Controller
public class RegistrationController {

    private final UserService userService;
    private final UserRepository userRepository;

    public RegistrationController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
     * Отображение формы регистрации
     */
    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("form", new RegistrationForm());
        return "registration";
    }

    /**
     * Обработка формы регистрации
     */
    @PostMapping("/registration")
    public String registrationUser(
            @Valid @ModelAttribute("form") RegistrationForm form,
            BindingResult bindingResult,
            Model model
    ) {
        User user = form.getUser();

        // Ошибки валидации (@NotBlank, @Size и т.п.)
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        boolean hasEmail = user.getEmail() != null && !user.getEmail().isBlank();
        boolean hasPhone = user.getPhoneNumber() != null && !user.getPhoneNumber().isBlank();

        // Пользователь не указал ни email, ни телефон
        if (!hasEmail && !hasPhone) {
            model.addAttribute("globalError", "Укажите email или номер телефона");
            return "registration";
        }

        // Если регистрация по email — очищаем телефон
        if (hasEmail && !hasPhone) {
            user.setPhoneNumber(null);
        }

        // Если регистрация по телефону — очищаем email
        if (hasPhone && !hasEmail) {
            user.setEmail(null);
        }

        // Проверка уникальности email
        if (hasEmail && userRepository.existsByEmail(user.getEmail())) {
            model.addAttribute("emailError", "Email уже занят");
            return "registration";
        }

        // Проверка уникальности телефона
        if (hasPhone && userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            model.addAttribute("phoneError", "Телефон уже занят");
            return "registration";
        }

        // Регистрация пользователя
        userService.registerUser(user);
        return "redirect:/login";
    }

}
