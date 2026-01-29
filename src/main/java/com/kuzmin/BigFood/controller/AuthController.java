package com.kuzmin.BigFood.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер аутентификации.
 */
@Controller
public class AuthController {

    /**
     * Страница входа в систему
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
