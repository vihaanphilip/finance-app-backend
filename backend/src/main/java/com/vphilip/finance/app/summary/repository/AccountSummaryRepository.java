package com.vphilip.finance.app.summary.repository;

import com.vphilip.finance.app.account.model.Account;
import com.vphilip.finance.app.summary.dto.AccountSummaryDTO;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AccountSummaryRepository extends ListCrudRepository<Account, Long> {

    @Query("""
            WITH earnings_before AS (
                SELECT e.account_id,
                       COALESCE(SUM(e.amount), 0)::numeric(19,2) AS total
                FROM earning e
                WHERE e.transaction_date < :start_date
                GROUP BY e.account_id
            ),
            expenses_before AS (
                SELECT ex.account_id,
                       COALESCE(SUM(ex.amount), 0)::numeric(19,2) AS total
                FROM expense ex
                WHERE ex.transaction_date < :start_date
                GROUP BY ex.account_id
            ),
            earnings_in_period AS (
                SELECT e.account_id,
                       COALESCE(SUM(e.amount), 0)::numeric(19,2) AS total
                FROM earning e
                WHERE e.transaction_date BETWEEN :start_date AND :end_date
                GROUP BY e.account_id
            ),
            expenses_in_period AS (
                SELECT ex.account_id,
                       COALESCE(SUM(ex.amount), 0)::numeric(19,2) AS total
                FROM expense ex
                WHERE ex.transaction_date BETWEEN :start_date AND :end_date
                GROUP BY ex.account_id
            )
            SELECT
                a.id                                                          AS account_id,
                a.name                                                        AS account_name,
                (COALESCE(eb.total, 0) - COALESCE(exb.total, 0))             AS starting_amount,
                (COALESCE(eb.total, 0) - COALESCE(exb.total, 0)
                    + COALESCE(eip.total, 0) - COALESCE(exip.total, 0))      AS balance_amount,
                COALESCE(eip.total, 0)                                        AS earnings_amount,
                COALESCE(exip.total, 0)                                       AS expenses_amount
            FROM account a
            LEFT JOIN earnings_before     eb   ON eb.account_id  = a.id
            LEFT JOIN expenses_before     exb  ON exb.account_id = a.id
            LEFT JOIN earnings_in_period  eip  ON eip.account_id = a.id
            LEFT JOIN expenses_in_period  exip ON exip.account_id = a.id
            ORDER BY a.id DESC
            """)
    List<AccountSummaryDTO> findAccountSummaries(
            @Param("start_date") LocalDate startDate,
            @Param("end_date") LocalDate endDate
    );
}

