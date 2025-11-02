package com.vphilip.finance.app.transaction.controller;

import com.vphilip.finance.app.transaction.model.Earning;
import com.vphilip.finance.app.transaction.repository.EarningRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/earnings")
public class EarningController {

    private final EarningRepository earningRepository;

    public EarningController(EarningRepository earningRepository) {
        this.earningRepository = earningRepository;
    }

    @GetMapping
    public List<Earning> findAll() {
        return earningRepository.findAll();
    }

    @GetMapping("/{id}")
    public Earning findById(@PathVariable Long id) {
        return earningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Earning not found"));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Earning create(@RequestBody Earning earning) {
        return earningRepository.save(earning);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Earning earning, @PathVariable Long id) {
        if (!earningRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Earning not found");
        }
        earningRepository.save(earning);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        earningRepository.deleteById(id);
    }
}
