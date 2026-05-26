package com.vphilip.finance.app.earning.repository;

import com.vphilip.finance.app.earning.model.Earning;
import com.vphilip.finance.app.earning.model.EarningCategorySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface EarningSummaryRepository extends JpaRepository<Earning, Long> {

    @Query(value = """
        SELECT COALESCE(SUM(e.amount), 0)
        FROM earning e
        JOIN account a ON e.account_id = a.id
        WHERE EXTRACT(YEAR FROM e.created_at) = :year
          AND EXTRACT(MONTH FROM e.created_at) = :month
          AND a.user_id = :userId
        """, nativeQuery = true)
    BigDecimal totalEarningsForMonth(@Param("year") int year, @Param("month") int month, @Param("userId") Integer userId);

    @Query(value = """
        SELECT
            MAKE_DATE(:year, :month, 1) AS month,
            ec.id   AS earning_category_id,
            ec.label AS earning_category_label,
            COALESCE(SUM(e.amount), 0) AS total_earnings
        FROM earning_category ec
        LEFT JOIN earning e
          ON e.earning_category_id = ec.id
         AND EXTRACT(YEAR FROM e.created_at) = :year
         AND EXTRACT(MONTH FROM e.created_at) = :month
        WHERE ec.user_id = :userId
        GROUP BY ec.id, ec.label
        ORDER BY ec.label
        """, nativeQuery = true)
    List<EarningCategorySummary> totalEarningsForMonthByCategory(@Param("year") int year, @Param("month") int month, @Param("userId") Integer userId);
}
