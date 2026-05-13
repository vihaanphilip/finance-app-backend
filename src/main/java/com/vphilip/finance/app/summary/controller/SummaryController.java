package com.vphilip.finance.app.summary.controller;

import com.vphilip.finance.app.summary.dto.AccountSummaryDTO;
import com.vphilip.finance.app.summary.repository.AccountSummaryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/summary")
public class SummaryController {

    private final AccountSummaryRepository accountSummaryRepository;

    public SummaryController(AccountSummaryRepository accountSummaryRepository) {
        this.accountSummaryRepository = accountSummaryRepository;
    }

    @GetMapping("/accounts")
    public List<AccountSummaryDTO> getAccountSummaries(
            @RequestParam LocalDate start_date,
            @RequestParam LocalDate end_date
    ) {
        return accountSummaryRepository.findAccountSummaries(start_date, end_date);
    }
}

