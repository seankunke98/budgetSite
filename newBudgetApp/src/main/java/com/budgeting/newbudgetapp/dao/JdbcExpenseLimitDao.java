package com.budgeting.newbudgetapp.dao;

import com.budgeting.newbudgetapp.model.ExpenseLimit;
import com.budgeting.newbudgetapp.model.ExpenseType;
import org.postgresql.util.PGobject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcExpenseLimitDao implements ExpenseLimitDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcExpenseLimitDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void setExpenseTypeTotal(ExpenseLimit expenseLimit, int userId) throws SQLException {
        String limit = "{ \"type_name\": " + "\"" + expenseLimit.getTypeName() + "\"" + ", \"expense_limit\": " + expenseLimit.getExpenseLimit() + "}";
        PGobject jsonObject = new PGobject();
        jsonObject.setType("json");
        jsonObject.setValue(limit);
        String sql = "insert into expense_limits (expense_limits, user_id) VALUES (?, ?) returning expense_limit_id";
        Integer newExpenseLimitId;
        newExpenseLimitId = jdbcTemplate.queryForObject(sql, Integer.class, jsonObject, userId);
        expenseLimit.setExpenseLimitId(newExpenseLimitId);
//        jdbcTemplate.update(sql, jsonObject);
    }

    @Override
    public List<ExpenseLimit> getExpenseLimits(int userId) {
        List<ExpenseLimit> expenseLimits = new ArrayList<>();
        String sql = "select expense_limits.expense_limit_id, expense_limits ->> 'type_name' as type_name, expense_limits ->> 'expense_limit' as expense_limit from expense_limits join user_limits ul on expense_limits.expense_limit_id = ul.expense_limit_id where user_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        while(rowSet.next()) {
            expenseLimits.add(mapRowToExpenseLimit(rowSet));
        }
        return expenseLimits;
    }

    @Override
    public void addUserLimit(int expenseLimitId) {
        String sql = "insert into user_limits (expense_limit_id) VALUES (?)";
        jdbcTemplate.update(sql, expenseLimitId);
    }

    @Override
    public void deleteExpenseLimit(int expenseLimitId) {
        String sql = "delete from expense_limits where expense_limit_id = ?";
        jdbcTemplate.update(sql, expenseLimitId);
    }

    @Override
    public List<ExpenseType> availableExpenseTypes(int userId) {
        List<ExpenseType> types = new ArrayList<>();
        String sql = "select type_id, type_name from expense_types where type_name not in (select expense_limits ->> 'type_name' from expense_limits where user_id = ? )";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        while(rowSet.next()) {
            types.add(mapRowToExpenseType(rowSet));
        }
        return types;
    }

    private ExpenseLimit mapRowToExpenseLimit(SqlRowSet rowSet) {
        return new ExpenseLimit(rowSet.getInt("expense_limit_id"), rowSet.getString("type_name"), rowSet.getDouble("expense_limit"));
    }

    private ExpenseType mapRowToExpenseType(SqlRowSet rowSet) {
        return new ExpenseType(rowSet.getInt("type_id"), rowSet.getString("type_name"));
    }
}
