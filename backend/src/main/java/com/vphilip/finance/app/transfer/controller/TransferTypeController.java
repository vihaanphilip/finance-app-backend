package com.vphilip.finance.app.transfer.controller;

import com.vphilip.finance.app.transfer.model.TransferType;
import com.vphilip.finance.app.transfer.repository.TransferTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    @DeleteMapping("/{id}")
    TransferType deleteTransferType(@PathVariable Long id) {
        Optional<TransferType> transferType = transferTypeRepository.findById(id);
        if (transferType.isPresent()) {
            transferTypeRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer Type Not Found");
        }
        return transferType.get();
    }

}
