package com.vphilip.finance.app.expense.repository;

import com.vphilip.finance.app.expense.model.ExpenseCategory;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

public interface ExpenseCategoryRepository extends ListCrudRepository<ExpenseCategory, Long> {

    @Query("""
            SELECT ec.id, ec.expense_type_id, ec.label, ec.description, et.label as expense_type_label
            FROM expense_category ec
            LEFT JOIN expense_type et ON ec.expense_type_id = et.id
            ORDER BY ec.id
            """)
    java.util.List<com.vphilip.finance.app.expense.dto.ExpenseCategoryDTO> findAllWithType();

    @Modifying
    @Query("""
            INSERT INTO expense_category (id, expense_type_id, label, description)
            VALUES (:#{#expenseCategory.id}, :#{#expenseCategory.expense_type_id}, :#{#expenseCategory.label}, :#{#expenseCategory.description})
            """)
    void insert(@Param("expenseCategory") ExpenseCategory expenseCategory);
}
