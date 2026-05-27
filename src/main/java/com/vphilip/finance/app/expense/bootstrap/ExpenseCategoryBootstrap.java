package com.vphilip.finance.app.expense.bootstrap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vphilip.finance.app.expense.model.ExpenseCategory;
import com.vphilip.finance.app.expense.model.ExpenseCategoryList;
import com.vphilip.finance.app.expense.repository.ExpenseCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@ConditionalOnProperty(name = "app.bootstrap-expense-data", havingValue = "true")
@Slf4j
public class ExpenseCategoryBootstrap implements CommandLineRunner {

    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final ObjectMapper objectMapper;

    public ExpenseCategoryBootstrap(ExpenseCategoryRepository expenseCategoryRepository, ObjectMapper objectMapper) {
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
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
