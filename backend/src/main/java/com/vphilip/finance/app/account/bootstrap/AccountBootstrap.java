package com.vphilip.finance.app.account.bootstrap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vphilip.finance.app.account.model.Account;
import com.vphilip.finance.app.account.model.Accounts;
import com.vphilip.finance.app.account.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class AccountBootstrap implements CommandLineRunner {

    @Value("${app.bootstrap-data:false}")
    private boolean bootstrapEnabled;

    private static final Logger log = LoggerFactory.getLogger(AccountBootstrap.class);
    private final ObjectMapper objectMapper;
    private final AccountRepository accountRepository;

    public AccountBootstrap(ObjectMapper objectMapper, AccountRepository accountRepository) {
        this.objectMapper = objectMapper;
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!bootstrapEnabled) {
            log.info("AccountBootstrap is disabled. Skipping data loading.");
            return;
        }
        log.info("AccountBootstrap is enabled. Loading data...");
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/accounts.json")) {
            Accounts allAccounts = objectMapper.readValue(inputStream, Accounts.class);
            log.info("Reading {} accounts from JSON data and saving to in-memory collection.", allAccounts.accounts().size());
            for (Account account : allAccounts.accounts()) {
                accountRepository.save(account);
                log.info("Saved account with name {}", account.name());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON data", e);
        }
    }
}
