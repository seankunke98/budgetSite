package com.budgeting.newbudgetapp.controller;

import com.budgeting.newbudgetapp.dao.*;
import com.budgeting.newbudgetapp.model.ExpenseLimit;
import com.budgeting.newbudgetapp.model.ExpenseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ExpenseLimitController {

    private ExpenseDao expenseDao;
    private UserDao userDao;
    private ExpenseLimitDao expenseLimitDao;
    private ExpenseTypeDao expenseTypeDao;
    @Autowired
    private JdbcExpenseLimitDao jdbcExpenseLimitDao;

    public ExpenseLimitController(ExpenseDao expenseDao, UserDao userDao, ExpenseLimitDao expenseLimitDao, ExpenseTypeDao expenseTypeDao) {
        this.expenseDao = expenseDao;
        this.userDao = userDao;
        this.expenseLimitDao = expenseLimitDao;
        this.expenseTypeDao = expenseTypeDao;
    }

    @RequestMapping(path = "/expenseLimits/setExpenseLimit", method = RequestMethod.POST)
    public void setExpenseLimit(@RequestBody ExpenseLimit expenseLimit, Principal principal) throws SQLException {
        int userId = userDao.findIdByUsername(principal.getName());
        expenseLimitDao.setExpenseTypeTotal(expenseLimit, userId);
        expenseLimitDao.addUserLimit(expenseLimit.getExpenseLimitId());
    }

    @RequestMapping(path = "/expenseLimits/getExpenseLimits", method = RequestMethod.GET)
    public List<ExpenseLimit> getExpenseLimit(Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        List<ExpenseType> totalsForTypes = expenseTypeDao.totalsEachTypeCurrentMonth(userId);
        List<ExpenseLimit> limits = expenseLimitDao.getExpenseLimits(userId);
        for(ExpenseType currentTotal : totalsForTypes) {
            for(ExpenseLimit currentLimit : limits) {
                if(currentTotal.getTypeName().equals(currentLimit.getTypeName())) {
                    currentLimit.setTotalExpenses(currentTotal.getTotalExpenses());
                }
            }
        }
        return limits;
    }

    @RequestMapping(path = "/expenseLimits/removeExpenseLimit/{expenseLimitId}", method = RequestMethod.DELETE)
    public void removeExpenseLimit(@PathVariable int expenseLimitId) {
        expenseLimitDao.deleteExpenseLimit(expenseLimitId);
    }

    @RequestMapping(path = "/expenseLimits/availableTypes", method = RequestMethod.GET)
    public List<ExpenseType> getAvailableExpenseTypes(Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        return expenseLimitDao.availableExpenseTypes(userId);
    }

}
