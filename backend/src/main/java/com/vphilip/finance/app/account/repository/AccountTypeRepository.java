package com.vphilip.finance.app.account.repository;

import com.vphilip.finance.app.account.model.AccountType;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

public interface AccountTypeRepository extends ListCrudRepository<AccountType, Long> {
    @Modifying
    @Query("INSERT INTO account_type (id, label, description) " +
            "VALUES (:#{#accountType.id}, :#{#accountType.label}, :#{#accountType.description})")
    void insert(@Param("accountType") AccountType accountType);
}
