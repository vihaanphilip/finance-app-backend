package com.vphilip.finance.app.transfer.repository;

import com.vphilip.finance.app.transfer.model.TransferType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransferTypeRepository extends JpaRepository<TransferType, Long> {

    @Query(value = "SELECT * FROM transfer_type WHERE user_id = :userId ORDER BY id", nativeQuery = true)
    List<TransferType> findAllByUserId(@Param("userId") Integer userId);
}
