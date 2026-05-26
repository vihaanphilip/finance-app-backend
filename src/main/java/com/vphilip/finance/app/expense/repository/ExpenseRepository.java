package com.vphilip.finance.app.expense.repository;

import com.vphilip.finance.app.expense.dto.ExpenseDTO;
import com.vphilip.finance.app.expense.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query(value = """
            SELECT e.id,
                   e.account_id,
                   a.name as account_label,
                   e.description,
                   e.amount,
                   ec.expense_type_id,
                   et.label as expense_type_label,
                   e.expense_category_id,
                   ec.label as expense_category_label,
                   e.transaction_date,
                   e.created_at,
                   e.last_modified_at
            FROM expense e
            LEFT JOIN account a ON e.account_id = a.id
            LEFT JOIN expense_category ec ON e.expense_category_id = ec.id
            LEFT JOIN expense_type et ON ec.expense_type_id = et.id
            WHERE a.user_id = :userId
            ORDER BY e.id DESC
            """, nativeQuery = true)
    List<ExpenseDTO> findAllWithLabels(@Param("userId") Integer userId);
}
