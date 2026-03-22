package com.vphilip.finance.app.transfer.repository;

import com.vphilip.finance.app.transfer.dto.TransferCategoryDTO;
import com.vphilip.finance.app.transfer.model.TransferCategory;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface TransferCategoryRepository extends ListCrudRepository<TransferCategory, Long> {
    @Query("""
            SELECT tc.id, tc.transfer_type_id, tc.label, tc.description, tt.label as transfer_type_label
            FROM transfer_category tc
            LEFT JOIN transfer_type tt ON tt.id = tc.transfer_type_id
            ORDER BY tc.id
            """)
    List<TransferCategoryDTO> findAllWithType();
}
