package com.vphilip.finance.app.account.repository;

import com.vphilip.finance.app.account.model.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
}
