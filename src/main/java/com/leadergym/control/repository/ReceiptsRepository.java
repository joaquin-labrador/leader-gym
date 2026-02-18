package com.leadergym.control.repository;

import com.leadergym.control.entity.Receipts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptsRepository extends JpaRepository<Receipts, Long> {
}
