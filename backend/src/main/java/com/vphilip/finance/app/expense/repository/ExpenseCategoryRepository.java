package com.vphilip.finance.app.expense.repository;

import com.vphilip.finance.app.expense.model.ExpenseCategory;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

public interface ExpenseCategoryRepository extends ListCrudRepository<ExpenseCategory, Long> {
    @Modifying
    @Query("""
            INSERT INTO expense_category (id, expense_type_id, label, description)
            VALUES (:#{#expenseCategory.id}, :#{#expenseCategory.expense_type_id}, :#{#expenseCategory.label}, :#{#expenseCategory.description})
            """)
    void insert(@Param("expenseCategory") ExpenseCategory expenseCategory);
}
