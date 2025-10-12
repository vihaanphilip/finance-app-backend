package com.vphilip.finance.app.account.repository;

import com.vphilip.finance.app.account.model.Account;
import com.vphilip.finance.app.account.model.AccountDTO;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface AccountRepository extends ListCrudRepository<Account, Integer> {
    @Query("""
        SELECT a.id, a.name, a.description, a.account_type_id, at.label as account_type_label
        FROM account a
        LEFT JOIN account_type at ON a.account_type_id = at.id
        ORDER BY a.id
    """)
    List<AccountDTO> findAllWithType();
}
