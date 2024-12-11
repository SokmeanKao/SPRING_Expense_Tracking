package org.example.srg3springminiproject.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.example.srg3springminiproject.exception.NotFoundException;
import org.example.srg3springminiproject.model.Expense;

import org.example.srg3springminiproject.model.request.ExpenseRequest;
import org.example.srg3springminiproject.repository.ExpenseRepository;
import org.example.srg3springminiproject.service.ExpenseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserServiceImpl userService;

    @Override
    public Expense findExpenseById(UUID id) {
        UUID userId = userService.getUsernameOfCurrentUser();

        Expense expense = expenseRepository.findExpenseById(id,userId);
        //return expenseRepository.findExpenseById(id,userId);

        if (expense == null) {
            throw new NotFoundException("The expense with id " + id + " doesn't exist.");
        }
        else {
            return expenseRepository.findExpenseById(id,userId);
        }
    }

    @Override
    public List<Expense> getAllExpense(int offset, int limit, String sortBy,String orderByStr) {
        UUID userId = userService.getUsernameOfCurrentUser();
        offset = (offset - 1) * limit;
        return expenseRepository.getAllExpense(offset,limit,sortBy,orderByStr, userId);
    }

    @Override
    public Expense saveExpense(ExpenseRequest expenseRequest) {
        UUID userId = userService.getUsernameOfCurrentUser();
        Expense expenseId = expenseRepository.saveExpense(expenseRequest,userId);
        return expenseId;
    }

    @Override
    public Expense updateExpense(UUID id, ExpenseRequest expenseRequest) {
        UUID userId = userService.getUsernameOfCurrentUser();
        Expense expenseId = expenseRepository.updateExpense(id,expenseRequest,userId);

        if (expenseId == null) {
            throw new NotFoundException("The expense with id " + id + " doesn't exist.");
        }
        else {
            return expenseId;
        }
    }

    @Override
    public Boolean deleteExpense(UUID id) {
        boolean removed = expenseRepository.deleteExpense(id);
        if (!removed) {
            throw new NotFoundException("The expense with id " + id + " doesn't exist.");
        }
        return removed;
    }
}
