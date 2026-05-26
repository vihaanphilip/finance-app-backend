package com.vphilip.finance.app.earning.controller;

import com.vphilip.finance.app.earning.model.EarningType;
import com.vphilip.finance.app.earning.repository.EarningTypeRepository;
import com.vphilip.finance.app.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/earningtypes")
public class EarningTypeController {
    private final EarningTypeRepository earningTypeRepository;

    public EarningTypeController(EarningTypeRepository earningTypeRepository) {
        this.earningTypeRepository = earningTypeRepository;
    }

    @GetMapping("")
    List<EarningType> getEarningTypes(@AuthenticationPrincipal User user) {
        return earningTypeRepository.findAllByUserId(user.getId());
    }

    @GetMapping("/{id}")
    EarningType getEarningTypeById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        EarningType earningType = earningTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!user.getId().equals(earningType.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return earningType;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    EarningType createEarningType(@RequestBody EarningType earningType, @AuthenticationPrincipal User user) {
        earningType.setUser_id(user.getId());
        return earningTypeRepository.save(earningType);
    }

    @PostMapping("/{id}")
    EarningType updateEarningType(@RequestBody EarningType earningType, @PathVariable Long id, @AuthenticationPrincipal User user) {
        if (!id.equals(earningType.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in path must match ID in request body");
        }
        EarningType existing = earningTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "EarningType not found"));
        if (!user.getId().equals(existing.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        earningType.setUser_id(existing.getUser_id());
        return earningTypeRepository.save(earningType);
    }

    @DeleteMapping("/{id}")
    EarningType deleteEarningType(@PathVariable Long id, @AuthenticationPrincipal User user) {
        EarningType earningType = earningTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!user.getId().equals(earningType.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        earningTypeRepository.deleteById(id);
        return earningType;
    }
}
