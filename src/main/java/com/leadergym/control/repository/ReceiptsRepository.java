package com.leadergym.control.repository;

import com.leadergym.control.entity.Receipts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReceiptsRepository extends JpaRepository<Receipts, Long> {
    long countByMemberDniAndDateBetween(String dni, LocalDate start, LocalDate end);
    List<Receipts> findByMemberDni(String dni);
    Optional<Receipts> findTopByMember_DniOrderByDateDesc(String dni);
    List<Receipts> findByDate(LocalDate date);
}
