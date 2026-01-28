package com.kuzmin.BigFood.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@PasswordMatches
public class RegistrationForm {
    @Valid
    private User user;
    private Role role;

    @NotBlank(message = "Подтвердите пароль")
    private String confirmPassword;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
