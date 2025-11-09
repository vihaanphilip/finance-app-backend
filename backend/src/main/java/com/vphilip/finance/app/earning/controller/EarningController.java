package com.vphilip.finance.app.earning.controller;

import com.vphilip.finance.app.earning.dto.EarningDTO;
import com.vphilip.finance.app.earning.exception.CsvProcessingException;
import com.vphilip.finance.app.earning.model.Earning;
import com.vphilip.finance.app.earning.repository.EarningRepository;
import com.vphilip.finance.app.earning.service.EarningCsvService;
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
    private final EarningCsvService earningCsvService;

    public EarningController(EarningRepository earningRepository, EarningCsvService earningCsvService) {
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
        return earningRepository.save(earning);
    }

    @PostMapping("/{id}")
    public Earning updateEarning(@RequestBody Earning earning, @PathVariable Long id) {
        if (!id.equals(earning.id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in path must match ID in request body");
        }
        if (!earningRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Earning not found");
        }
        return earningRepository.save(earning);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Earning earning, @PathVariable Long id) {
        if (!earningRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Earning not found");
        }
        earningRepository.save(earning);
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
