package com.leadergym.control.data.jpa.domain.specification;

import com.leadergym.control.common.enums.PaymentMethod;
import com.leadergym.control.dto.PaymentHistoryFilterDTO;
import com.leadergym.control.entity.OtherPayments;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class OtherPaymentsSpecifications {

    public static Specification<OtherPayments> withFilters(PaymentHistoryFilterDTO f, ZoneId zoneId) {
        return Specification
                .where(memberDniEquals(f.getMemberDni()))
                .and(paymentMethodEquals(f.getPaymentMethod()))
                .and(paidAtFrom(f.getStartDate(), zoneId))
                .and(paidAtTo(f.getEndDate(), zoneId));
    }

    private static Specification<OtherPayments> memberDniEquals(String dni) {
        return (root, query, cb) -> {
            if (dni == null || dni.isBlank()) return cb.conjunction();
            // Acá hacemos el join con la entidad Member para buscar por su DNI (ya que no es un string directo en OtherPayments)
            return cb.equal(root.join("member").get("dni"), dni.trim());
        };
    }

    private static Specification<OtherPayments> paymentMethodEquals(String method) {
        return (root, query, cb) -> {
            if (method == null || method.isBlank()) return cb.conjunction();
            PaymentMethod pm;
            try {
                pm = PaymentMethod.valueOf(method.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new RuntimeException("Invalid paymentMethod: " + method);
            }
            return cb.equal(root.get("paymentMethod"), pm);
        };
    }

    private static Specification<OtherPayments> paidAtFrom(Date start, ZoneId zoneId) {
        return (root, query, cb) -> {
            if (start == null) return cb.conjunction();
            LocalDate from = toLocalDate(start, zoneId);
            return cb.greaterThanOrEqualTo(root.get("paymentDate"), from);
        };
    }

    private static Specification<OtherPayments> paidAtTo(Date end, ZoneId zoneId) {
        return (root, query, cb) -> {
            if (end == null) return cb.conjunction();
            // Como en OtherPayments 'paymentDate' es LocalDate, usamos un lessThanOrEqualTo directo
            LocalDate toInclusive = toLocalDate(end, zoneId);
            return cb.lessThanOrEqualTo(root.get("paymentDate"), toInclusive);
        };
    }

    private static LocalDate toLocalDate(Date date, ZoneId zoneId) {
        return Instant.ofEpochMilli(date.getTime()).atZone(zoneId).toLocalDate();
    }
}

