package com.leadergym.control.data.jpa.domain.specification;

import com.leadergym.control.common.enums.PaymentMethod;
import com.leadergym.control.dto.PaymentHistoryFilterDTO;
import com.leadergym.control.entity.PaymentHistory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class PaymentHistorySpecifications {

    public static Specification<PaymentHistory> withFilters(PaymentHistoryFilterDTO f, ZoneId zoneId) {
        return Specification
                .where(memberDniEquals(f.getMemberDni()))
                .and(paymentMethodEquals(f.getPaymentMethod()))
                .and(paidAtFrom(f.getStartDate(), zoneId))
                .and(paidAtTo(f.getEndDate(), zoneId));
    }

    private static Specification<PaymentHistory> memberDniEquals(String dni) {
        return (root, query, cb) -> {
            if (dni == null || dni.isBlank()) return cb.conjunction();
            return cb.equal(root.get("memberDni"), dni.trim());
        };
    }

    private static Specification<PaymentHistory> paymentMethodEquals(String method) {
        return (root, query, cb) -> {
            if (method == null || method.isBlank()) return cb.conjunction();
            PaymentMethod pm;
            try {
                pm = PaymentMethod.valueOf(method.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                // podés lanzar tu excepción custom si preferís
                throw new RuntimeException("Invalid paymentMethod: " + method);
            }
            return cb.equal(root.get("paymentMethod"), pm);
        };
    }

    private static Specification<PaymentHistory> paidAtFrom(Date start, ZoneId zoneId) {
        return (root, query, cb) -> {
            if (start == null) return cb.conjunction();
            LocalDateTime from = toLocalDateTime(start, zoneId);
            return cb.greaterThanOrEqualTo(root.get("paymentDate"), from);
        };
    }

    // endDate lo hago inclusivo hasta fin de día
    private static Specification<PaymentHistory> paidAtTo(Date end, ZoneId zoneId) {
        return (root, query, cb) -> {
            if (end == null) return cb.conjunction();
            LocalDate endDay = toLocalDateTime(end, zoneId).toLocalDate();
            LocalDateTime toExclusive = endDay.plusDays(1).atStartOfDay();
            return cb.lessThan(root.get("paymentDates"), toExclusive);
        };
    }

    private static LocalDateTime toLocalDateTime(Date date, ZoneId zoneId) {
        return Instant.ofEpochMilli(date.getTime()).atZone(zoneId).toLocalDateTime();
    }
}