package com.vphilip.finance.app.earning.repository;

import com.vphilip.finance.app.earning.model.EarningCategorySummary;
import com.vphilip.finance.app.earning.model.EarningSummary;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface EarningSummaryRepository extends ListCrudRepository<EarningSummary, Long> {
    @Query("""
        SELECT COALESCE(SUM(e.amount), 0)
        FROM earning e
        WHERE EXTRACT(YEAR FROM e.created_at) = :year
          AND EXTRACT(MONTH FROM e.created_at) = :month
        """)
    BigDecimal totalEarningsForMonth(@Param("year") int year, @Param("month") int month);

    @Query("""
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
        GROUP BY ec.id, ec.label
        ORDER BY ec.label
        """)
    List<EarningCategorySummary> totalEarningsForMonthByCategory(@Param("year") int year, @Param("month") int month);
}
