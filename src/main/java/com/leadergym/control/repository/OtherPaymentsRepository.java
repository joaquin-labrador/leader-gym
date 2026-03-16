package com.leadergym.control.repository;

import com.leadergym.control.entity.OtherPayments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherPaymentsRepository extends JpaRepository<OtherPayments, Long>, JpaSpecificationExecutor<OtherPayments> {
    
}
