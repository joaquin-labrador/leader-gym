package com.leadergym.control.entity;

import com.leadergym.control.common.enums.PaymentMethod;
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

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", length = 20)
    private PaymentMethod paymentMethod;

}
