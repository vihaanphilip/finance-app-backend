package com.vphilip.finance.app.account.bootstrap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vphilip.finance.app.account.model.Account;
import com.vphilip.finance.app.account.model.Accounts;
import com.vphilip.finance.app.account.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@ConditionalOnProperty(name = "app.bootstrap-data", havingValue = "true")
public class AccountBootstrap implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AccountBootstrap.class);
    private final ObjectMapper objectMapper;
    private final AccountRepository accountRepository;

    public AccountBootstrap(ObjectMapper objectMapper, AccountRepository accountRepository) {
        this.objectMapper = objectMapper;
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("AccountBootstrap is enabled. Loading data...");
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/accounts.json")) {
            Accounts allAccounts = objectMapper.readValue(inputStream, Accounts.class);
            log.info("Reading {} accounts from JSON data and saving to in-memory collection.", allAccounts.accounts().size());
            for (Account account : allAccounts.accounts()) {
                accountRepository.save(account);
                log.info("Saved account with name {}", account.getName());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON data", e);
        }
    }
}
