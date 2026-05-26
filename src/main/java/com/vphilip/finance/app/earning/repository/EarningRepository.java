package com.vphilip.finance.app.earning.repository;

import com.vphilip.finance.app.earning.dto.EarningDTO;
import com.vphilip.finance.app.earning.model.Earning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EarningRepository extends JpaRepository<Earning, Long> {

    @Query(value = """
            SELECT e.id,
                   e.account_id,
                   a.name as account_label,
                   e.description,
                   e.amount,
                   ec.earning_type_id,
                   et.label as earning_type_label,
                   e.earning_category_id,
                   ec.label as earning_category_label,
                   e.transaction_date,
                   e.created_at,
                   e.last_modified_at
            FROM earning e
            LEFT JOIN account a ON e.account_id = a.id
            LEFT JOIN earning_category ec ON e.earning_category_id = ec.id
            LEFT JOIN earning_type et ON et.id = ec.earning_type_id
            WHERE a.user_id = :userId
            ORDER BY e.id DESC
            """, nativeQuery = true)
    List<EarningDTO> findAllWithLabels(@Param("userId") Integer userId);
}
