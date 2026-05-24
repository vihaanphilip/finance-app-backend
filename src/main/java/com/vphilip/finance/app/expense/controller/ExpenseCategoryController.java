package com.vphilip.finance.app.expense.controller;

import com.vphilip.finance.app.expense.model.ExpenseCategory;
import com.vphilip.finance.app.expense.repository.ExpenseCategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        return expenseCategoryRepository.save(expenseCategory);
    }

    @PutMapping("/{id}")
    public Object updateExpenseCategory(@PathVariable Long id, @RequestBody ExpenseCategory expenseCategory) {
        if (!expenseCategoryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        ExpenseCategory updatedCategory = new ExpenseCategory(
                id,
                expenseCategory.getExpenseTypeId(),
                expenseCategory.getLabel(),
                expenseCategory.getDescription()
        );
        return expenseCategoryRepository.save(updatedCategory);
    }

    @DeleteMapping("/{id}")
    ExpenseCategory deleteExpenseCategory(@PathVariable Long id) {
        Optional<ExpenseCategory> expenseCategory = expenseCategoryRepository.findById(id);
        if (expenseCategory.isPresent()) {
            expenseCategoryRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return expenseCategory.get();
    }

    @PostMapping("/{id}")
    ExpenseCategory updateExpenseCategoryPost(@PathVariable Long id, @RequestBody ExpenseCategory expenseCategory) {
        if (!id.equals(expenseCategory.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (expenseCategoryRepository.existsById(id)) {
            return expenseCategoryRepository.save(expenseCategory);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
