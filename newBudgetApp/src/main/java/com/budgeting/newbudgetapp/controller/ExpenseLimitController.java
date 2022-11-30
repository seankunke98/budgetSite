package com.budgeting.newbudgetapp.controller;

import com.budgeting.newbudgetapp.dao.*;
import com.budgeting.newbudgetapp.model.ExpenseLimit;
import com.budgeting.newbudgetapp.model.ExpenseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @RequestMapping(path = "/expenses/setExpenseLimit", method = RequestMethod.POST)
    public void setExpenseLimit(@RequestBody ExpenseLimit expenseLimit, Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        expenseLimitDao.setExpenseTypeTotal(expenseLimit);
        expenseLimitDao.addUserLimit(expenseLimit.getTypeName(), userId);
    }

    @RequestMapping(path = "/expenses/getExpenseLimits", method = RequestMethod.GET)
    public List<ExpenseLimit> getExpenseLimit(Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        List<ExpenseType> totalsForTypes = expenseTypeDao.totalsEachTypeCurrentMonth(userId);
        List<ExpenseType> expenseTypes = expenseTypeDao.getExpenseTypes();
        List<ExpenseLimit> limits = expenseLimitDao.getExpenseLimits(userId);
        for(ExpenseType currentTotal : totalsForTypes) {
            for (ExpenseLimit currentLimit : limits) {
                if (currentTotal.getTypeName().equals(currentLimit.getTypeName())) {
                    currentLimit.setTotalExpenses(currentTotal.getTotalExpenses());
                }
            }
        }
        return limits;
    }
}
