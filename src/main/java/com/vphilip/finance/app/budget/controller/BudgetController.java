package com.vphilip.finance.app.budget.controller;

import com.vphilip.finance.app.account.model.Account;
import com.vphilip.finance.app.account.repository.AccountRepository;
import com.vphilip.finance.app.budget.dto.BudgetAccountDTO;
import com.vphilip.finance.app.budget.dto.BudgetDTO;
import com.vphilip.finance.app.budget.dto.BudgetSummaryDTO;
import com.vphilip.finance.app.budget.dto.BudgetTransactionsDTO;
import com.vphilip.finance.app.budget.model.Budget;
import com.vphilip.finance.app.budget.model.BudgetAccount;
import com.vphilip.finance.app.budget.model.BudgetEntry;
import com.vphilip.finance.app.budget.repository.BudgetAccountRepository;
import com.vphilip.finance.app.budget.repository.BudgetEntryRepository;
import com.vphilip.finance.app.budget.repository.BudgetRepository;
import com.vphilip.finance.app.budget.service.BudgetService;
import com.vphilip.finance.app.earning.repository.EarningRepository;
import com.vphilip.finance.app.expense.repository.ExpenseRepository;
import com.vphilip.finance.app.transfer.repository.TransferRepository;
import com.vphilip.finance.app.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/budgets")
@Slf4j
public class BudgetController {

    private final BudgetRepository budgetRepository;
    private final BudgetAccountRepository budgetAccountRepository;
    private final BudgetEntryRepository budgetEntryRepository;
    private final BudgetService budgetService;
    private final AccountRepository accountRepository;
    private final EarningRepository earningRepository;
    private final ExpenseRepository expenseRepository;
    private final TransferRepository transferRepository;

    public BudgetController(BudgetRepository budgetRepository,
                            BudgetAccountRepository budgetAccountRepository,
                            BudgetEntryRepository budgetEntryRepository,
                            BudgetService budgetService,
                            AccountRepository accountRepository,
                            EarningRepository earningRepository,
                            ExpenseRepository expenseRepository,
                            TransferRepository transferRepository) {
        this.budgetRepository = budgetRepository;
        this.budgetAccountRepository = budgetAccountRepository;
        this.budgetEntryRepository = budgetEntryRepository;
        this.budgetService = budgetService;
        this.accountRepository = accountRepository;
        this.earningRepository = earningRepository;
        this.expenseRepository = expenseRepository;
        this.transferRepository = transferRepository;
    }

    // -------------------------------------------------------------------------
    // Ownership helpers
    // -------------------------------------------------------------------------

    private Budget verifyBudgetOwnership(Long budgetId, Integer userId) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!userId.equals(budget.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return budget;
    }

    private void verifyAccountOwnership(Long accountId, Integer userId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!userId.equals(account.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    // -------------------------------------------------------------------------
    // Budget CRUD
    // -------------------------------------------------------------------------

    @GetMapping
    public List<BudgetDTO> findAll(@AuthenticationPrincipal User user) {
        log.debug("findAll budgets for user={}", user.getId());
        return budgetRepository.findAllByUserId(user.getId());
    }

    @GetMapping("/{id}")
    public Budget findById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        log.debug("findById budget id={} user={}", id, user.getId());
        return verifyBudgetOwnership(id, user.getId());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Budget create(@RequestBody Budget budget, @AuthenticationPrincipal User user) {
        log.debug("create budget name={} user={}", budget.getName(), user.getId());
        if (budget.getName() == null || budget.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "name is required");
        }
        Budget newBudget = new Budget(
                null,
                budget.getName(),
                budget.getDescription(),
                budget.getStarting_amount() != null ? budget.getStarting_amount() : BigDecimal.ZERO,
                user.getId()
        );
        return budgetRepository.save(newBudget);
    }

    @PostMapping("/{id}")
    public Budget update(@PathVariable Long id, @RequestBody Budget budget, @AuthenticationPrincipal User user) {
        log.debug("update budget id={} user={}", id, user.getId());
        verifyBudgetOwnership(id, user.getId());
        if (budget.getName() == null || budget.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "name is required");
        }
        Budget updatedBudget = new Budget(
                id,
                budget.getName(),
                budget.getDescription(),
                budget.getStarting_amount() != null ? budget.getStarting_amount() : BigDecimal.ZERO,
                user.getId()
        );
        return budgetRepository.save(updatedBudget);
    }

