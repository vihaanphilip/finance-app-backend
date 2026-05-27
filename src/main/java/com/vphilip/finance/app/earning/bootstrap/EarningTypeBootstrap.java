package com.vphilip.finance.app.earning.bootstrap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vphilip.finance.app.earning.model.EarningType;
import com.vphilip.finance.app.earning.model.EarningTypeList;
import com.vphilip.finance.app.earning.repository.EarningTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@ConditionalOnProperty(name = "app.bootstrap-earnings-data", havingValue = "true")
@Slf4j
public class EarningTypeBootstrap implements CommandLineRunner {

    private final EarningTypeRepository earningTypeRepository;
    private final ObjectMapper objectMapper;

    public EarningTypeBootstrap(EarningTypeRepository earningTypeRepository, ObjectMapper objectMapper) {
        this.earningTypeRepository = earningTypeRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("EarningTypeBootstrap is enabled. Loading data...");
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/earning_types.json")) {
            EarningTypeList allEarningTypes = objectMapper.readValue(inputStream, EarningTypeList.class);
            log.info("Reading {} runs from JSON data and saving to in-memory collection.", allEarningTypes.earningTypes().size());
            for (EarningType earningType : allEarningTypes.earningTypes()) {
                log.info("Processing earning type: id={}, label={}, description={}",
                        earningType.getId(), earningType.getLabel(), earningType.getDescription());
                earningTypeRepository.save(earningType);
                log.info("Saved earning type with id {}", earningType.getId());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON data", e);
        }
    }
}
