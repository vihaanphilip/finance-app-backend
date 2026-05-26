package com.vphilip.finance.app.account.repository;

import com.vphilip.finance.app.account.model.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {

    @Query(value = "SELECT * FROM account_type WHERE user_id = :userId ORDER BY id", nativeQuery = true)
    List<AccountType> findAllByUserId(@Param("userId") Integer userId);
}
