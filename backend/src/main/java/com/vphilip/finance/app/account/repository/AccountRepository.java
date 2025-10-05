package com.vphilip.finance.app.account.repository;

import com.vphilip.finance.app.account.model.Account;
import org.springframework.data.repository.ListCrudRepository;

public interface AccountRepository extends ListCrudRepository<Account, Integer> {
}
