package com.vphilip.finance.app.transaction.controller;

import com.vphilip.finance.app.transaction.model.EarningType;
import com.vphilip.finance.app.transaction.repository.EarningTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/earningtypes")
public class EarningTypeController {
    private final EarningTypeRepository earningTypeRepository;

    public EarningTypeController(EarningTypeRepository earningTypeRepository) {
        this.earningTypeRepository = earningTypeRepository;
    }

    @GetMapping("")
    List<EarningType> getEarningTypes() {
        return earningTypeRepository.findAll();
    }

    @GetMapping("/{id}")
    EarningType getEarningTypeById(@PathVariable Long id) {
        Optional<EarningType> earningType = earningTypeRepository.findById(id);
        if (earningType.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return earningType.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createEarningType(@RequestBody EarningType earningType) {
        earningTypeRepository.insert(earningType);
    }

}


