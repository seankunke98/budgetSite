package com.budgeting.newbudgetapp.dao;

import com.budgeting.newbudgetapp.model.ExpenseType;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
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
        String sql = "select sum(expense_amount) as total_expenses, type_name from (SELECT expenses.expense_amount, expenses.type_name FROM expenses join user_expenses ue on expenses.expense_id = ue.expense_id inner join expense_types et on expenses.type_name = et.type_name \n"
                + "where user_id = ?) as user_expenses group by type_name";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);

        while(rowSet.next()) {
            totalExpenses.add(mapRowToExpenseTypeTotal(rowSet));
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
    public List<ExpenseType> totalsEachTypeCurrentMonth(int userId) {
        List<ExpenseType> totalForCurrentMonth = new ArrayList<>();
        LocalDate start = LocalDate.now().withDayOfMonth(1);
        LocalDate end = setEndDate();
        String sql = "select type_name, sum(expense_amount) as total_expenses from expenses join user_expenses ue on expenses.expense_id = ue.expense_id where expense_date between '" + start + "' AND '" + end + "' and user_id = ? group by type_name";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        while(rowSet.next()) {
            totalForCurrentMonth.add(mapRowToExpenseTypeTotal(rowSet));
        }
        return totalForCurrentMonth;
    }

    private ExpenseType mapRowToExpenseTypeTotal(SqlRowSet rowSet) {
        return new ExpenseType(rowSet.getString("type_name"), rowSet.getDouble("total_expenses"));
    }
    private ExpenseType mapRowToExpenseType(SqlRowSet rowSet) {
        return new ExpenseType(rowSet.getInt("type_id"), rowSet.getString("type_name"));
    }
    private LocalDate setEndDate() {
        Calendar calendar = Calendar.getInstance();
        int numDays = calendar.getActualMaximum(Calendar.DATE);
        LocalDate end;
        if(numDays == 30) {
            end = LocalDate.now().withDayOfMonth(30);
        } else {
            end = LocalDate.now().withDayOfMonth(31);
        }
        return end;
    }
}
