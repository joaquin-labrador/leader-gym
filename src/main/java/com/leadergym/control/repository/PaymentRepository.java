package com.leadergym.control.repository;

import com.leadergym.control.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findTopByMember_DniOrderByEndDateDesc(String dni);
    Optional<Payment> findTopByMember_DniAndActiveTrueOrderByEndDateDesc(String dni);
    List<Payment> findByMember_Dni(String dni);
    List<Payment> findByMemberId(Long memberId);
}
