package com.vphilip.finance.app.transaction.repository;

import com.vphilip.finance.app.transaction.model.EarningCategory;
import com.vphilip.finance.app.transaction.dto.EarningCategoryDTO;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EarningCategoryRepository extends ListCrudRepository<EarningCategory, Long> {

    @Query("""
        SELECT ec.id, ec.earning_type_id, ec.label, ec.description, et.label as earning_type_label
        FROM earning_category ec
        LEFT JOIN earning_type et ON ec.earning_type_id = et.id
        ORDER BY ec.id
    """)
    List<EarningCategoryDTO> findAllWithType();

    @Query("""
        SELECT ec.id, ec.earning_type_id, ec.label, ec.description, et.label as earning_type_label
        FROM earning_category ec
        LEFT JOIN earning_type et ON ec.earning_type_id = et.id
        WHERE ec.id = :id
    """)
    Optional<EarningCategoryDTO> findByIdWithType(@Param("id") Long id);
}