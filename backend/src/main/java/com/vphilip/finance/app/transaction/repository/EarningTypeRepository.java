package com.vphilip.finance.app.transaction.repository;

import com.vphilip.finance.app.transaction.model.EarningType;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

public interface EarningTypeRepository extends ListCrudRepository<EarningType, Long> {
    @Modifying
    @Query("""
            INSERT INTO earning_type (id, label, description)
            VALUES (:#{#earningType.id}, :#{#earningType.label}, :#{#earningType.description})
            """)
    void insert(@Param("earningType") EarningType earningType);
}
