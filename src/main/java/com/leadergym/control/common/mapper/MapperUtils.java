package com.leadergym.control.common.mapper;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MapperUtils {
    public static int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            return 0;
        }
        java.time.LocalDate currentDate = java.time.LocalDate.now();
        int age = currentDate.getYear() - birthDate.getYear();
        if (currentDate.getMonthValue() < birthDate.getMonthValue() ||
                (currentDate.getMonthValue() == birthDate.getMonthValue() && currentDate.getDayOfMonth() < birthDate.getDayOfMonth())) {
            age--;
        }
        return age;
    }
}
