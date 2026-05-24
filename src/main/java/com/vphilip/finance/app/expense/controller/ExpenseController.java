package com.vphilip.finance.app.expense.controller;

import com.vphilip.finance.app.expense.model.Expense;
import com.vphilip.finance.app.expense.repository.ExpenseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseController {
    private final ExpenseRepository expenseRepository;

    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @GetMapping
    public Object findAll() {
        return expenseRepository.findAllWithLabels();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Expense create(@RequestBody Expense expense) {
        Expense newExpense = new Expense(
            expense.getId(),
            expense.getAccount_id(),
            expense.getDescription(),
            expense.getAmount(),
            expense.getExpense_category_id(),
            expense.getTransaction_date(),
            LocalDateTime.now(),
            LocalDateTime.now()
        );
        return expenseRepository.save(newExpense);
    }

    @PostMapping("/{id}")
    public Expense update(@PathVariable Long id, @RequestBody Expense expense) {
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Expense updatedExpense = new Expense(
                id,
                expense.getAccount_id(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getExpense_category_id(),
                expense.getTransaction_date(),
                existingExpense.getCreated_at(),
                LocalDateTime.now()
        );
        return expenseRepository.save(updatedExpense);
    }

    @DeleteMapping("/{id}")
    public Expense delete(@PathVariable Long id) {
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        expenseRepository.deleteById(id);
        return existingExpense;
    }
}
