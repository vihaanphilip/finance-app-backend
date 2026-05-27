package com.vphilip.finance.app.expense.bootstrap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vphilip.finance.app.expense.model.Expense;
import com.vphilip.finance.app.expense.model.ExpenseList;
import com.vphilip.finance.app.expense.repository.ExpenseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

@Component
@ConditionalOnProperty(name = "app.bootstrap-expense-data", havingValue = "true")
@Slf4j
public class ExpenseBootstrap implements CommandLineRunner {

    private final ExpenseRepository expenseRepository;
    private final ObjectMapper objectMapper;

    public ExpenseBootstrap(ExpenseRepository expenseRepository, ObjectMapper objectMapper) {
        this.expenseRepository = expenseRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("ExpenseBootstrap is enabled. Loading data...");
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/expenses.json")) {
            ExpenseList allExpenses = objectMapper.readValue(inputStream, ExpenseList.class);
            log.info("Reading {} expenses from JSON data and saving to database.", allExpenses.expenses().size());
            LocalDateTime now = LocalDateTime.now();
            for (Expense expense : allExpenses.expenses()) {
                Expense expenseWithTimestamps = new Expense(
                        null,
                        expense.getAccount_id(),
                        expense.getDescription(),
                        expense.getAmount(),
                        expense.getExpense_category_id(),
                        expense.getTransaction_date(),
                        now,
                        now
                );
                log.info("Processing expense: description={}, amount={}, category_id={}",
                        expenseWithTimestamps.getDescription(),
                        expenseWithTimestamps.getAmount(),
                        expenseWithTimestamps.getExpense_category_id());
                expenseRepository.save(expenseWithTimestamps);
                log.info("Saved expense: {}", expenseWithTimestamps.getDescription());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON data", e);
        }
    }
}
