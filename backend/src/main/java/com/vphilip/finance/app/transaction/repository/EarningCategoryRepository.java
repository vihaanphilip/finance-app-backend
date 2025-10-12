package com.vphilip.finance.app.transaction.repository;

import com.vphilip.finance.app.transaction.model.EarningCategory;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

public interface EarningCategoryRepository extends ListCrudRepository<EarningCategory, Long> {
    @Modifying
    @Query("""
            INSERT INTO earning_category (id, earning_type_id, label, description)
            VALUES (:#{#earningCategory.id}, :#{#earningCategory.earning_type_id}, :#{#earningCategory.label}, :#{#earningCategory.description})
            """)
    void insert(@Param("earningCategory") EarningCategory earningCategory);
}