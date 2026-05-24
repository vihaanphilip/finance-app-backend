package com.vphilip.finance.app.earning.repository;

import com.vphilip.finance.app.earning.model.EarningType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EarningTypeRepository extends JpaRepository<EarningType, Long> {
}
