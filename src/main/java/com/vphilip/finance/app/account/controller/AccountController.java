package com.vphilip.finance.app.account.controller;

import com.vphilip.finance.app.account.model.Account;
import com.vphilip.finance.app.account.model.AccountDTO;
import com.vphilip.finance.app.account.repository.AccountRepository;
import com.vphilip.finance.app.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("")
    List<AccountDTO> getAccounts(@AuthenticationPrincipal User user) {
        return accountRepository.findAllWithType(user.getId());
    }

    @GetMapping("/{id}")
    Account getAccountById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!user.getId().equals(account.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return account;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createAccount(@RequestBody Account account, @AuthenticationPrincipal User user) {
        account.setUser_id(user.getId());
        accountRepository.save(account);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Account updateAccount(@PathVariable Long id, @RequestBody Account account, @AuthenticationPrincipal User user) {
        Account existing = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        if (!user.getId().equals(existing.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Account updated = new Account(
                existing.getId(),
                account.getName(),
                account.getDescription(),
                account.getAccount_type_id(),
                account.getStarting_amount(),
                existing.getUser_id()
        );
        return accountRepository.save(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Account deleteAccount(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!user.getId().equals(account.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        accountRepository.deleteById(id);
        return account;
    }
}
