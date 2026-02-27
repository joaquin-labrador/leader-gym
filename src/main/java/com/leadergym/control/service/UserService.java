package com.leadergym.control.service;

import com.leadergym.control.dto.LoginResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    LoginResponseDTO login(String username, String password,
                           HttpServletRequest request,
                           HttpServletResponse response);
}