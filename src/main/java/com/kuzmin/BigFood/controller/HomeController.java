package com.kuzmin.BigFood.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер главной страницы.
 */
@Controller
public class HomeController {

    /**
     * Главная страница сайта
     */
    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();

        if (isAuthenticated) {
            var authorities = authentication.getAuthorities();
            model.addAttribute("showCustomerButtons", authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_User")));
            model.addAttribute("showMechanicButtons", authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_Mechanic")));
            model.addAttribute("showPartsManagerButtons", authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_PartsManager")));
            model.addAttribute("showAdminButtons", authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_Admin")));
        } else {
            model.addAttribute("showAnonymousButton", true);
        }

        return "home";
    }

}
