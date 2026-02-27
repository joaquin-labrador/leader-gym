package com.leadergym.control.service.impl;

import com.leadergym.control.dto.LoginResponseDTO;
import com.leadergym.control.entity.User;
import com.leadergym.control.repository.UserRepository;
import com.leadergym.control.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponseDTO login(String username, String password,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

        if (username == null || password == null) return null;

        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return null;

        if (!passwordEncoder.matches(password, user.getPassword())) return null;

        // ROLE para Spring Security
        String role = user.getRole();
        String authority = (role != null && role.startsWith("ROLE_")) ? role : "ROLE_" + role;

        var auth = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                null,
                List.of(new SimpleGrantedAuthority(authority))
        );

        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        // Guarda en sesión (JSESSIONID)
        securityContextRepository.saveContext(context, request, response);

        // 24h (opcional si ya lo tienes en properties)
        request.getSession(true).setMaxInactiveInterval(24 * 60 * 60);

        return new LoginResponseDTO(user.getUsername(), user.getRole(), true);
    }
}