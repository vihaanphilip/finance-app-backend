package com.vphilip.finance.app.earning.repository;

import com.vphilip.finance.app.earning.model.EarningType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EarningTypeRepository extends JpaRepository<EarningType, Long> {

    @Query(value = "SELECT * FROM earning_type WHERE user_id = :userId ORDER BY id", nativeQuery = true)
    List<EarningType> findAllByUserId(@Param("userId") Integer userId);
}
