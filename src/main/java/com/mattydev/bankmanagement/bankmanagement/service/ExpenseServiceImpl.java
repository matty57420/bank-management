package com.mattydev.bankmanagement.bankmanagement.service;

import com.mattydev.bankmanagement.bankmanagement.exception.ExpenseCreationException;
import com.mattydev.bankmanagement.bankmanagement.exception.ExpenseNotFoundException;
import com.mattydev.bankmanagement.bankmanagement.models.Expense;
import com.mattydev.bankmanagement.bankmanagement.repository.ExpenseRepository;
import com.mattydev.bankmanagement.bankmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author matty - 08/04/2023
 * @project bank-management
 */
@Service
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Expense> listExpense() {
        return expenseRepository.findAll();
    }


    @Override
    public List<Expense> findExpensesByUser(Long idUser) {
        return expenseRepository.findExpensesWithUser(idUser);
    }

    @Override
    public Expense findExpenseById(Long idExpense) {
        Optional<Expense> expense = expenseRepository.findById(idExpense);
        if(expense.isPresent()){
            return expense.get();
        } else {
            throw new ExpenseNotFoundException("Expense not found with this ID : "+idExpense);
        }
    }

    @Override
    public Expense createExpense(Expense expense) {
        if(expense.getType_id() != null && expense.getAmount().compareTo(BigDecimal.ZERO) > 0
                && expense.getDate() != null &&  userRepository.findById(expense.getUser_id()).isPresent()){
            return expenseRepository.save(expense);
        } else {
            throw new ExpenseCreationException("One field at least is not correctly filled");
        }
    }

    @Override
    public Expense updateExpense(Expense expense) {
        if(expenseRepository.findById(expense.getId()).isPresent()){
            return expenseRepository.save(expense);
        } else {
            throw new ExpenseNotFoundException("Expense not found with this ID : "+expense.getId());
        }
    }

    @Override
    public boolean deleteExpense(Long idExpense) {
        if(expenseRepository.findById(idExpense).isPresent()){
            expenseRepository.deleteById(idExpense);
            return expenseRepository.findById(idExpense).isPresent();
        } else {
            throw new ExpenseNotFoundException("Expense not found with this ID : "+idExpense);
        }
    }
}
