package com.devsu.AccountMoveService.repository;

import com.devsu.AccountMoveService.entity.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MoveRepository extends JpaRepository<Move,Long> {
    List<Move> findByAccountIdOrderByIdAsc(Long accountId);

    @Query("SELECT m FROM Move m " +
            "WHERE m.account.id = :accountId " +
            "AND m.date >= :startDate " +
            "AND m.date <= :endDate")
    List<Move> findByAccountIdAndDateRange(Long accountId, LocalDate startDate, LocalDate endDate);
}
