package com.vphilip.finance.app.expense.controller;

import com.vphilip.finance.app.expense.model.ExpenseType;
import com.vphilip.finance.app.expense.repository.ExpenseTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    @DeleteMapping("/{id}")
    ExpenseType deleteExpenseType(@PathVariable Long id) {
        Optional<ExpenseType> expenseType = expenseTypeRepository.findById(id);
        if (expenseType.isPresent()) {
            expenseTypeRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ExpenseType not found");
        }
        return expenseType.get();
    }
}
