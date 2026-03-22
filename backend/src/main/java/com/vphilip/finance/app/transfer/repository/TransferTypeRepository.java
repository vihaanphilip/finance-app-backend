package com.vphilip.finance.app.transfer.repository;

import com.vphilip.finance.app.transfer.model.TransferType;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

public interface TransferTypeRepository extends ListCrudRepository<TransferType, Long> {
    @Modifying
    @Query("""
            INSERT INTO transfer_type (id, label, description)
            VALUES (:#{#transferType.id}, :#{#transferType.label}, :#{#transferType.description})
            """)
    void insert(@Param("transferType") TransferType transferType);
}
