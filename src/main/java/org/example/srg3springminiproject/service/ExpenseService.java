package org.example.srg3springminiproject.service;


import org.example.srg3springminiproject.model.Expense;

import org.example.srg3springminiproject.model.request.ExpenseRequest;


import java.util.List;
import java.util.UUID;

public interface ExpenseService {

    List<Expense> getAllExpense(int offset, int limit,  String sortBy,String orderByStr);

    Expense findExpenseById(UUID id);

    Expense saveExpense(ExpenseRequest expenseRequest);

    Expense updateExpense(UUID id, ExpenseRequest expenseRequest);

    Boolean deleteExpense(UUID id);
}
