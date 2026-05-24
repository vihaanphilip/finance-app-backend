package com.vphilip.finance.app.earning.repository;

import com.vphilip.finance.app.earning.model.EarningCategory;
import com.vphilip.finance.app.earning.dto.EarningCategoryDTO;
import org.springframework.data.jdbc.repository.query.Modifying;
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

    @Modifying
    @Query("""
        INSERT INTO earning_category (id, earning_type_id, label, description)
        VALUES (:#{#earningCategory.id}, :#{#earningCategory.earning_type_id}, :#{#earningCategory.label}, :#{#earningCategory.description})
        """)
    void insert(@Param("earningCategory") EarningCategory earningCategory);
}