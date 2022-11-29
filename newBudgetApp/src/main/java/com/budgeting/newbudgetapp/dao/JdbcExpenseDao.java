package com.budgeting.newbudgetapp.dao;


import com.budgeting.newbudgetapp.model.Expense;
import com.budgeting.newbudgetapp.model.TimeBasedTotal;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.*;

@Component
public class JdbcExpenseDao implements ExpenseDao {

    private final JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate parameterJdbcTemplate;

    public JdbcExpenseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addUserExpense(int userId, int expenseId) {
        String sql = "INSERT INTO user_expense (user_id, expense_id) VALUES ((select user_id from users where users.user_id = ?), ?)";
        jdbcTemplate.update(sql, userId, expenseId);
    }

    @Override
    public List<Expense> getExpenses() {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT expense_id, expense_amount, expense_name, expenses.type_name, et.type_name, expense_date FROM expenses join expense_types et on expenses.type_name = et.type_name;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        while(rowSet.next()) {
            expenses.add(mapRowToExpense(rowSet));
        }
        return expenses;
    }


    @Override
    public void deleteAll() {
        String sql = "delete from expenses";
        jdbcTemplate.update(sql);
    }

    @Override
    public double totalLastMonth() {
        Calendar calendar = Calendar.getInstance();
        int lastMonth = calendar.get(Calendar.MONTH) - 1;
        calendar.set(Calendar.MONTH, lastMonth);
        int lastMonthDays = calendar.getActualMaximum(Calendar.DATE);
        LocalDate start = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate end;
        if(lastMonthDays == 30) {
            end = LocalDate.now().minusMonths(1).withDayOfMonth(30);
        } else {
            end = LocalDate.now().minusMonths(1).withDayOfMonth(31);
        }
        double totalForCurrentMonth = 0;
        String sql = "select sum(expense_amount) as total_expenses from expenses where expense_date between '" + start + "' AND '" + end + "' ";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while(rowSet.next()) {
            totalForCurrentMonth = rowSet.getDouble("total_expenses");
        }
        return totalForCurrentMonth;
    }

    @Override
    public List<Double> totalsFullYear() {
        List<LocalDate> startDates = getStartDates();
        String sql = "";
        List<LocalDate> endDates = getEndDates();
        List<Double> totals = new ArrayList<>();
        for(int i = 1; i < startDates.size(); i++) {
                LocalDate start = startDates.get(i);
                LocalDate end = endDates.get(i);
                sql = "select sum(expense_amount) as total_expenses from expenses where expense_date between '" + start + "' AND '" + end + "'";
                SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
                while (rowSet.next()) {
                    totals.add(rowSet.getDouble("total_expenses"));
            }
        }

        return totals;
    }

    @Override
    public List<TimeBasedTotal> totalsFullYearWithMonth(int userId) {
        List<TimeBasedTotal> totals = new ArrayList<>();
        String sql = "SELECT date_trunc('month', expense_date)::date as month,\n" +
                "                sum(expense_amount) AS total_expenses\n" +
                "                FROM (SELECT expenses.expense_amount, expense_date FROM expenses join user_expense ue on expenses.expense_id = ue.expense_id inner join expense_types et on expenses.type_name = et.type_name\n" +
                "                             where user_id = ?) as user_expenses\n" +
                "                WHERE expense_date >= '2022-01-01' AND expense_date <= '2022-12-01'\n" +
                "                GROUP BY month\n" +
                "                order by month";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        while (rowSet.next()) {
            totals.add(mapRowToTotal(rowSet));
        }
        return totals;
    }

