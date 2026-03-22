package com.vphilip.finance.app.transfer.controller;

import com.vphilip.finance.app.transfer.model.TransferType;
import com.vphilip.finance.app.transfer.repository.TransferTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transfertypes")
public class TransferTypeController {
    private final TransferTypeRepository transferTypeRepository;

    public TransferTypeController(TransferTypeRepository transferTypeRepository) {
        this.transferTypeRepository = transferTypeRepository;
    }

    @GetMapping("")
    List<TransferType> getTransferTypes() { return transferTypeRepository.findAll(); }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TransferType create(@RequestBody TransferType transferType) {
         transferTypeRepository.insert(transferType);
         return transferType;
    }
}
