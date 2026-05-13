// java
package com.vphilip.finance.app.earning.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.vphilip.finance.app.earning.exception.CsvProcessingException;
import com.vphilip.finance.app.earning.model.Earning;
import com.vphilip.finance.app.earning.dto.EarningCsvDto;
import com.vphilip.finance.app.earning.repository.EarningRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class EarningService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private final EarningRepository earningRepository;

    public EarningService(EarningRepository earningRepository) {
        this.earningRepository = earningRepository;
    }

    public List<Earning> processCSVFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new CsvProcessingException("Uploaded file is empty");
        }

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            CsvToBean<EarningCsvDto> csvToBean = new CsvToBeanBuilder<EarningCsvDto>(reader)
                    .withType(EarningCsvDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .withSeparator(',')
                    .build();

            List<Earning> earnings = csvToBean.parse().stream()
                    .map(dto -> {
                        LocalDateTime now = LocalDateTime.now();
                        LocalDateTime createdAt = parseCreatedAt(dto.getCreated_at());
                        return new Earning(
                                dto.getId(),
                                dto.getAccount_id(),
                                dto.getDescription(),
                                dto.getAmount(),
                                dto.getEarning_type_id(),
                                dto.getEarning_category_id(),
                                dto.getTransaction_date(),
                                createdAt,
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

    private LocalDateTime parseCreatedAt(Object value) {
        if (value == null) {
            throw new CsvProcessingException("created_at cannot be null");
        }

        if (value instanceof LocalDateTime) {
            return (LocalDateTime) value;
        }

        if (value instanceof LocalDate) {
            return ((LocalDate) value).atStartOfDay();
        }

        // Normalize possible BOM/whitespace and stray characters
        String dateString = value.toString()
                .replace("\uFEFF", "") // BOM
                .trim();

        if (dateString.isEmpty()) {
            throw new CsvProcessingException("created_at cannot be empty");
        }

        // Try datetime first (handles YYYY-MM-DDTHH:MM:SS)
        try {
            return LocalDateTime.parse(dateString, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException ignored) { }

        // Fallback to date-only (YYYY-MM-DD)
        try {
            LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
            return date.atStartOfDay();
        } catch (DateTimeParseException ex) {
            throw new CsvProcessingException(
                    "Invalid date format for created_at: " + dateString +
                            ". Expected YYYY-MM-DD or ISO datetime format.", ex);
        }
    }
}
