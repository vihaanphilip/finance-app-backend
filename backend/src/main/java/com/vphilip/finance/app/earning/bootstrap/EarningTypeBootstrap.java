package com.vphilip.finance.app.earning.bootstrap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vphilip.finance.app.earning.model.EarningType;
import com.vphilip.finance.app.earning.model.EarningTypes;
import com.vphilip.finance.app.earning.repository.EarningTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class EarningTypeBootstrap implements CommandLineRunner {

    @Value("${app.bootstrap-data:false}")
    private boolean bootstrapEnabled;

    private static final Logger log = LoggerFactory.getLogger(EarningTypeBootstrap.class);
    private final EarningTypeRepository earningTypeRepository;
    private final ObjectMapper objectMapper;

    public EarningTypeBootstrap(EarningTypeRepository earningTypeRepository, ObjectMapper objectMapper) {
        this.earningTypeRepository = earningTypeRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!bootstrapEnabled) {
            log.info("EarningTypeBootstrap is disabled. Skipping data loading.");
            return;
        }
        log.info("EarningTypeBootstrap is enabled. Loading data...");
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/earning_types.json")) {
            EarningTypes allEarningTypes = objectMapper.readValue(inputStream, EarningTypes.class);
            log.info("Reading {} runs from JSON data and saving to in-memory collection.", allEarningTypes.earningTypes().size());
            for (EarningType earningType : allEarningTypes.earningTypes()) {
                log.info("Processing earning type: id={}, label={}, description={}",
                    earningType.getId(), earningType.getLabel(), earningType.getDescription());
                if (!earningTypeRepository.existsById(earningType.getId())) {
                    earningTypeRepository.insert(earningType);
                    log.info("Inserted earning type with id {}", earningType.getId());
                } else {
                    earningTypeRepository.save(earningType);
                    log.info("Updated earning type with id {}", earningType.getId());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON data", e);
        }
    }
}
