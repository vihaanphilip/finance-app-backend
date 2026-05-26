package com.vphilip.finance.app.earning.controller;

import com.vphilip.finance.app.account.model.Account;
import com.vphilip.finance.app.account.repository.AccountRepository;
import com.vphilip.finance.app.earning.dto.EarningDTO;
import com.vphilip.finance.app.earning.exception.CsvProcessingException;
import com.vphilip.finance.app.earning.model.Earning;
import com.vphilip.finance.app.earning.model.EarningCategory;
import com.vphilip.finance.app.earning.repository.EarningCategoryRepository;
import com.vphilip.finance.app.earning.repository.EarningRepository;
import com.vphilip.finance.app.earning.service.EarningService;
import com.vphilip.finance.app.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final AccountRepository accountRepository;
    private final EarningCategoryRepository earningCategoryRepository;

    public EarningController(EarningRepository earningRepository, EarningService earningCsvService,
                             AccountRepository accountRepository, EarningCategoryRepository earningCategoryRepository) {
        this.earningRepository = earningRepository;
        this.earningCsvService = earningCsvService;
        this.accountRepository = accountRepository;
        this.earningCategoryRepository = earningCategoryRepository;
    }

    private void verifyAccountOwnership(Long accountId, Integer userId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!userId.equals(account.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private void verifyCategoryOwnership(Long categoryId, Integer userId) {
        EarningCategory category = earningCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!userId.equals(category.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public List<EarningDTO> findAll(@AuthenticationPrincipal User user) {
        return earningRepository.findAllWithLabels(user.getId());
    }

    @GetMapping("/{id}")
    public Earning findById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Earning earning = earningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Earning not found"));
        verifyAccountOwnership(earning.getAccount_id(), user.getId());
        return earning;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Earning create(@RequestBody Earning earning, @AuthenticationPrincipal User user) {
        verifyAccountOwnership(earning.getAccount_id(), user.getId());
        if (earning.getEarning_category_id() != null) {
            verifyCategoryOwnership(earning.getEarning_category_id(), user.getId());
        }
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
    public Earning updateEarning(@RequestBody Earning earning, @PathVariable Long id, @AuthenticationPrincipal User user) {
        if (!id.equals(earning.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in path must match ID in request body");
        }
        Earning existingEarning = earningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Earning not found"));
        verifyAccountOwnership(existingEarning.getAccount_id(), user.getId());
        verifyAccountOwnership(earning.getAccount_id(), user.getId());
        if (earning.getEarning_category_id() != null) {
            verifyCategoryOwnership(earning.getEarning_category_id(), user.getId());
        }
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
    public void update(@RequestBody Earning earning, @PathVariable Long id, @AuthenticationPrincipal User user) {
        Earning existingEarning = earningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Earning not found"));
        verifyAccountOwnership(existingEarning.getAccount_id(), user.getId());
        verifyAccountOwnership(earning.getAccount_id(), user.getId());
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
    public Earning delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Earning earning = earningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Earning not found"));
        verifyAccountOwnership(earning.getAccount_id(), user.getId());
        earningRepository.deleteById(id);
        return earning;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Earning> uploadCsv(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal User user) throws IOException {
        return earningCsvService.processCSVFile(file, user.getId());
    }

    @ExceptionHandler(CsvProcessingException.class)
    public ResponseEntity<String> handleCsvProcessingException(CsvProcessingException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
