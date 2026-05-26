package com.vphilip.finance.app.expense.repository;

import com.vphilip.finance.app.expense.model.ExpenseCategory;
import com.vphilip.finance.app.expense.dto.ExpenseCategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {

    @Query(value = """
            SELECT ec.id, ec.expense_type_id, ec.label, ec.description, et.label as expense_type_label
            FROM expense_category ec
            LEFT JOIN expense_type et ON ec.expense_type_id = et.id
            WHERE ec.user_id = :userId
            ORDER BY ec.id
            """, nativeQuery = true)
    List<ExpenseCategoryDTO> findAllWithType(@Param("userId") Integer userId);
}
