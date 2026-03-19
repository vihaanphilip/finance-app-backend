package com.vphilip.finance.app.expense.controller;

import com.vphilip.finance.app.expense.model.ExpenseType;
import com.vphilip.finance.app.expense.repository.ExpenseTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ExpenseType createExpenseType(@RequestBody ExpenseType expenseType) {
        expenseTypeRepository.insert(expenseType);
        return expenseType;
    }
}
