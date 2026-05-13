package com.vphilip.finance.app.expense.controller;


import com.vphilip.finance.app.expense.model.Expense;
import com.vphilip.finance.app.expense.repository.ExpenseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        // Create new expense with current timestamps
        Expense newExpense = new Expense(
            expense.id(),
            expense.account_id(),
            expense.description(),
            expense.amount(),
            expense.expense_category_id(),
            expense.transaction_date(),
            java.time.LocalDateTime.now(), // Set created_at to current time
            java.time.LocalDateTime.now()  // Set last_modified_at to current time
        );

        return expenseRepository.save(newExpense);
    }

    @PostMapping("/{id}")
    public Expense update(@PathVariable Long id, @RequestBody Expense expense) {
        if (!expenseRepository.existsById(id)) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND);
        }

        Expense existingExpense = expenseRepository.findById(id).orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND));

        // Create updated expense with the ID from path and updated last_modified_at
        Expense updatedExpense = new Expense(
                id,
                expense.account_id(),
                expense.description(),
                expense.amount(),
                expense.expense_category_id(),
                expense.transaction_date(),
                existingExpense.created_at(),  // Preserve original created_at
                java.time.LocalDateTime.now()  // Update last_modified_at to current time
        );

        return expenseRepository.save(updatedExpense);
    }

    @DeleteMapping("/{id}")
    public Expense delete(@PathVariable Long id) {
        Expense existingExpense = expenseRepository.findById(id).orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND));
        expenseRepository.deleteById(id);
        return existingExpense;
    }
}
