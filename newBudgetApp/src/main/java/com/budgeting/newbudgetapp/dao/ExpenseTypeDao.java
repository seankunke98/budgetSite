package com.budgeting.newbudgetapp.dao;

import com.budgeting.newbudgetapp.model.ExpenseType;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ExpenseTypeDao {

    List<ExpenseType> totalExpensesEachType(int userId) throws JsonProcessingException;

    List<ExpenseType> getExpenseTypes();

    List<ExpenseType> totalsEachTypeCurrentMonth(int userId);

}
