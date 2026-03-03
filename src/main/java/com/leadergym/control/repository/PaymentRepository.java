package com.leadergym.control.repository;

import com.leadergym.control.entity.Payment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findTopByMember_DniOrderByEndDateDesc(String dni);
    Optional<Payment> findTopByMember_DniAndActiveTrueOrderByEndDateDesc(String dni);
    List<Payment> findByMember_Dni(String dni);
    List<Payment> findByMemberId(Long memberId);
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Payment p WHERE p.member.dni = :dni")
    Boolean existsByMember_DniAndActiveTrue(String dni);
    @Query("SELECT p FROM Payment p WHERE p.active = true ORDER BY p.endDate DESC")
    List<Payment> findActivePaymentsOrderByEndDateDesc(Pageable pageable);
}
