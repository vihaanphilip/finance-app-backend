package com.vphilip.finance.app.transfer.repository;

import com.vphilip.finance.app.transfer.model.TransferType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferTypeRepository extends JpaRepository<TransferType, Long> {
}
