package com.leadergym.control.repository;

import com.leadergym.control.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByDni(String dni);

    @Query("""
                            SELECT m
                            FROM Member m
                            LEFT JOIN Payment p
                                ON p.member = m
                                AND p.startDate = (
                                    SELECT MAX(p2.startDate)
                                    FROM Payment p2
                                    WHERE p2.member = m
                                )
            ORDER BY
                CASE
                    WHEN p IS NOT NULL
                         AND p.active = true
                         AND p.endDate > :today
                    THEN 0
                    ELSE 1
                END ASC,
                LOWER(m.firstName) ASC,
                LOWER(m.lastName) ASC
            """)
    Page<Member> findAllOrderByActiveFirst(
            @Param("today") LocalDate today,
            Pageable pageable
    );
}
