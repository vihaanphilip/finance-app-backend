package com.vphilip.finance.app.transfer.controller;

import com.vphilip.finance.app.transfer.dto.TransferCategoryDTO;
import com.vphilip.finance.app.transfer.repository.TransferCategoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}