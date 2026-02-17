package com.leadergym.control.service.impl;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class UtilService {
    public LocalDate calculateExpiration(int daysToAdd) {
        return LocalDate.now().plusDays(daysToAdd);
    }
}
