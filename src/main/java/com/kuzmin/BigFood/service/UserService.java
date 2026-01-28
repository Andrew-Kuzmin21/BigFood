package com.kuzmin.BigFood.service;

import com.kuzmin.BigFood.model.Role;
import com.kuzmin.BigFood.model.User;
import com.kuzmin.BigFood.repository.RoleRepository;
import com.kuzmin.BigFood.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) {

        Role role = roleRepository.findByName("ROLE_User")
                .orElseThrow(() -> new IllegalStateException("ROLE_User not found"));

        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        return userRepository.findByEmail(login)
                .or(() -> userRepository.findByPhoneNumber(login))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}