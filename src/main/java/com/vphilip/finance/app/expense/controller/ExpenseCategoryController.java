package com.vphilip.finance.app.expense.controller;

import com.vphilip.finance.app.expense.model.ExpenseCategory;
import com.vphilip.finance.app.expense.repository.ExpenseCategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/expensecategories")
public class ExpenseCategoryController {
    private final ExpenseCategoryRepository expenseCategoryRepository;

    public ExpenseCategoryController(ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    @GetMapping("")
    public Object getExpenseCategories() {
        return expenseCategoryRepository.findAllWithType();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ExpenseCategory createExpenseCategory(@RequestBody ExpenseCategory expenseCategory) {
        expenseCategoryRepository.insert(expenseCategory);
        return expenseCategory;
    }

    @PutMapping("/{id}")
    public Object updateExpenseCategory(@PathVariable Long id, @RequestBody ExpenseCategory expenseCategory) {
        if (!expenseCategoryRepository.existsById(id)) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND);
        }
        // Create updated category with the ID from path
        ExpenseCategory updatedCategory = new ExpenseCategory(
                id,
                expenseCategory.expense_type_id(),
                expenseCategory.label(),
                expenseCategory.description()
        );
        return expenseCategoryRepository.save(updatedCategory);
    }

    @DeleteMapping("/{id}")
    ExpenseCategory deleteExpenseCategory(@PathVariable Long id) {
        Optional<ExpenseCategory> expenseCategory = expenseCategoryRepository.findById(id);
        if (expenseCategory.isPresent()) {
            expenseCategoryRepository.deleteById(id);
        } else {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND);
        }
        return expenseCategory.get();
    }

    @PostMapping("/{id}")
    ExpenseCategory updateExpenseCategoryId(@PathVariable Long id, @RequestBody ExpenseCategory expenseCategory) {
        if (!id.equals(expenseCategory.getId())) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND);
        }
        if (expenseCategoryRepository.existsById(id)) {
            return expenseCategoryRepository.save(expenseCategory);
        } else  {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND);
        }
    }
}
