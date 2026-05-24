package com.vphilip.finance.app.account.repository;

import com.vphilip.finance.app.account.model.Account;
import com.vphilip.finance.app.account.model.AccountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = """
        SELECT a.id, a.name, a.description, a.account_type_id, at.label as account_type_label, a.starting_amount
        FROM account a
        LEFT JOIN account_type at ON a.account_type_id = at.id
        ORDER BY a.id
    """, nativeQuery = true)
    List<AccountDTO> findAllWithType();
}
