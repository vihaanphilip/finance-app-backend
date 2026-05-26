package com.vphilip.finance.app.account.controller;

import com.vphilip.finance.app.account.model.AccountType;
import com.vphilip.finance.app.account.repository.AccountTypeRepository;
import com.vphilip.finance.app.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounttypes")
public class AccountTypeController {

    private final AccountTypeRepository accountTypeRepository;

    public AccountTypeController(AccountTypeRepository accountTypeRepository) {
        this.accountTypeRepository = accountTypeRepository;
    }

    @GetMapping("")
    List<AccountType> getAccountTypes(@AuthenticationPrincipal User user) {
        return accountTypeRepository.findAllByUserId(user.getId());
    }

    @GetMapping("/{id}")
    AccountType getAccountTypeById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        AccountType accountType = accountTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!user.getId().equals(accountType.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return accountType;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createAccountType(@RequestBody AccountType accountType, @AuthenticationPrincipal User user) {
        accountType.setUser_id(user.getId());
        accountTypeRepository.save(accountType);
    }
}
