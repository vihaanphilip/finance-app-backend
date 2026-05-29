package com.vphilip.finance.app.budget.service;

import com.vphilip.finance.app.budget.repository.BudgetAccountRepository;
import com.vphilip.finance.app.budget.repository.BudgetEntryRepository;
import com.vphilip.finance.app.budget.repository.BudgetRepository;
import com.vphilip.finance.app.earning.repository.EarningRepository;
import com.vphilip.finance.app.expense.repository.ExpenseRepository;
import com.vphilip.finance.app.transfer.repository.TransferRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetAccountRepository budgetAccountRepository;
    private final BudgetEntryRepository budgetEntryRepository;
    private final EarningRepository earningRepository;
    private final ExpenseRepository expenseRepository;
    private final TransferRepository transferRepository;

    /**
     * Deletes a budget and performs cascade cleanup:
     * 1. Delete all budget_entry rows for this budget.
     * 2. Delete all budget_account rows for this budget.
     * 3. Null-out budget_id on attributed earnings, expenses, transfers (preserves transaction history).
     * 4. Delete the budget itself.
     */
    @Transactional
    public void deleteBudget(Long budgetId) {
        budgetEntryRepository.deleteAllByBudgetId(budgetId);
        budgetAccountRepository.deleteAllByBudgetId(budgetId);
        earningRepository.clearBudgetId(budgetId);
        expenseRepository.clearBudgetId(budgetId);
        transferRepository.clearBudgetId(budgetId);
        budgetRepository.deleteById(budgetId);
    }
}
