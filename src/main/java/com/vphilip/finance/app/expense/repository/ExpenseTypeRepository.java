package com.vphilip.finance.app.expense.repository;

import com.vphilip.finance.app.expense.model.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long> {
}
