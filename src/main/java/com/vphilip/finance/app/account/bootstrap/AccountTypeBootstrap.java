package com.vphilip.finance.app.account.bootstrap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vphilip.finance.app.account.model.AccountType;
import com.vphilip.finance.app.account.model.AccountTypes;
import com.vphilip.finance.app.account.repository.AccountTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class AccountTypeBootstrap implements CommandLineRunner {

    @Value("${app.bootstrap-data:false}")
    private boolean bootstrapEnabled;

    private static final Logger log = LoggerFactory.getLogger(AccountTypeBootstrap.class);
    private final ObjectMapper objectMapper;
    private final AccountTypeRepository accountTypeRepository;

    public AccountTypeBootstrap(ObjectMapper objectMapper, AccountTypeRepository accountRepository) {
        this.objectMapper = objectMapper;
        this.accountTypeRepository = accountRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!bootstrapEnabled) {
            log.info("AccountTypeBootstrap is disabled. Skipping data loading.");
            return;
        }
        log.info("AccountTypeBootstrap is enabled. Loading data...");
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/account_types.json")) {
            AccountTypes allAccountTypes = objectMapper.readValue(inputStream, AccountTypes.class);
            log.info("Reading {} runs from JSON data and saving to in-memory collection.", allAccountTypes.accountTypes().size());
            for (AccountType accountType : allAccountTypes.accountTypes()) {
                // if account type with this id does not exist, insert it
                if (!accountTypeRepository.existsById(accountType.getId())) {
                    accountTypeRepository.insert(accountType);
                    log.info("Inserted account type with id {}", accountType.getId());
                } else {
                    // update existing account type
                    accountTypeRepository.save(accountType);
                    log.info("Updated account type with id {}", accountType.getId());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON data", e);
        }
    }
}

