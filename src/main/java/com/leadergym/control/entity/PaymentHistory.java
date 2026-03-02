package com.leadergym.control.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "payment_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    //not relation with payment, just a copy of the data at the moment of the payment
    @Column(name = "member_dni", nullable = false)
    private String memberDni;

    @Column(name = "plan_name", nullable = false)
    private String planName;

    @Column(name = "amount_paid", nullable = false)
    private double amountPaid;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

}
