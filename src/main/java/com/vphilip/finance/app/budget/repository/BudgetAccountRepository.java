package com.vphilip.finance.app.budget.repository;

import com.vphilip.finance.app.budget.dto.BudgetAccountDTO;
import com.vphilip.finance.app.budget.model.BudgetAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BudgetAccountRepository extends JpaRepository<BudgetAccount, Long> {

    @Query(value = """
            SELECT ba.id, ba.budget_id, ba.account_id, a.name AS account_label
            FROM budget_account ba
            JOIN account a ON a.id = ba.account_id
            WHERE ba.budget_id = :budgetId
            ORDER BY ba.id
            """, nativeQuery = true)
    List<BudgetAccountDTO> findAllByBudgetId(@Param("budgetId") Long budgetId);

    @Query(value = "SELECT * FROM budget_account WHERE budget_id = :budgetId AND account_id = :accountId LIMIT 1",
            nativeQuery = true)
    Optional<BudgetAccount> findByBudgetIdAndAccountId(@Param("budgetId") Long budgetId,
                                                        @Param("accountId") Long accountId);

    @Query(value = "SELECT COUNT(*) > 0 FROM budget_account WHERE budget_id = :budgetId AND account_id = :accountId",
            nativeQuery = true)
    boolean existsByBudgetIdAndAccountId(@Param("budgetId") Long budgetId, @Param("accountId") Long accountId);

    @Modifying
    @Query(value = "DELETE FROM budget_account WHERE budget_id = :budgetId", nativeQuery = true)
    void deleteAllByBudgetId(@Param("budgetId") Long budgetId);
}
