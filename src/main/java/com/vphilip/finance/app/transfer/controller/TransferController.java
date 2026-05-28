package com.vphilip.finance.app.transfer.controller;

import com.vphilip.finance.app.account.model.Account;
import com.vphilip.finance.app.account.repository.AccountRepository;
import com.vphilip.finance.app.transfer.model.Transfer;
import com.vphilip.finance.app.transfer.model.TransferCategory;
import com.vphilip.finance.app.transfer.repository.TransferCategoryRepository;
import com.vphilip.finance.app.transfer.repository.TransferRepository;
import com.vphilip.finance.app.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/transfers")
public class TransferController {
    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    private final TransferCategoryRepository transferCategoryRepository;

    public TransferController(TransferRepository transferRepository, AccountRepository accountRepository,
                              TransferCategoryRepository transferCategoryRepository) {
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
        this.transferCategoryRepository = transferCategoryRepository;
    }

    private void verifyAccountOwnership(Long accountId, Integer userId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!userId.equals(account.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private void verifyCategoryOwnership(Long categoryId, Integer userId) {
        TransferCategory category = transferCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!userId.equals(category.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public Object findAll(@AuthenticationPrincipal User user) {
        return transferRepository.findAllWithLabels(user.getId());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Transfer create(@RequestBody Transfer transfer, @AuthenticationPrincipal User user) {
        verifyAccountOwnership(transfer.getFrom_account_id(), user.getId());
        verifyAccountOwnership(transfer.getTo_account_id(), user.getId());
        if (transfer.getTransfer_category_id() != null) {
            verifyCategoryOwnership(transfer.getTransfer_category_id(), user.getId());
        }
        Transfer newTransfer = new Transfer(
            transfer.getId(),
            transfer.getFrom_account_id(),
            transfer.getTo_account_id(),
            transfer.getDescription(),
            transfer.getAmount(),
            transfer.getTransfer_category_id(),
            transfer.getTransaction_date(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            transfer.getBudget_id()
        );
        return transferRepository.save(newTransfer);
    }

    @DeleteMapping("/{id}")
    public Transfer delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Transfer existingTransfer = transferRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        verifyAccountOwnership(existingTransfer.getFrom_account_id(), user.getId());
        transferRepository.delete(existingTransfer);
        return existingTransfer;
    }

    @PostMapping("/{id}")
    public Transfer update(@PathVariable Long id, @RequestBody Transfer transfer, @AuthenticationPrincipal User user) {
        Transfer existingTransfer = transferRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        verifyAccountOwnership(existingTransfer.getFrom_account_id(), user.getId());
        verifyAccountOwnership(transfer.getFrom_account_id(), user.getId());
        verifyAccountOwnership(transfer.getTo_account_id(), user.getId());
        if (transfer.getTransfer_category_id() != null) {
            verifyCategoryOwnership(transfer.getTransfer_category_id(), user.getId());
        }
        Transfer updatedTransfer = new Transfer(
            id,
            transfer.getFrom_account_id(),
            transfer.getTo_account_id(),
            transfer.getDescription(),
            transfer.getAmount(),
            transfer.getTransfer_category_id(),
            transfer.getTransaction_date(),
            existingTransfer.getCreated_at(),
            LocalDateTime.now(),
            transfer.getBudget_id()
        );
        return transferRepository.save(updatedTransfer);
    }
}
