package com.vphilip.finance.app.account.controller;

import com.vphilip.finance.app.account.model.Account;
import com.vphilip.finance.app.account.model.AccountDTO;
import com.vphilip.finance.app.account.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("")
    List<AccountDTO> getAccounts() {
        return accountRepository.findAllWithType();
    }

    @GetMapping("/{id}")
    Account getAccountById(@PathVariable Integer id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return account.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createAccount(@RequestBody Account account) {
        accountRepository.save(account);
    }
}
