package com.vphilip.finance.app.expense.controller;

import com.vphilip.finance.app.expense.model.ExpenseCategory;
import com.vphilip.finance.app.expense.repository.ExpenseCategoryRepository;
import org.springframework.web.bind.annotation.*;

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
}
