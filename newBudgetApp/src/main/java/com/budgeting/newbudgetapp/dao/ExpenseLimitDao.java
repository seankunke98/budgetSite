package com.budgeting.newbudgetapp.dao;

import com.budgeting.newbudgetapp.model.ExpenseLimit;

import java.util.List;

public interface ExpenseLimitDao {
    void setExpenseTypeTotal(ExpenseLimit expenseLimit);

    List<ExpenseLimit> getExpenseLimits(int userId);

    void addUserLimit(String typeName, int userId);
}
