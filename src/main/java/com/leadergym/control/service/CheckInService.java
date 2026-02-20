package com.leadergym.control.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface CheckInService {
        void checkIn(String dni);
}