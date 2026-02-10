package com.leadergym.control.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "membership_fees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Plan to which this fee applies
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    /**
     * Price of the subscription
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /**
     * Date from which this fee is valid
     */
    @Column(name = "valid_from", nullable = false)
    private LocalDate validFrom;

    /**
     * Indicates if this is the current active fee
     */
    @Column(nullable = false)
    private boolean active;
}
