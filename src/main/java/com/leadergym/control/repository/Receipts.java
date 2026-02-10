package com.leadergym.control.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Receipts extends JpaRepository<Receipts, Long> {
}
