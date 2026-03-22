package com.vphilip.finance.app.transfer.controller;

import com.vphilip.finance.app.transfer.dto.TransferCategoryDTO;
import com.vphilip.finance.app.transfer.model.TransferCategory;
import com.vphilip.finance.app.transfer.repository.TransferCategoryRepository;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/transfercategories")
public class TransferCategoryController {
    private final TransferCategoryRepository transferCategoryRepository;

    public TransferCategoryController(TransferCategoryRepository transferCategoryRepository) {
        this.transferCategoryRepository = transferCategoryRepository;
    }

    @GetMapping("")
    public Object getTransferCategories() {
        return transferCategoryRepository.findAllWithType();
    }

    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    @PostMapping("")
    TransferCategory createTransferCategory(@RequestBody TransferCategory  transferCategory) {
        transferCategoryRepository.insert(transferCategory);
        return transferCategory;
    }

    @DeleteMapping("/{id}")
    TransferCategory deleteTransferCategory(@PathVariable Long id) {
        Optional<TransferCategory> transferCategory = transferCategoryRepository.findById(id);
        if(transferCategory.isPresent()) {
            transferCategoryRepository.deleteById(id);
        } else {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND);
        }
        return transferCategory.get();
    }
}