package com.vphilip.finance.app.transaction.repository;

import com.vphilip.finance.app.transaction.model.Earning;
import org.springframework.data.repository.ListCrudRepository;

public interface EarningRepository extends ListCrudRepository<Earning, Long> {
    // Inherits basic CRUD operations from ListCrudRepository
    // The Long type parameter matches our id field type from the Earning record
}
