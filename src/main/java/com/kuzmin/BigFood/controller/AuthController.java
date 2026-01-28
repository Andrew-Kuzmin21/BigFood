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

@Controller
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;

    public AuthController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("form", new RegistrationForm());
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationUser(
            @Valid @ModelAttribute("form") RegistrationForm form,
            BindingResult bindingResult,
            Model model
    ) {
        User user = form.getUser();

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        boolean hasEmail = user.getEmail() != null && !user.getEmail().isBlank();
        boolean hasPhone = user.getPhoneNumber() != null && !user.getPhoneNumber().isBlank();

        if (!hasEmail && !hasPhone) {
            model.addAttribute("globalError", "Укажите email или номер телефона");
            return "registration";
        }

        // 🔥 ВАЖНО
        if (hasEmail && !hasPhone) {
            user.setPhoneNumber(null);
        }

        if (hasPhone && !hasEmail) {
            user.setEmail(null);
        }

        if (hasEmail && userRepository.existsByEmail(user.getEmail())) {
            model.addAttribute("emailError", "Email уже занят");
            return "registration";
        }

        if (hasPhone && userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            model.addAttribute("phoneError", "Телефон уже занят");
            return "registration";
        }

        userService.registerUser(user);
        return "redirect:/login";
    }

}