    @DeleteMapping("/{id}")
    public Budget delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        log.debug("delete budget id={} user={}", id, user.getId());
        Budget budget = verifyBudgetOwnership(id, user.getId());
        budgetService.deleteBudget(id);
        return budget;
    }

    // -------------------------------------------------------------------------
    // Budget summary
    // -------------------------------------------------------------------------

    @GetMapping("/{id}/summary")
    public BudgetSummaryDTO getSummary(@PathVariable Long id, @AuthenticationPrincipal User user) {
        log.debug("getSummary budget id={} user={}", id, user.getId());
        verifyBudgetOwnership(id, user.getId());
        BudgetSummaryDTO summary = budgetRepository.findSummaryByIdAndUserId(id, user.getId());
        if (summary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return summary;
    }

    // -------------------------------------------------------------------------
    // Budget ↔ Account linking
    // -------------------------------------------------------------------------

    @GetMapping("/{id}/accounts")
    public List<BudgetAccountDTO> findAccounts(@PathVariable Long id, @AuthenticationPrincipal User user) {
        log.debug("findAccounts budget id={} user={}", id, user.getId());
        verifyBudgetOwnership(id, user.getId());
        return budgetAccountRepository.findAllByBudgetId(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/accounts")
    public BudgetAccount linkAccount(@PathVariable Long id, @RequestBody BudgetAccount link,
                                     @AuthenticationPrincipal User user) {
        log.debug("linkAccount budget id={} account id={} user={}", id, link.getAccount_id(), user.getId());
        verifyBudgetOwnership(id, user.getId());
        verifyAccountOwnership(link.getAccount_id(), user.getId());
        if (budgetAccountRepository.existsByBudgetIdAndAccountId(id, link.getAccount_id())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account already linked to this budget");
        }
        BudgetAccount newLink = new BudgetAccount(null, id, link.getAccount_id());
        return budgetAccountRepository.save(newLink);
    }

    @DeleteMapping("/{id}/accounts/{accountId}")
    public BudgetAccount unlinkAccount(@PathVariable Long id, @PathVariable Long accountId,
                                       @AuthenticationPrincipal User user) {
        log.debug("unlinkAccount budget id={} account id={} user={}", id, accountId, user.getId());
        verifyBudgetOwnership(id, user.getId());
        BudgetAccount link = budgetAccountRepository.findByBudgetIdAndAccountId(id, accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        budgetAccountRepository.delete(link);
        return link;
    }

    // -------------------------------------------------------------------------
    // Budget entry ledger
    // -------------------------------------------------------------------------

    @GetMapping("/{budgetId}/entries")
    public List<BudgetEntry> findEntries(@PathVariable Long budgetId, @AuthenticationPrincipal User user) {
        log.debug("findEntries budget id={} user={}", budgetId, user.getId());
        verifyBudgetOwnership(budgetId, user.getId());
        return budgetEntryRepository.findAllByBudgetId(budgetId);
    }

    @GetMapping("/{budgetId}/entries/{id}")
    public BudgetEntry findEntry(@PathVariable Long budgetId, @PathVariable Long id,
                                 @AuthenticationPrincipal User user) {
        log.debug("findEntry budget id={} entry id={} user={}", budgetId, id, user.getId());
        verifyBudgetOwnership(budgetId, user.getId());
        BudgetEntry entry = budgetEntryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!budgetId.equals(entry.getBudget_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return entry;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{budgetId}/entries")
    public BudgetEntry createEntry(@PathVariable Long budgetId, @RequestBody BudgetEntry entry,
                                   @AuthenticationPrincipal User user) {
        log.debug("createEntry budget id={} user={}", budgetId, user.getId());
        verifyBudgetOwnership(budgetId, user.getId());
        validateEntry(entry);
        BudgetEntry newEntry = new BudgetEntry(
                null,
                budgetId,
                entry.getDirection(),
                entry.getAmount(),
                entry.getDescription(),
                entry.getTransaction_date(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        return budgetEntryRepository.save(newEntry);
    }

    @PostMapping("/{budgetId}/entries/{id}")
    public BudgetEntry updateEntry(@PathVariable Long budgetId, @PathVariable Long id,
                                   @RequestBody BudgetEntry entry, @AuthenticationPrincipal User user) {
        log.debug("updateEntry budget id={} entry id={} user={}", budgetId, id, user.getId());
        verifyBudgetOwnership(budgetId, user.getId());
        BudgetEntry existingEntry = budgetEntryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!budgetId.equals(existingEntry.getBudget_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        validateEntry(entry);
        BudgetEntry updatedEntry = new BudgetEntry(
                id,
                budgetId,
                entry.getDirection(),
                entry.getAmount(),
                entry.getDescription(),
                entry.getTransaction_date(),
                existingEntry.getCreated_at(),
                LocalDateTime.now()
        );
        return budgetEntryRepository.save(updatedEntry);
    }

    @DeleteMapping("/{budgetId}/entries/{id}")
    public BudgetEntry deleteEntry(@PathVariable Long budgetId, @PathVariable Long id,
                                   @AuthenticationPrincipal User user) {
        log.debug("deleteEntry budget id={} entry id={} user={}", budgetId, id, user.getId());
        verifyBudgetOwnership(budgetId, user.getId());
        BudgetEntry entry = budgetEntryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!budgetId.equals(entry.getBudget_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        budgetEntryRepository.delete(entry);
        return entry;
    }

    // -------------------------------------------------------------------------
    // Attributed transactions
    // -------------------------------------------------------------------------

    @GetMapping("/{id}/transactions")
    public BudgetTransactionsDTO getTransactions(@PathVariable Long id, @AuthenticationPrincipal User user) {
        log.debug("getTransactions budget id={} user={}", id, user.getId());
        verifyBudgetOwnership(id, user.getId());
        return new BudgetTransactionsDTO(
                earningRepository.findAllByBudgetId(id, user.getId()),
                expenseRepository.findAllByBudgetId(id, user.getId()),
                transferRepository.findAllByBudgetId(id, user.getId())
        );
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    private void validateEntry(BudgetEntry entry) {
        if (entry.getDirection() == null || (!entry.getDirection().equals("IN") && !entry.getDirection().equals("OUT"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "direction must be IN or OUT");
        }
        if (entry.getAmount() == null || entry.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "amount must be greater than 0");
        }
    }
}
