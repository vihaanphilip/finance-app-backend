package com.vphilip.finance.app.earning.controller;

import com.vphilip.finance.app.earning.model.EarningType;
import com.vphilip.finance.app.earning.repository.EarningTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/earningtypes")
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
    EarningType createEarningType(@RequestBody EarningType earningType) {
        return earningTypeRepository.save(earningType);
    }

    @PostMapping("/{id}")
    EarningType updateEarningType(@RequestBody EarningType earningType, @PathVariable Long id) {
        if (!id.equals(earningType.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in path must match ID in request body");
        }
        if (!earningTypeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "EarningType not found");
        }
        return earningTypeRepository.save(earningType);
    }

    @DeleteMapping("/{id}")
    EarningType deleteEarningType(@PathVariable Long id) {
        Optional<EarningType> earningType = earningTypeRepository.findById(id);
        if (earningType.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        earningTypeRepository.deleteById(id);
        return earningType.get();
    }
}
