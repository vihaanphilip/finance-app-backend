package com.vphilip.finance.app.transfer.controller;

import com.vphilip.finance.app.transfer.model.TransferType;
import com.vphilip.finance.app.transfer.repository.TransferTypeRepository;
import com.vphilip.finance.app.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transfertypes")
public class TransferTypeController {
    private final TransferTypeRepository transferTypeRepository;

    public TransferTypeController(TransferTypeRepository transferTypeRepository) {
        this.transferTypeRepository = transferTypeRepository;
    }

    @GetMapping("")
    List<TransferType> getTransferTypes(@AuthenticationPrincipal User user) {
        return transferTypeRepository.findAllByUserId(user.getId());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TransferType create(@RequestBody TransferType transferType, @AuthenticationPrincipal User user) {
        transferType.setUser_id(user.getId());
        return transferTypeRepository.save(transferType);
    }

    @DeleteMapping("/{id}")
    TransferType deleteTransferType(@PathVariable Long id, @AuthenticationPrincipal User user) {
        TransferType transferType = transferTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer Type Not Found"));
        if (!user.getId().equals(transferType.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        transferTypeRepository.deleteById(id);
        return transferType;
    }

    @PostMapping("/{id}")
    TransferType updateTransferType(@RequestBody TransferType transferType, @PathVariable Long id, @AuthenticationPrincipal User user) {
        if (!id.equals(transferType.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transfer Type IDs don't match");
        }
        TransferType existing = transferTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer Type Not Found"));
        if (!user.getId().equals(existing.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        transferType.setUser_id(existing.getUser_id());
        return transferTypeRepository.save(transferType);
    }
}
