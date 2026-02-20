package com.leadergym.control.repository;

import com.leadergym.control.entity.Receipts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReceiptsRepository extends JpaRepository<Receipts, Long> {
    long countByMemberDniAndDateBetween(String dni, LocalDateTime start, LocalDateTime end);
}
