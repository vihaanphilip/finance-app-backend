package com.vphilip.finance.app.expense.bootstrap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vphilip.finance.app.expense.model.ExpenseType;
import com.vphilip.finance.app.expense.model.ExpenseTypeList;
import com.vphilip.finance.app.expense.repository.ExpenseTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class ExpenseTypeBootstrap implements CommandLineRunner {
    @Value("${app.bootstrap-expense-data:false}")
    private boolean bootstrapEnabled;

    private static final Logger log = LoggerFactory.getLogger(ExpenseTypeBootstrap.class);
    private final ExpenseTypeRepository expenseTypeRepository;
    private final ObjectMapper objectMapper;

    public ExpenseTypeBootstrap(ExpenseTypeRepository expenseTypeRepository, ObjectMapper objectMapper) {
        this.expenseTypeRepository = expenseTypeRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!bootstrapEnabled) {
            log.info("ExpenseTypeBootstrap is disabled. Skipping data loading.");
            return;
        }
        log.info("ExpenseTypeBootstrap is enabled. Loading data...");
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/expense_types.json")) {
            ExpenseTypeList allExpenseTypes = objectMapper.readValue(inputStream, ExpenseTypeList.class);
            log.info("Reading {} expense types from JSON data and saving to in-memory collection.", allExpenseTypes.expenseTypes().size());
            for (ExpenseType expenseType : allExpenseTypes.expenseTypes()) {
                log.info("Processing expense type: id={}, label={}, description={}",
                        expenseType.getId(), expenseType.getLabel(), expenseType.getDescription());
                if (!expenseTypeRepository.existsById(expenseType.getId())) {
                    expenseTypeRepository.insert(expenseType);
                    log.info("Inserted expense type with id {}", expenseType.getId());
                } else {
                    expenseTypeRepository.save(expenseType);
                    log.info("Updated expense type with id {}", expenseType.getId());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON data", e);
        }
    }
}
