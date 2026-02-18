package com.leadergym.control.repository;

import com.leadergym.control.entity.MembershipFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipFeeRepository extends JpaRepository<MembershipFee, Long> {
}
