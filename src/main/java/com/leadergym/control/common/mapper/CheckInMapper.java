package com.leadergym.control.common.mapper;

import com.leadergym.control.common.constants.Constants;
import com.leadergym.control.dto.CheckInResponseDTO;
import com.leadergym.control.entity.Member;
import com.leadergym.control.entity.Payment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Component
public class CheckInMapper {

    public static CheckInResponseDTO toDto(Member member, Payment payment) {
        LocalDate today = LocalDate.now(ZoneId.of(String.valueOf(Constants.ARGENTINA_TIME_ZONE)));
        LocalDate expirationDate = payment.getEndDate();

        int daysRemaining = Math.toIntExact(ChronoUnit.DAYS.between(today, expirationDate));
        if (daysRemaining < 0) {
            daysRemaining = 0;
        }

        return new CheckInResponseDTO(
                member.getFirstName() + " " + member.getLastName(),
                daysRemaining
        );
    }
}