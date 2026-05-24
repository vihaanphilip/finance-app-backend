package com.vphilip.finance.app.transfer.controller;

import com.vphilip.finance.app.transfer.model.Transfer;
import com.vphilip.finance.app.transfer.repository.TransferRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/transfers")
public class TransferController {
    private final TransferRepository transferRepository;

    public TransferController(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    @GetMapping
    public Object findAll() {
        return transferRepository.findAllWithLabels();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Transfer create(@RequestBody Transfer transfer) {
        Transfer newTransfer = new Transfer(
            transfer.getId(),
            transfer.getFrom_account_id(),
            transfer.getTo_account_id(),
            transfer.getDescription(),
            transfer.getAmount(),
            transfer.getTransfer_category_id(),
            transfer.getTransaction_date(),
            LocalDateTime.now(),
            LocalDateTime.now()
        );
        return transferRepository.save(newTransfer);
    }

    @DeleteMapping("/{id}")
    public Transfer delete(@PathVariable Long id) {
        Transfer existingTransfer = transferRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        transferRepository.delete(existingTransfer);
        return existingTransfer;
    }

    @PostMapping("/{id}")
    public Transfer update(@PathVariable Long id, @RequestBody Transfer transfer) {
        Transfer existingTransfer = transferRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Transfer updatedTransfer = new Transfer(
            id,
            transfer.getFrom_account_id(),
            transfer.getTo_account_id(),
            transfer.getDescription(),
            transfer.getAmount(),
            transfer.getTransfer_category_id(),
            transfer.getTransaction_date(),
            existingTransfer.getCreated_at(),
            LocalDateTime.now()
        );
        return transferRepository.save(updatedTransfer);
    }
}
