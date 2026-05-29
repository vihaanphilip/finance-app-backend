package com.vphilip.finance.app.budget.repository;

import com.vphilip.finance.app.budget.model.BudgetEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BudgetEntryRepository extends JpaRepository<BudgetEntry, Long> {

    @Query(value = "SELECT * FROM budget_entry WHERE budget_id = :budgetId ORDER BY id DESC", nativeQuery = true)
    List<BudgetEntry> findAllByBudgetId(@Param("budgetId") Long budgetId);

    @Modifying
    @Query(value = "DELETE FROM budget_entry WHERE budget_id = :budgetId", nativeQuery = true)
    void deleteAllByBudgetId(@Param("budgetId") Long budgetId);
}
