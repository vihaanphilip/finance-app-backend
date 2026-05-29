package com.vphilip.finance.app.expense.controller;

import com.vphilip.finance.app.account.model.Account;
import com.vphilip.finance.app.account.repository.AccountRepository;
import com.vphilip.finance.app.budget.model.Budget;
import com.vphilip.finance.app.budget.repository.BudgetRepository;
import com.vphilip.finance.app.expense.model.Expense;
import com.vphilip.finance.app.expense.model.ExpenseCategory;
import com.vphilip.finance.app.expense.repository.ExpenseCategoryRepository;
import com.vphilip.finance.app.expense.repository.ExpenseRepository;
import com.vphilip.finance.app.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseController {
    private final ExpenseRepository expenseRepository;
    private final AccountRepository accountRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final BudgetRepository budgetRepository;

    public ExpenseController(ExpenseRepository expenseRepository, AccountRepository accountRepository,
                             ExpenseCategoryRepository expenseCategoryRepository,
                             BudgetRepository budgetRepository) {
        this.expenseRepository = expenseRepository;
        this.accountRepository = accountRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.budgetRepository = budgetRepository;
    }

    private void verifyAccountOwnership(Long accountId, Integer userId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!userId.equals(account.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private void verifyCategoryOwnership(Long categoryId, Integer userId) {
        ExpenseCategory category = expenseCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!userId.equals(category.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private void verifyBudgetOwnership(Long budgetId, Integer userId) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!userId.equals(budget.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public Object findAll(@AuthenticationPrincipal User user) {
        return expenseRepository.findAllWithLabels(user.getId());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Expense create(@RequestBody Expense expense, @AuthenticationPrincipal User user) {
        verifyAccountOwnership(expense.getAccount_id(), user.getId());
        if (expense.getExpense_category_id() != null) {
            verifyCategoryOwnership(expense.getExpense_category_id(), user.getId());
        }
        if (expense.getBudget_id() != null) {
            verifyBudgetOwnership(expense.getBudget_id(), user.getId());
        }
        Expense newExpense = new Expense(
            expense.getId(),
            expense.getAccount_id(),
            expense.getDescription(),
            expense.getAmount(),
            expense.getExpense_category_id(),
            expense.getTransaction_date(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            expense.getBudget_id()
        );
        return expenseRepository.save(newExpense);
    }

    @PostMapping("/{id}")
    public Expense update(@PathVariable Long id, @RequestBody Expense expense, @AuthenticationPrincipal User user) {
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        verifyAccountOwnership(existingExpense.getAccount_id(), user.getId());
        verifyAccountOwnership(expense.getAccount_id(), user.getId());
        if (expense.getExpense_category_id() != null) {
            verifyCategoryOwnership(expense.getExpense_category_id(), user.getId());
        }
        if (expense.getBudget_id() != null) {
            verifyBudgetOwnership(expense.getBudget_id(), user.getId());
        }
        Expense updatedExpense = new Expense(
                id,
                expense.getAccount_id(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getExpense_category_id(),
                expense.getTransaction_date(),
                existingExpense.getCreated_at(),
                LocalDateTime.now(),
                expense.getBudget_id()
        );
        return expenseRepository.save(updatedExpense);
    }

    @DeleteMapping("/{id}")
    public Expense delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        verifyAccountOwnership(existingExpense.getAccount_id(), user.getId());
        expenseRepository.deleteById(id);
        return existingExpense;
    }
}
