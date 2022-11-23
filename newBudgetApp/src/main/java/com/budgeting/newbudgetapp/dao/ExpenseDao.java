package com.budgeting.newbudgetapp.dao;

import com.budgeting.newbudgetapp.model.Expense;
import com.budgeting.newbudgetapp.model.TimeBasedTotal;

import java.util.List;

public interface ExpenseDao {


    void addExpense(Expense expense);
    void deleteExpense(int expenseId);

    List<Expense> getExpensesByUserId(int userId);

    void addUserExpense(int userId, int expenseId);

    void deleteAll();

    double totalCurrentMonth();

    double totalLastMonth();

    List<Double> totalsFullYear();

    List<TimeBasedTotal> totalsFullYearWithMonth(int userId);

    List<Expense> getExpenses();

    void updateExpense(Expense expense);

    double totalExpenses(int userId);

    void multipleExpenseInsert(List<Expense> expenses);

    void deleteMultipleExpenses(List<Integer> expenses);



}
