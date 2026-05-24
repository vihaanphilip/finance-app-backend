package com.vphilip.finance.app.earning.repository;

import com.vphilip.finance.app.earning.model.EarningCategory;
import com.vphilip.finance.app.earning.dto.EarningCategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EarningCategoryRepository extends JpaRepository<EarningCategory, Long> {

    @Query(value = """
        SELECT ec.id, ec.earning_type_id, ec.label, ec.description, et.label as earning_type_label
        FROM earning_category ec
        LEFT JOIN earning_type et ON ec.earning_type_id = et.id
        ORDER BY ec.id
    """, nativeQuery = true)
    List<EarningCategoryDTO> findAllWithType();

    @Query(value = """
        SELECT ec.id, ec.earning_type_id, ec.label, ec.description, et.label as earning_type_label
        FROM earning_category ec
        LEFT JOIN earning_type et ON ec.earning_type_id = et.id
        WHERE ec.id = :id
    """, nativeQuery = true)
    Optional<EarningCategoryDTO> findByIdWithType(@Param("id") Long id);
}
