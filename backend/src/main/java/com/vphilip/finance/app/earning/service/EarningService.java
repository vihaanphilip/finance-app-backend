package com.vphilip.finance.app.earning.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.vphilip.finance.app.earning.exception.CsvProcessingException;
import com.vphilip.finance.app.earning.model.Earning;
import com.vphilip.finance.app.earning.dto.EarningCsvDto;
import com.vphilip.finance.app.earning.model.EarningSummary;
import com.vphilip.finance.app.earning.repository.EarningRepository;
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
public class EarningService {

    private final EarningRepository earningRepository;

    public EarningService(EarningRepository earningRepository) {
        this.earningRepository = earningRepository;
    }

    public List<Earning> processCSVFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new CsvProcessingException("Uploaded file is empty");
        }

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<EarningCsvDto> csvToBean = new CsvToBeanBuilder<EarningCsvDto>(reader)
                    .withType(EarningCsvDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .withSeparator(',')
                    .build();

            List<Earning> earnings = csvToBean.parse().stream()
                .map(dto -> {
                    LocalDateTime now = LocalDateTime.now();
                    return new Earning(
                        dto.getId(),
                        dto.getAccount_id(),
                        dto.getDescription(),
                        dto.getAmount(),
                        dto.getEarning_type_id(),
                        dto.getEarning_category_id(),
                        dto.getCreated_at(),
                        now
                    );
                })
                .toList();

            if (earnings.isEmpty()) {
                throw new CsvProcessingException("No earnings found in CSV file");
            }

            return earningRepository.saveAll(earnings);
        } catch (IOException e) {
            throw new CsvProcessingException("Failed to read CSV file: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new CsvProcessingException("Error processing CSV file: " + e.getMessage() +
                (e.getCause() != null ? ". Cause: " + e.getCause().getMessage() : ""), e);
        }
    }

    public EarningSummary getEarningsSummaryByMonth(LocalDate targetDate) {
        if (targetDate == null) {
            throw new IllegalArgumentException("targetDate cannot be null");
        }
        int year = targetDate.getYear();
        int month = targetDate.getMonthValue();
        BigDecimal total = earningRepository.totalEarningsForMonth(year, month);
        return new EarningSummary(targetDate, total);
    }
}
