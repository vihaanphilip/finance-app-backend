package com.vphilip.finance.app.transaction.bootstrap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vphilip.finance.app.transaction.model.EarningCategory;
import com.vphilip.finance.app.transaction.model.EarningCategories;
import com.vphilip.finance.app.transaction.repository.EarningCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class EarningCategoryBootstrap implements CommandLineRunner {

    @Value("${app.bootstrap-data:false}")
    private boolean bootstrapEnabled;

    private static final Logger log = LoggerFactory.getLogger(EarningCategoryBootstrap.class);
    private final EarningCategoryRepository earningCategoryRepository;
    private final ObjectMapper objectMapper;

    public EarningCategoryBootstrap(EarningCategoryRepository earningCategoryRepository, ObjectMapper objectMapper) {
        this.earningCategoryRepository = earningCategoryRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!bootstrapEnabled) {
            log.info("EarningCategoryBootstrap is disabled. Skipping data loading.");
            return;
        }
        log.info("EarningCategoryBootstrap is enabled. Loading data...");
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/earning_categories.json")) {
            EarningCategories allEarningCategories = objectMapper.readValue(inputStream, EarningCategories.class);
            log.info("Reading {} earning categories from JSON data and saving to in-memory collection.", allEarningCategories.earningCategories().size());
            for (EarningCategory earningCategory : allEarningCategories.earningCategories()) {
                log.info("Processing earning category: id={}, label={}, description={}, earning_type_id={}",
                        earningCategory.getId(), earningCategory.getLabel(), earningCategory.getDescription(), earningCategory.getEarningTypeId());
                if (!earningCategoryRepository.existsById(earningCategory.getId())) {
                    earningCategoryRepository.insert(earningCategory);
                    log.info("Inserted earning category with id {}", earningCategory.getId());
                } else {
                    earningCategoryRepository.save(earningCategory);
                    log.info("Updated earning category with id {}", earningCategory.getId());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON data", e);
        }
    }
}