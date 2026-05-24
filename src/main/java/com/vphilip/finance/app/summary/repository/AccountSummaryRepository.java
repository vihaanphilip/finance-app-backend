package com.vphilip.finance.app.summary.repository;

import com.vphilip.finance.app.account.model.Account;
import com.vphilip.finance.app.summary.dto.AccountSummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AccountSummaryRepository extends JpaRepository<Account, Long> {

    @Query(value = """
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
            transfers_in_before AS (
                SELECT t.to_account_id AS account_id,
                       COALESCE(SUM(t.amount), 0)::numeric(19,2) AS total
                FROM transfer t
                WHERE t.transaction_date < :start_date
                GROUP BY t.to_account_id
            ),
            transfers_out_before AS (
                SELECT t.from_account_id AS account_id,
                       COALESCE(SUM(t.amount), 0)::numeric(19,2) AS total
                FROM transfer t
                WHERE t.transaction_date < :start_date
                GROUP BY t.from_account_id
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
            ),
            transfers_in_period AS (
                SELECT t.to_account_id AS account_id,
                       COALESCE(SUM(t.amount), 0)::numeric(19,2) AS total
                FROM transfer t
                WHERE t.transaction_date BETWEEN :start_date AND :end_date
                GROUP BY t.to_account_id
            ),
            transfers_out_period AS (
                SELECT t.from_account_id AS account_id,
                       COALESCE(SUM(t.amount), 0)::numeric(19,2) AS total
                FROM transfer t
                WHERE t.transaction_date BETWEEN :start_date AND :end_date
                GROUP BY t.from_account_id
            )
            SELECT
                a.id                                                                      AS account_id,
                a.name                                                                    AS account_name,
                (a.starting_amount
                    + COALESCE(eb.total, 0) - COALESCE(exb.total, 0)
                    + COALESCE(tib.total, 0) - COALESCE(tob.total, 0))                   AS starting_amount,
                (a.starting_amount
                    + COALESCE(eb.total, 0) - COALESCE(exb.total, 0)
                    + COALESCE(tib.total, 0) - COALESCE(tob.total, 0)
                    + COALESCE(eip.total, 0) - COALESCE(exip.total, 0)
                    + COALESCE(tip.total, 0) - COALESCE(top_.total, 0))                  AS balance_amount,
                COALESCE(eip.total, 0)                                                    AS earnings_amount,
                COALESCE(exip.total, 0)                                                   AS expenses_amount,
                COALESCE(tip.total, 0)                                                    AS transfers_in_amount,
                COALESCE(top_.total, 0)                                                   AS transfers_out_amount
            FROM account a
            LEFT JOIN earnings_before      eb    ON eb.account_id   = a.id
            LEFT JOIN expenses_before      exb   ON exb.account_id  = a.id
            LEFT JOIN transfers_in_before  tib   ON tib.account_id  = a.id
            LEFT JOIN transfers_out_before tob   ON tob.account_id  = a.id
            LEFT JOIN earnings_in_period   eip   ON eip.account_id  = a.id
            LEFT JOIN expenses_in_period   exip  ON exip.account_id = a.id
            LEFT JOIN transfers_in_period  tip   ON tip.account_id  = a.id
            LEFT JOIN transfers_out_period top_  ON top_.account_id = a.id
            ORDER BY a.id DESC
            """, nativeQuery = true)
    List<AccountSummaryDTO> findAccountSummaries(
            @Param("start_date") LocalDate startDate,
            @Param("end_date") LocalDate endDate
    );
}
