package com.vphilip.finance.app.expense.repository;

import com.vphilip.finance.app.expense.model.ExpenseType;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

public interface ExpenseTypeRepository extends ListCrudRepository<ExpenseType, Long> {
    @Modifying
    @Query("""
            INSERT INTO expense_type (id, label, description)
            VALUES (:#{#expenseType.id}, :#{#expenseType.label}, :#{#expenseType.description})
            """)
    void insert(@Param("expenseType") ExpenseType expenseType);
}

