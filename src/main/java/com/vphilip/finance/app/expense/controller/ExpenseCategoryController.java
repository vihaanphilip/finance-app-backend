package com.vphilip.finance.app.expense.controller;

import com.vphilip.finance.app.expense.model.ExpenseCategory;
import com.vphilip.finance.app.expense.repository.ExpenseCategoryRepository;
import com.vphilip.finance.app.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/expensecategories")
public class ExpenseCategoryController {
    private final ExpenseCategoryRepository expenseCategoryRepository;

    public ExpenseCategoryController(ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    @GetMapping("")
    public Object getExpenseCategories(@AuthenticationPrincipal User user) {
        return expenseCategoryRepository.findAllWithType(user.getId());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ExpenseCategory createExpenseCategory(@RequestBody ExpenseCategory expenseCategory, @AuthenticationPrincipal User user) {
        expenseCategory.setUser_id(user.getId());
        return expenseCategoryRepository.save(expenseCategory);
    }

    @PutMapping("/{id}")
    public Object updateExpenseCategory(@PathVariable Long id, @RequestBody ExpenseCategory expenseCategory, @AuthenticationPrincipal User user) {
        ExpenseCategory existing = expenseCategoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!user.getId().equals(existing.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        ExpenseCategory updatedCategory = new ExpenseCategory(
                id,
                expenseCategory.getExpenseTypeId(),
                expenseCategory.getLabel(),
                expenseCategory.getDescription(),
                existing.getUser_id()
        );
        return expenseCategoryRepository.save(updatedCategory);
    }

    @DeleteMapping("/{id}")
    ExpenseCategory deleteExpenseCategory(@PathVariable Long id, @AuthenticationPrincipal User user) {
        ExpenseCategory expenseCategory = expenseCategoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!user.getId().equals(expenseCategory.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        expenseCategoryRepository.deleteById(id);
        return expenseCategory;
    }

    @PostMapping("/{id}")
    ExpenseCategory updateExpenseCategoryPost(@PathVariable Long id, @RequestBody ExpenseCategory expenseCategory, @AuthenticationPrincipal User user) {
        if (!id.equals(expenseCategory.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        ExpenseCategory existing = expenseCategoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!user.getId().equals(existing.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        expenseCategory.setUser_id(existing.getUser_id());
        return expenseCategoryRepository.save(expenseCategory);
    }
}
