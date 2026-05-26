package com.vphilip.finance.app.earning.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.vphilip.finance.app.earning.dto.EarningCsvDto;
import com.vphilip.finance.app.earning.exception.CsvProcessingException;
import com.vphilip.finance.app.earning.model.Earning;
import com.vphilip.finance.app.earning.model.EarningCategorySummary;
import com.vphilip.finance.app.earning.model.EarningSummary;
import com.vphilip.finance.app.earning.repository.EarningRepository;
import com.vphilip.finance.app.earning.repository.EarningSummaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EarningSummaryService {
    private final EarningSummaryRepository earningSummaryRepository;

    public EarningSummaryService(EarningSummaryRepository earningSummaryRepository) {
        this.earningSummaryRepository = earningSummaryRepository;
    }

    public EarningSummary getEarningsSummaryByMonth(LocalDate targetDate, Integer userId) {
        if (targetDate == null) {
            throw new IllegalArgumentException("targetDate cannot be null");
        }
        int year = targetDate.getYear();
        int month = targetDate.getMonthValue();
        BigDecimal total = earningSummaryRepository.totalEarningsForMonth(year, month, userId);
        List<EarningCategorySummary> categories = earningSummaryRepository.totalEarningsForMonthByCategory(year, month, userId);
        return new EarningSummary(targetDate, total, categories);
    }
}
