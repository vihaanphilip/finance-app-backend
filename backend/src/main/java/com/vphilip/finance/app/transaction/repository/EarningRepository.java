package com.vphilip.finance.app.transaction.repository;

import com.vphilip.finance.app.transaction.dto.EarningDTO;
import com.vphilip.finance.app.transaction.model.Earning;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface EarningRepository extends ListCrudRepository<Earning, Long> {
    // Inherits basic CRUD operations from ListCrudRepository
    // The Long type parameter matches our id field type from the Earning record

    @Query("""
            SELECT e.id, 
                   e.account_id, 
                   a.name as account_label,
                   e.description, 
                   e.amount,
                   e.earning_type_id, 
                   et.label as earning_type_label,
                   e.earning_category_id, 
                   ec.label as earning_category_label,
                   e.created_at, 
                   e.last_modified_at
            FROM earning e
            LEFT JOIN account a ON e.account_id = a.id
            LEFT JOIN earning_type et ON e.earning_type_id = et.id
            LEFT JOIN earning_category ec ON e.earning_category_id = ec.id
            """)
    List<EarningDTO> findAllWithLabels();
}
