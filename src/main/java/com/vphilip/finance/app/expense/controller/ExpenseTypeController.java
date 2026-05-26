package com.vphilip.finance.app.expense.controller;

import com.vphilip.finance.app.expense.model.ExpenseType;
import com.vphilip.finance.app.expense.repository.ExpenseTypeRepository;
import com.vphilip.finance.app.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expensetypes")
public class ExpenseTypeController {
    private final ExpenseTypeRepository expenseTypeRepository;

    public ExpenseTypeController(ExpenseTypeRepository expenseTypeRepository) {
        this.expenseTypeRepository = expenseTypeRepository;
    }

    @GetMapping("")
    List<ExpenseType> getExpenseTypes(@AuthenticationPrincipal User user) {
        return expenseTypeRepository.findAllByUserId(user.getId());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ExpenseType createExpenseType(@RequestBody ExpenseType expenseType, @AuthenticationPrincipal User user) {
        expenseType.setUser_id(user.getId());
        return expenseTypeRepository.save(expenseType);
    }

    @DeleteMapping("/{id}")
    ExpenseType deleteExpenseType(@PathVariable Long id, @AuthenticationPrincipal User user) {
        ExpenseType expenseType = expenseTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ExpenseType not found"));
        if (!user.getId().equals(expenseType.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        expenseTypeRepository.deleteById(id);
        return expenseType;
    }

    @PostMapping("/{id}")
    ExpenseType updateExpenseType(@RequestBody ExpenseType expenseType, @PathVariable Long id, @AuthenticationPrincipal User user) {
        if (!id.equals(expenseType.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ExpenseType IDs don't match");
        }
        ExpenseType existing = expenseTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ExpenseType not found"));
        if (!user.getId().equals(existing.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        expenseType.setUser_id(existing.getUser_id());
        return expenseTypeRepository.save(expenseType);
    }
}
