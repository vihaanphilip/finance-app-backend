package com.vphilip.finance.app.earning.controller;

import com.vphilip.finance.app.earning.model.EarningSummary;
import com.vphilip.finance.app.earning.service.EarningService;
import com.vphilip.finance.app.earning.service.EarningSummaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/earnings/summary")
public class EarningSummaryController {

    private final EarningSummaryService earningSummaryService;

    public EarningSummaryController(EarningSummaryService earningSummaryService) {
        this.earningSummaryService = earningSummaryService;
    }

    @GetMapping("")
    public EarningSummary getMonthlySummary(
            @RequestParam LocalDate date
    ) {
        return earningSummaryService.getEarningsSummaryByMonth(date);
    }
}
