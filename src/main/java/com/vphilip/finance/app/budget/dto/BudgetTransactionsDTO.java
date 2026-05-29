package com.vphilip.finance.app.budget.dto;

import com.vphilip.finance.app.earning.dto.EarningDTO;
import com.vphilip.finance.app.expense.dto.ExpenseDTO;
import com.vphilip.finance.app.transfer.dto.TransferDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetTransactionsDTO {
    private List<EarningDTO> earnings;
    private List<ExpenseDTO> expenses;
    private List<TransferDTO> transfers;
}
