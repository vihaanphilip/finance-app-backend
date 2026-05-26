package com.vphilip.finance.app.transfer.repository;

import com.vphilip.finance.app.transfer.dto.TransferDTO;
import com.vphilip.finance.app.transfer.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

    @Query(value = """
        SELECT t.id,
                t.from_account_id,
                af.name as from_account_label,
                t.to_account_id,
                at2.name as to_account_label,
                t.description,
                t.amount,
                tc.transfer_type_id,
                tt.label as transfer_type_label,
                t.transfer_category_id,
                tc.label as transfer_category_label,
                t.transaction_date,
                t.created_at,
                t.last_modified_at
        FROM transfer t
        LEFT JOIN account af ON af.id = t.from_account_id
        LEFT JOIN account at2 ON at2.id = t.to_account_id
        LEFT JOIN transfer_category tc ON tc.id = t.transfer_category_id
        LEFT JOIN transfer_type tt ON tt.id = tc.transfer_type_id
        WHERE af.user_id = :userId
        ORDER BY t.id DESC
    """, nativeQuery = true)
    List<TransferDTO> findAllWithLabels(@Param("userId") Integer userId);
}
