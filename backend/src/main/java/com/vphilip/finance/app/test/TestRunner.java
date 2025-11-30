package com.vphilip.finance.app.test;

import com.vphilip.finance.app.earning.model.EarningSummary;
import com.vphilip.finance.app.earning.service.EarningService;
import com.vphilip.finance.app.earning.service.EarningSummaryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

@Component
public class TestRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(TestRunner.class);

    @Value("${app.bootstrap-test-runner:false}")
    private boolean bootstrapEnabled;

    private final EarningSummaryService earningSummaryService;

    public TestRunner(EarningSummaryService earningSummaryService) {
        this.earningSummaryService = earningSummaryService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!bootstrapEnabled) {
            log.info("EarningBootstrap is disabled. Skipping data loading.");
            return;
        }
        log.info("=== TestRunner Started ===");

        // Add your test code here

        // Test: getEarningsSummaryByMonth action
        EarningSummary earningSummary = earningSummaryService.getEarningsSummaryByMonth(LocalDate.of(2025,10,1));
        log.info("Earning Summary for 2025-10: " + earningSummary.total_earnings());

        log.info("=== TestRunner Completed ===");
    }
}
