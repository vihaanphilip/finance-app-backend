package com.vphilip.finance.app.expense.controller;

import com.vphilip.finance.app.expense.model.ExpenseType;
import com.vphilip.finance.app.expense.repository.ExpenseTypeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expensetypes")
public class ExpenseTypeController {
    private final ExpenseTypeRepository expenseTypeRepository;

    public ExpenseTypeController(ExpenseTypeRepository expenseTypeRepository) {
        this.expenseTypeRepository = expenseTypeRepository;
    }

    @GetMapping("")
    List<ExpenseType> getExpenseTypes() { return expenseTypeRepository.findAll(); }
}
