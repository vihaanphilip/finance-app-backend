package com.vphilip.finance.app.earning.controller;

import com.vphilip.finance.app.earning.model.EarningCategory;
import com.vphilip.finance.app.earning.dto.EarningCategoryDTO;
import com.vphilip.finance.app.earning.repository.EarningCategoryRepository;
import com.vphilip.finance.app.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/earningcategories")
public class EarningCategoryController {
    private final EarningCategoryRepository earningCategoryRepository;

    public EarningCategoryController(EarningCategoryRepository earningCategoryRepository) {
        this.earningCategoryRepository = earningCategoryRepository;
    }

    @GetMapping("")
    List<EarningCategoryDTO> getEarningCategories(@AuthenticationPrincipal User user) {
        return earningCategoryRepository.findAllWithType(user.getId());
    }

    @GetMapping("/{id}")
    EarningCategory getEarningCategoryById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        EarningCategory earningCategory = earningCategoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!user.getId().equals(earningCategory.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return earningCategory;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    EarningCategory createEarningCategory(@RequestBody EarningCategory earningCategory, @AuthenticationPrincipal User user) {
        earningCategory.setUser_id(user.getId());
        return earningCategoryRepository.save(earningCategory);
    }

    @PutMapping("/{id}")
    EarningCategory updateEarningCategory(@PathVariable Long id, @RequestBody EarningCategory earningCategory, @AuthenticationPrincipal User user) {
        EarningCategory existing = earningCategoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!user.getId().equals(existing.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        EarningCategory updatedCategory = new EarningCategory(
                id,
                earningCategory.getEarningTypeId(),
                earningCategory.getLabel(),
                earningCategory.getDescription(),
                existing.getUser_id()
        );
        return earningCategoryRepository.save(updatedCategory);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    EarningCategory deleteEarningCategory(@PathVariable Long id, @AuthenticationPrincipal User user) {
        EarningCategory earningCategory = earningCategoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!user.getId().equals(earningCategory.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        earningCategoryRepository.deleteById(id);
        return earningCategory;
    }
}
