package com.vphilip.finance.app.earning.bootstrap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vphilip.finance.app.earning.model.Earning;
import com.vphilip.finance.app.earning.model.Earnings;
import com.vphilip.finance.app.earning.repository.EarningRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

@Component
public class EarningBootstrap implements CommandLineRunner {

    @Value("${app.bootstrap-transaction-data:false}")
    private boolean bootstrapEnabled;

    private static final Logger log = LoggerFactory.getLogger(EarningBootstrap.class);
    private final EarningRepository earningRepository;
    private final ObjectMapper objectMapper;

    public EarningBootstrap(EarningRepository earningRepository, ObjectMapper objectMapper) {
        this.earningRepository = earningRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!bootstrapEnabled) {
            log.info("EarningBootstrap is disabled. Skipping data loading.");
            return;
        }
        log.info("EarningBootstrap is enabled. Loading data...");
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/earnings.json")) {
            Earnings allEarnings = objectMapper.readValue(inputStream, Earnings.class);
            log.info("Reading {} earnings from JSON data and saving to database.", allEarnings.earnings().size());
            LocalDateTime now = LocalDateTime.now();
            for (Earning earning : allEarnings.earnings()) {
                Earning earningWithTimestamps = new Earning(
                        earning.id(),
                        earning.account_id(),
                        earning.description(),
                        earning.amount(),
                        earning.earning_type_id(),
                        earning.earning_category_id(),
                        now,
                        now
                );
                log.info("Processing earning: description={}, amount={}",
                        earningWithTimestamps.description(), earningWithTimestamps.amount());
                earningRepository.save(earningWithTimestamps);
                log.info("Saved earning: {}", earningWithTimestamps.description());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON data", e);
        }
    }
}
