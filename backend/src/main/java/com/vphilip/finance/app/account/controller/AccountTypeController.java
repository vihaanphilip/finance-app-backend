package com.vphilip.finance.app.account.controller;

import com.vphilip.finance.app.account.model.AccountType;
import com.vphilip.finance.app.account.repository.AccountTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounttypes")
public class AccountTypeController {

    private final AccountTypeRepository accountTypeRepository;

    public AccountTypeController(AccountTypeRepository accountTypeRepository) {
        this.accountTypeRepository = accountTypeRepository;
    }
    
    @GetMapping("")
    List<AccountType> getAccountTypes() {
        return accountTypeRepository.findAll();
    }

    @GetMapping("/{id}")
    AccountType getAccountTypeById(@PathVariable Integer id) {
        Optional<AccountType> AccountType = accountTypeRepository.findById(id);
        if (AccountType.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return AccountType.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createAccountType(@RequestBody AccountType accountType) {
        accountTypeRepository.insert(accountType);
    }
    
}
