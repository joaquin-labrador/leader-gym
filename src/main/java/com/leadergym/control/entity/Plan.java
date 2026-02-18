package com.leadergym.control.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Example:
     * FREE_MONTHLY
     * THREE_TIMES_PER_WEEK
     * WEEKLY
     * DAILY
     */
    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String description;

    /**
     * Duration in days.
     * Examples:
     * Monthly = 30
     * Weekly = 7
     * Daily = 1
     */
    @Column(name = "duration_days", nullable = false)
    private Integer durationInDays;

    /**
     * Number of allowed weekly visits.
     * NULL = unlimited
     */
    @Column(name = "weekly_visits")
    private Integer weeklyVisits;

    @Column(nullable = false, name = "price")
    private double price;
}
