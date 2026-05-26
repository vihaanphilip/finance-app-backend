package com.vphilip.finance.app.transfer.repository;

import com.vphilip.finance.app.transfer.dto.TransferCategoryDTO;
import com.vphilip.finance.app.transfer.model.TransferCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransferCategoryRepository extends JpaRepository<TransferCategory, Long> {

    @Query(value = """
            SELECT tc.id, tc.transfer_type_id, tc.label, tc.description, tt.label as transfer_type_label
            FROM transfer_category tc
            LEFT JOIN transfer_type tt ON tt.id = tc.transfer_type_id
            WHERE tc.user_id = :userId
            ORDER BY tc.id
            """, nativeQuery = true)
    List<TransferCategoryDTO> findAllWithType(@Param("userId") Integer userId);
}
