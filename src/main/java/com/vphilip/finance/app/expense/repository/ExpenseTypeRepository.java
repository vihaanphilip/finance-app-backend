package com.vphilip.finance.app.expense.repository;

import com.vphilip.finance.app.expense.model.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long> {

    @Query(value = "SELECT * FROM expense_type WHERE user_id = :userId ORDER BY id", nativeQuery = true)
    List<ExpenseType> findAllByUserId(@Param("userId") Integer userId);
}
