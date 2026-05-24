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
        Earning newEarning = new Earning(
            earning.getId(),
            earning.getAccount_id(),
            earning.getDescription(),
            earning.getAmount(),
            earning.getEarning_type_id(),
            earning.getEarning_category_id(),
            earning.getTransaction_date(),
            java.time.LocalDateTime.now(),
            java.time.LocalDateTime.now()
        );
        return earningRepository.save(newEarning);
    }

    @PostMapping("/{id}")
    public Earning updateEarning(@RequestBody Earning earning, @PathVariable Long id) {
        if (!id.equals(earning.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in path must match ID in request body");
        }
        Earning existingEarning = earningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Earning not found"));

        Earning updatedEarning = new Earning(
            earning.getId(),
            earning.getAccount_id(),
            earning.getDescription(),
            earning.getAmount(),
            earning.getEarning_type_id(),
            earning.getEarning_category_id(),
            earning.getTransaction_date(),
            existingEarning.getCreated_at(),
            java.time.LocalDateTime.now()
        );
        return earningRepository.save(updatedEarning);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Earning earning, @PathVariable Long id) {
        Earning existingEarning = earningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Earning not found"));

        Earning updatedEarning = new Earning(
            id,
            earning.getAccount_id(),
            earning.getDescription(),
            earning.getAmount(),
            earning.getEarning_type_id(),
            earning.getEarning_category_id(),
            earning.getTransaction_date(),
            existingEarning.getCreated_at(),
            java.time.LocalDateTime.now()
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
