package com.budgeting.newbudgetapp.dao;

import com.budgeting.newbudgetapp.model.ExpenseLimit;
import com.budgeting.newbudgetapp.model.ExpenseType;

import java.sql.SQLException;
import java.util.List;

public interface ExpenseLimitDao {
    void setExpenseTypeTotal(ExpenseLimit expenseLimit, int userId) throws SQLException;

    List<ExpenseLimit> getExpenseLimits(int userId);

    void addUserLimit(int expenseLimitId);

    void deleteExpenseLimit(int expenseLimitId);

    List<ExpenseType> availableExpenseTypes(int userId);
}
