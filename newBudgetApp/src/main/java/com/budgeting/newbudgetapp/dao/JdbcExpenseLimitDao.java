package com.budgeting.newbudgetapp.dao;

import com.budgeting.newbudgetapp.model.ExpenseLimit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcExpenseLimitDao implements ExpenseLimitDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcExpenseLimitDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void setExpenseTypeTotal(ExpenseLimit expenseLimit) {
        String sql = "insert into expense_limits (type_name, expense_limit) VALUES (?, ?)";
        jdbcTemplate.update(sql, expenseLimit.getTypeName(), expenseLimit.getExpenseLimit());
    }

    @Override
    public List<ExpenseLimit> getExpenseLimits(int userId) {
        List<ExpenseLimit> expenseLimits = new ArrayList<>();
        String sql = "select expense_limit, expense_limits.type_name from expense_limits join user_limits ul on expense_limits.expense_limit = ul.expense_limit_id where user_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        while(rowSet.next()) {
            expenseLimits.add(mapRowToExpenseLimit(rowSet));
        }
        return expenseLimits;
    }

    @Override
    public void addUserLimit(String typeName, int userId) {
        String sql = "insert into user_limits (type_name, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, typeName, userId);
    }

    private ExpenseLimit mapRowToExpenseLimit(SqlRowSet rowSet) {
        return new ExpenseLimit(rowSet.getString("type_name"), rowSet.getDouble("expense_limit"));
    }
}
