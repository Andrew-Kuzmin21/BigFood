package com.kuzmin.BigFood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Accessors(chain = true)
@Data
@Entity(name = "users")
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    private Long id;

    @NotBlank(message = "Имя пользователя обязательно")
    @Size(min = 1, max = 50, message = "Имя пользователя: 3–50 символов")
    @Pattern(
            regexp = "^[a-zA-Z0-9_]+$",
            message = "Только латиница, цифры и _"
    )
    @Column(name = "user_username", length = 50, nullable = false, unique = true)
    private String username;

//    @NotBlank(message = "Email обязателен")
    @Email(message = "Некорректный email")
    @Column(name = "user_email", length = 50, unique = true)
    private String email;

    @Pattern(
            regexp = "^\\d{10,15}$",
            message = "Телефон должен содержать 10–15 цифр"
    )
    @Column(name = "user_phone_number", length = 11, unique = true)
    private String phoneNumber;

//    @Column(name = "user_phone_verified")
//    private boolean phoneVerified = false;
//
//    //Одноразовый код
//    @Column(name = "user_phone_verification_code")
//    private String phoneVerificationCode;
//
//    @Column(name = "user_phone_verification_expires_at")
//    private LocalDateTime phoneVerificationExpiresAt;

    @NotBlank(message = "Пароль обязателен")
    @Size(min = 2, message = "Пароль минимум 8 символов")
    @Column(name = "user_password", length = 100, nullable = false)
    private String password;

    @Column(name = "photo_url")
    private String photo;

    @CreationTimestamp
    @Column(name = "user_created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    @Override
    public String getUsername() { return username; }

    @Override
    public String getPassword() { return password; }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



}
