package com.leadergym.control.service.impl;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
public class UtilService {
    public LocalDate calculateExpiration(int daysToAdd) {
        return LocalDate.now().plusDays(daysToAdd);
    }

    public boolean isPaymentValid(LocalDate endDate) {
        LocalDate currentDate = LocalDate.now();
        return !currentDate.isAfter(endDate);
    }

    public LocalDate getFirstDayOfTheWeek() {
        return LocalDate.now().with(DayOfWeek.MONDAY);
    }

    public LocalDate getLastDayOfTheWeek() {
        return LocalDate.now().with(DayOfWeek.SUNDAY);
    }

    public int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            return 0;
        }
        LocalDate currentDate = LocalDate.now();
        int age = currentDate.getYear() - birthDate.getYear();
        if (currentDate.getMonthValue() < birthDate.getMonthValue() ||
                (currentDate.getMonthValue() == birthDate.getMonthValue() && currentDate.getDayOfMonth() < birthDate.getDayOfMonth())) {
            age--;

        }
        return age;
    }

    public LocalDate calculatePlanEndDate(LocalDate startDate, int durationInDays) {
        return startDate.plusDays(durationInDays);
    }
}