package com.vphilip.finance.app.account.repository;

import com.vphilip.finance.app.account.model.AccountType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository
public class JdbcClientAccountTypeRepository_OBSOLETE {

    private final JdbcClient jdbcClient;

    public JdbcClientAccountTypeRepository_OBSOLETE(JdbcClient jdbcTemplate) {
        this.jdbcClient = jdbcTemplate;
    }

    public List<AccountType> findAllAccountTypes() {
        String sql = "SELECT id, label, description FROM account_type";
        return jdbcClient.sql(sql)
                .query(AccountType.class)
                .list();
    }


    public void create(AccountType accountType) {
        var updated = jdbcClient.sql("INSERT INTO account_type(id,label,description) values(?,?,?)")
                .params(List.of(accountType.id(),accountType.label(), accountType.description()))
                .update();

        Assert.state(updated == 1, "Failed to create accountTYpe " + accountType.label());
    }


    public int count() {
        return jdbcClient.sql("SELECT * FROM account_type").query().listOfRows().size();
    }

    public void saveAll(List<AccountType> accountTypes) {
        accountTypes.stream().forEach(this::create);
    }
}
