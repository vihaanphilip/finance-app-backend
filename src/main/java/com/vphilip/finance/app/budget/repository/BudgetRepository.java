package com.vphilip.finance.app.budget.repository;

import com.vphilip.finance.app.budget.dto.BudgetDTO;
import com.vphilip.finance.app.budget.dto.BudgetSummaryDTO;
import com.vphilip.finance.app.budget.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    @Query(value = """
            SELECT b.id, b.name, b.description, b.starting_amount
            FROM budget b
            WHERE b.user_id = :userId
            ORDER BY b.id DESC
            """, nativeQuery = true)
    List<BudgetDTO> findAllByUserId(@Param("userId") Integer userId);

    @Query(value = """
            SELECT
                b.id,
                b.name,
                b.starting_amount,
                COALESCE(SUM(CASE WHEN be.direction = 'IN'  THEN be.amount ELSE 0 END), 0) AS entries_in,
                COALESCE(SUM(CASE WHEN be.direction = 'OUT' THEN be.amount ELSE 0 END), 0) AS entries_out,
                COALESCE((SELECT SUM(e.amount) FROM earning e WHERE e.budget_id = b.id), 0) AS attributed_earnings,
                COALESCE((SELECT SUM(ex.amount) FROM expense ex WHERE ex.budget_id = b.id), 0) AS attributed_expenses,
                COALESCE((
                    SELECT
                        COALESCE(SUM(CASE WHEN ba_in.id IS NOT NULL AND ba_out.id IS NULL THEN t.amount ELSE 0 END), 0)
                        - COALESCE(SUM(CASE WHEN ba_out.id IS NOT NULL AND ba_in.id IS NULL THEN t.amount ELSE 0 END), 0)
                    FROM transfer t
                    LEFT JOIN budget_account ba_in  ON ba_in.budget_id  = b.id AND ba_in.account_id  = t.to_account_id
                    LEFT JOIN budget_account ba_out ON ba_out.budget_id = b.id AND ba_out.account_id = t.from_account_id
                    WHERE t.budget_id = b.id
                ), 0) AS attributed_transfers_net,
                b.starting_amount
                + COALESCE(SUM(CASE WHEN be.direction = 'IN'  THEN be.amount ELSE 0 END), 0)
                - COALESCE(SUM(CASE WHEN be.direction = 'OUT' THEN be.amount ELSE 0 END), 0)
                + COALESCE((SELECT SUM(e.amount) FROM earning e WHERE e.budget_id = b.id), 0)
                - COALESCE((SELECT SUM(ex.amount) FROM expense ex WHERE ex.budget_id = b.id), 0)
                + COALESCE((
                    SELECT
                        COALESCE(SUM(CASE WHEN ba_in.id IS NOT NULL AND ba_out.id IS NULL THEN t.amount ELSE 0 END), 0)
                        - COALESCE(SUM(CASE WHEN ba_out.id IS NOT NULL AND ba_in.id IS NULL THEN t.amount ELSE 0 END), 0)
                    FROM transfer t
                    LEFT JOIN budget_account ba_in  ON ba_in.budget_id  = b.id AND ba_in.account_id  = t.to_account_id
                    LEFT JOIN budget_account ba_out ON ba_out.budget_id = b.id AND ba_out.account_id = t.from_account_id
                    WHERE t.budget_id = b.id
                ), 0) AS current_value
            FROM budget b
            LEFT JOIN budget_entry be ON be.budget_id = b.id
            WHERE b.id = :budgetId AND b.user_id = :userId
            GROUP BY b.id, b.name, b.starting_amount
            """, nativeQuery = true)
    BudgetSummaryDTO findSummaryByIdAndUserId(@Param("budgetId") Long budgetId, @Param("userId") Integer userId);
}
