package com.vphilip.finance.app.transfer.controller;

import com.vphilip.finance.app.transfer.dto.TransferCategoryDTO;
import com.vphilip.finance.app.transfer.model.TransferCategory;
import com.vphilip.finance.app.transfer.repository.TransferCategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/transfercategories")
public class TransferCategoryController {
    private final TransferCategoryRepository transferCategoryRepository;

    public TransferCategoryController(TransferCategoryRepository transferCategoryRepository) {
        this.transferCategoryRepository = transferCategoryRepository;
    }

    @GetMapping("")
    public List<TransferCategoryDTO> getTransferCategories() {
        return transferCategoryRepository.findAllWithType();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    TransferCategory createTransferCategory(@RequestBody TransferCategory transferCategory) {
        return transferCategoryRepository.save(transferCategory);
    }

    @DeleteMapping("/{id}")
    TransferCategory deleteTransferCategory(@PathVariable Long id) {
        Optional<TransferCategory> transferCategory = transferCategoryRepository.findById(id);
        if (transferCategory.isPresent()) {
            transferCategoryRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return transferCategory.get();
    }

    @PostMapping("/{id}")
    TransferCategory updateTransferCategory(@PathVariable Long id, @RequestBody TransferCategory transferCategory) {
        if (!id.equals(transferCategory.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (transferCategoryRepository.findById(id).isPresent()) {
            return transferCategoryRepository.save(transferCategory);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
