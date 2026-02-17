package com.vphilip.finance.app.earning.controller;

import com.vphilip.finance.app.earning.dto.EarningDTO;
import com.vphilip.finance.app.earning.exception.CsvProcessingException;
import com.vphilip.finance.app.earning.model.Earning;
import com.vphilip.finance.app.earning.repository.EarningRepository;
import com.vphilip.finance.app.earning.service.EarningService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/earnings")
public class EarningController {

    private final EarningRepository earningRepository;
    private final EarningService earningCsvService;

    public EarningController(EarningRepository earningRepository, EarningService earningCsvService) {
        this.earningRepository = earningRepository;
        this.earningCsvService = earningCsvService;
    }

    @GetMapping
    public List<EarningDTO> findAll() {
        return earningRepository.findAllWithLabels();
    }

    @GetMapping("/{id}")
    public Earning findById(@PathVariable Long id) {
        return earningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Earning not found"));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Earning create(@RequestBody Earning earning) {
        // Create new earning with current timestamps
        Earning newEarning = new Earning(
            earning.id(),
            earning.account_id(),
            earning.description(),
            earning.amount(),
            earning.earning_type_id(),
            earning.earning_category_id(),
            earning.transaction_date(),
            java.time.LocalDateTime.now(), // Set created_at to current time
            java.time.LocalDateTime.now()  // Set last_modified_at to current time
        );

        return earningRepository.save(newEarning);
    }

    @PostMapping("/{id}")
    public Earning updateEarning(@RequestBody Earning earning, @PathVariable Long id) {
        if (!id.equals(earning.id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in path must match ID in request body");
        }
        Earning existingEarning = earningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Earning not found"));

        // Create updated earning with preserved created_at and updated last_modified_at
        Earning updatedEarning = new Earning(
            earning.id(),
            earning.account_id(),
            earning.description(),
            earning.amount(),
            earning.earning_type_id(),
            earning.earning_category_id(),
            earning.transaction_date(),
            existingEarning.created_at(), // Preserve original created_at
            java.time.LocalDateTime.now() // Set last_modified_at to current time
        );

        return earningRepository.save(updatedEarning);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Earning earning, @PathVariable Long id) {
        Earning existingEarning = earningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Earning not found"));

        // Create updated earning with preserved created_at and updated last_modified_at
        Earning updatedEarning = new Earning(
            id,
            earning.account_id(),
            earning.description(),
            earning.amount(),
            earning.earning_type_id(),
            earning.earning_category_id(),
            earning.transaction_date(),
            existingEarning.created_at(), // Preserve original created_at
            java.time.LocalDateTime.now() // Set last_modified_at to current time
        );

        earningRepository.save(updatedEarning);
    }

    @DeleteMapping("/{id}")
    public Earning delete(@PathVariable Long id) {
        Earning earning = earningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Earning not found"));
        earningRepository.deleteById(id);
        return earning;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Earning> uploadCsv(@RequestParam("file") MultipartFile file) throws IOException {
        return earningCsvService.processCSVFile(file);
    }

    @ExceptionHandler(CsvProcessingException.class)
    public ResponseEntity<String> handleCsvProcessingException(CsvProcessingException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