    @Override
    public double totalCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        int numDays = calendar.getActualMaximum(Calendar.DATE);
        LocalDate start = LocalDate.now().withDayOfMonth(1);
        LocalDate end;
        if(numDays == 30) {
            end = LocalDate.now().withDayOfMonth(30);
        } else {
            end = LocalDate.now().withDayOfMonth(31);
        }
        double totalForCurrentMonth = 0;
        String sql = "select sum(expense_amount) as total_expenses from expenses where expense_date between '" + start + "' AND '" + end + "' ";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while(rowSet.next()) {
            totalForCurrentMonth = rowSet.getDouble("total_expenses");
        }
        return totalForCurrentMonth;
    }

    @Override
    public void updateExpense(Expense expense) {
        String sql = "update expenses set expense_name = ?, expense_amount = ?, type_name = ?, expense_date = ? where expense_id = ?";
        jdbcTemplate.update(sql, expense.getExpenseName(), expense.getExpenseAmount(), expense.getTypeName(), expense.getExpenseDate(), expense.getExpenseId());

    }

    @Override
    public double totalExpenses(int userId) {
        double totalExpenses = 0;
        String sql = "select sum(expense_amount) as total_expenses from expenses join user_expense on expenses.expense_id = user_expense.expense_id where user_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        if(rowSet.next()) {
            totalExpenses = rowSet.getDouble("total_expenses");
        }
        return totalExpenses;
    }

    @Override
    public void multipleExpenseInsert(List<Expense> expenses) {
        String sql = "insert into expenses (expense_name, expense_amount, type_name, expense_date)" +
                "values (?, ?, ?, ?) returning expense_id";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Expense expense = expenses.get(i);
                Integer newExpenseId;
                newExpenseId = jdbcTemplate.queryForObject(sql, Integer.class, expense.getExpenseName(), expense.getExpenseAmount(), expense.getTypeName(), expense.getExpenseDate());
                expense.setExpenseId(newExpenseId);
                ps.setString(1, expense.getExpenseName());
                ps.setDouble(2, expense.getExpenseAmount());
                ps.setString(3, expense.getTypeName());
                ps.setDate(4, java.sql.Date.valueOf(expense.getExpenseDate()));
            }

            @Override
            public int getBatchSize() {
                return expenses.size();
            }
        });
    }

    @Override
    public void addExpense(Expense expense) {
        String sql = "INSERT INTO expenses (expense_name, expense_amount, type_name, expense_date) VALUES (?, ?, ?, ?) returning expense_id;";
        Integer newExpenseId;
        newExpenseId = jdbcTemplate.queryForObject(sql, Integer.class, expense.getExpenseName(), expense.getExpenseAmount(), expense.getTypeName(), expense.getExpenseDate());
        expense.setExpenseId(newExpenseId);
    }

    @Override
    public void deleteExpense(int expenseId) {
        String sql = "delete from expenses where expense_id = ?";
        jdbcTemplate.update(sql, expenseId);
    }

    @Override
    public List<Expense> getExpensesByUserId(int userId) {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT expenses.expense_id, expenses.expense_amount, expenses.expense_name, expenses.type_name, expense_date FROM expenses join user_expense ue on expenses.expense_id = ue.expense_id inner join expense_types et on expenses.type_name = et.type_name \n"
                + "where user_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        while(rowSet.next()) {
            expenses.add(mapRowToExpense(rowSet));
        }
        return expenses;
    }

    private Expense mapRowToExpense(SqlRowSet rowSet) {
        Expense expense = new Expense();
        expense.setExpenseId(rowSet.getInt("expense_id"));
        expense.setExpenseName(rowSet.getString("expense_name"));
        expense.setExpenseAmount(rowSet.getDouble("expense_amount"));
        expense.setTypeName(rowSet.getString("type_name"));
        expense.setExpenseDate(rowSet.getDate("expense_date").toLocalDate());
        return expense;
    }

    private TimeBasedTotal mapRowToTotal(SqlRowSet rowSet) {
        TimeBasedTotal total = new TimeBasedTotal();
        total.setMonthName(rowSet.getDate("month").toLocalDate().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH));
        total.setTotalExpenses(rowSet.getDouble("total_expenses"));
        return total;
    }
    private List<LocalDate> getStartDates() {
        List<LocalDate> startDates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        LocalDate date;
        for(int i = 1; i < 13; i++) {
            date = LocalDate.of(currentYear, i, 1);
            startDates.add(date);
        }
        return startDates;
    }

    private List<LocalDate> getEndDates() {
        List<LocalDate> endDates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        LocalDate date;
        for(int i = 1; i < 13; i++) {
            YearMonth yearMonthObject = YearMonth.of(currentYear, i);
            int daysInMonth = yearMonthObject.lengthOfMonth();
            if(daysInMonth == 30) {
                date = LocalDate.of(currentYear, i, 30);
            } else if(daysInMonth == 31) {
                date = LocalDate.of(currentYear, i, 31);
            } else {
                date = LocalDate.of(currentYear, i, 28);
            }
            endDates.add(date);
        }
        return endDates;
    }


}
