package com.vphilip.finance.app.earning.controller;

import com.vphilip.finance.app.earning.model.EarningSummary;
import com.vphilip.finance.app.earning.service.EarningService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/earnings/summary")
public class EarningSummaryController {

    private final EarningService earningService;

    public EarningSummaryController(EarningService earningService) {
        this.earningService = earningService;
    }

    @GetMapping("")
    public EarningSummary getMonthlySummary(
            @RequestParam("date") LocalDate date
    ) {
        return earningService.getEarningsSummaryByMonth(date);
    }
}
