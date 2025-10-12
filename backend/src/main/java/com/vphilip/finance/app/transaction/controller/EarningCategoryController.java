package com.vphilip.finance.app.transaction.controller;

import com.vphilip.finance.app.transaction.model.EarningCategory;
import com.vphilip.finance.app.transaction.model.EarningCategoryDTO;
import com.vphilip.finance.app.transaction.repository.EarningCategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/earningcategories")
public class EarningCategoryController {
    private final EarningCategoryRepository earningCategoryRepository;

    public EarningCategoryController(EarningCategoryRepository earningCategoryRepository) {
        this.earningCategoryRepository = earningCategoryRepository;
    }

    @GetMapping("")
    List<EarningCategoryDTO> getEarningCategories() {
        return earningCategoryRepository.findAllWithType();
    }

    @GetMapping("/{id}")
    EarningCategory getEarningCategoryById(@PathVariable Long id) {
        Optional<EarningCategory> earningCategory = earningCategoryRepository.findById(id);
        if (earningCategory.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return earningCategory.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    EarningCategory createEarningCategory(@RequestBody EarningCategory earningCategory) {
        return earningCategoryRepository.save(earningCategory);
    }

    @PutMapping("/{id}")
    EarningCategory updateEarningCategory(@PathVariable Long id, @RequestBody EarningCategory earningCategory) {
        if (!earningCategoryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        // Create updated category with the ID from path
        EarningCategory updatedCategory = new EarningCategory(
                id,
                earningCategory.earning_type_id(),
                earningCategory.label(),
                earningCategory.description()
        );
        return earningCategoryRepository.save(updatedCategory);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    EarningCategory deleteEarningCategory(@PathVariable Long id) {
        EarningCategory earningCategory = earningCategoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        earningCategoryRepository.deleteById(id);
        return earningCategory;
    }
}