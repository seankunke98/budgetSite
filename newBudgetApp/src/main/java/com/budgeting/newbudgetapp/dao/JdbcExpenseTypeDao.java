package com.budgeting.newbudgetapp.dao;

import com.budgeting.newbudgetapp.model.ExpenseType;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcExpenseTypeDao implements ExpenseTypeDao {


    private final JdbcTemplate jdbcTemplate;

    public JdbcExpenseTypeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<ExpenseType> totalExpensesEachType(int userId) throws JsonProcessingException {
        List<ExpenseType> totalExpenses = new ArrayList<>();
        ExpenseType expenseType = new ExpenseType();
        String sql = "select sum(expense_amount) as total_expenses, expense_type_name from (SELECT expenses.expense_id, expenses.expense_amount, expenses.expense_name, expense_type_name, expense_date FROM expenses join user_expense ue on expenses.expense_id = ue.expense_id inner join expense_types et on expenses.expense_type_name = et.type_name \n"
                + "where user_id = ?) as user_expenses group by expense_type_name";
//        String sql = "select distinct total_expenses, total_expenses_by_type.type_name, type_id from total_expenses_by_type join expense_types et on total_expenses_by_type.type_name = et.type_name \n" +
//                "join expenses e on et.type_name = e.expense_type_name join user_expense ue on e.expense_id = ue.expense_id where user_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);

        while(rowSet.next()) {
            expenseType = mapRowToExpenseTypeTotal(rowSet);
            totalExpenses.add(expenseType);
        }
        return totalExpenses;
    }

    @Override
    public List<ExpenseType> getExpenseTypes() {
        List<ExpenseType> expenseTypes = new ArrayList<>();
        String sql = "select type_id, type_name from expense_types";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        while(rowSet.next()) {
            expenseTypes.add(mapRowToExpenseType(rowSet));
        }
        return expenseTypes;
    }

    @Override
    public List<Double> typeTotals() {
        List<Double> totals = new ArrayList<>();
        String sql = "select distinct sum(expense_amount) as total_expenses, type_name from expenses join expense_types et on expenses.expense_type_name = et.type_name group by type_name";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        while(rowSet.next()) {
            totals.add(rowSet.getDouble("total_expenses"));
        }
        System.out.println(totals);
        return totals;
    }

//    private ExpenseType mapRowToExpenseTypeTotal(SqlRowSet rowSet) {
//        return new ExpenseType(rowSet.getInt("type_id"), rowSet.getString("expense_type_name"), rowSet.getDouble("total_expenses"));
//    }
    private ExpenseType mapRowToExpenseType(SqlRowSet rowSet) {
        return new ExpenseType(rowSet.getInt("type_id"), rowSet.getString("type_name"));
    }
}
