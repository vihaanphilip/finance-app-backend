package com.vphilip.finance.app.transfer.controller;

import com.vphilip.finance.app.transfer.dto.TransferDTO;
import com.vphilip.finance.app.transfer.model.Transfer;
import com.vphilip.finance.app.transfer.model.TransferCategory;
import com.vphilip.finance.app.transfer.repository.TransferRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
            transfer.id(),
            transfer.from_account_id(),
            transfer.to_account_id(),
            transfer.description(),
            transfer.amount(),
            transfer.transfer_category_id(),
            transfer.transaction_date(),
            java.time.LocalDateTime.now(), // Set created_at to current time
            java.time.LocalDateTime.now()  // Set last_modified_at to current time
        );

        return transferRepository.save(newTransfer);
    }
}
