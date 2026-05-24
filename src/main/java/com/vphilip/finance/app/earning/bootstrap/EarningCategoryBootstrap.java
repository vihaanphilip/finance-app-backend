package com.vphilip.finance.app.earning.bootstrap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vphilip.finance.app.earning.model.EarningCategory;
import com.vphilip.finance.app.earning.model.EarningCategoryList;
import com.vphilip.finance.app.earning.repository.EarningCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@ConditionalOnProperty(name = "app.bootstrap-earnings-data", havingValue = "true")
public class EarningCategoryBootstrap implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(EarningCategoryBootstrap.class);
    private final EarningCategoryRepository earningCategoryRepository;
    private final ObjectMapper objectMapper;

    public EarningCategoryBootstrap(EarningCategoryRepository earningCategoryRepository, ObjectMapper objectMapper) {
        this.earningCategoryRepository = earningCategoryRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("EarningCategoryBootstrap is enabled. Loading data...");
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/earning_categories.json")) {
            EarningCategoryList allEarningCategories = objectMapper.readValue(inputStream, EarningCategoryList.class);
            log.info("Reading {} earning categories from JSON data and saving to database.", allEarningCategories.earningCategories().size());
            for (EarningCategory earningCategory : allEarningCategories.earningCategories()) {
                log.info("Processing earning category: label={}, description={}, earning_type_id={}",
                        earningCategory.getLabel(), earningCategory.getDescription(), earningCategory.getEarningTypeId());
                earningCategoryRepository.save(earningCategory);
                log.info("Saved earning category: {}", earningCategory.getLabel());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON data", e);
        }
    }
}
