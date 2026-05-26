package com.vphilip.finance.app.transfer.controller;

import com.vphilip.finance.app.transfer.dto.TransferCategoryDTO;
import com.vphilip.finance.app.transfer.model.TransferCategory;
import com.vphilip.finance.app.transfer.repository.TransferCategoryRepository;
import com.vphilip.finance.app.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transfercategories")
public class TransferCategoryController {
    private final TransferCategoryRepository transferCategoryRepository;

    public TransferCategoryController(TransferCategoryRepository transferCategoryRepository) {
        this.transferCategoryRepository = transferCategoryRepository;
    }

    @GetMapping("")
    public List<TransferCategoryDTO> getTransferCategories(@AuthenticationPrincipal User user) {
        return transferCategoryRepository.findAllWithType(user.getId());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    TransferCategory createTransferCategory(@RequestBody TransferCategory transferCategory, @AuthenticationPrincipal User user) {
        transferCategory.setUser_id(user.getId());
        return transferCategoryRepository.save(transferCategory);
    }

    @DeleteMapping("/{id}")
    TransferCategory deleteTransferCategory(@PathVariable Long id, @AuthenticationPrincipal User user) {
        TransferCategory transferCategory = transferCategoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!user.getId().equals(transferCategory.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        transferCategoryRepository.deleteById(id);
        return transferCategory;
    }

    @PostMapping("/{id}")
    TransferCategory updateTransferCategory(@PathVariable Long id, @RequestBody TransferCategory transferCategory, @AuthenticationPrincipal User user) {
        if (!id.equals(transferCategory.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        TransferCategory existing = transferCategoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!user.getId().equals(existing.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        transferCategory.setUser_id(existing.getUser_id());
        return transferCategoryRepository.save(transferCategory);
    }
}
