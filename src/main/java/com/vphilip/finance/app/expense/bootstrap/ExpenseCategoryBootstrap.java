package com.vphilip.finance.app.expense.bootstrap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vphilip.finance.app.expense.model.ExpenseCategory;
import com.vphilip.finance.app.expense.model.ExpenseCategoryList;
import com.vphilip.finance.app.expense.repository.ExpenseCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class ExpenseCategoryBootstrap implements CommandLineRunner {
    @Value("${app.bootstrap-expense-data:false}")
    private boolean bootstrapEnabled;

    private static final Logger log = LoggerFactory.getLogger(ExpenseCategoryBootstrap.class);
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final ObjectMapper objectMapper;

    public ExpenseCategoryBootstrap(ExpenseCategoryRepository expenseCategoryRepository, ObjectMapper objectMapper) {
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!bootstrapEnabled) {
            log.info("ExpenseCategoryBootstrap is disabled. Skipping data loading.");
            return;
        }
        log.info("ExpenseCategoryBootstrap is enabled. Loading data...");
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/expense_categories.json")) {
            ExpenseCategoryList allExpenseCategories = objectMapper.readValue(inputStream, ExpenseCategoryList.class);
            log.info("Reading {} expense categories from JSON data and saving to database.", allExpenseCategories.expenseCategories().size());
            for (ExpenseCategory expenseCategory : allExpenseCategories.expenseCategories()) {
                log.info("Processing expense category: id={}, label={}, description={}, expense_type_id={}",
                        expenseCategory.getId(), expenseCategory.getLabel(), expenseCategory.getDescription(), expenseCategory.getExpenseTypeId());
                expenseCategoryRepository.save(expenseCategory);
                log.info("Saved expense category with id {}", expenseCategory.getId());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON data", e);
        }
    }
}
