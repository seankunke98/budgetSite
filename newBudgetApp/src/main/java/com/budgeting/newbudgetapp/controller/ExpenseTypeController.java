package com.budgeting.newbudgetapp.controller;

import com.budgeting.newbudgetapp.dao.ExpenseDao;
import com.budgeting.newbudgetapp.dao.ExpenseTypeDao;
import com.budgeting.newbudgetapp.dao.JdbcExpenseTypeDao;
import com.budgeting.newbudgetapp.dao.UserDao;
import com.budgeting.newbudgetapp.model.ExpenseType;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RestController
public class ExpenseTypeController {

    private final ExpenseDao expenseDao;
    private UserDao userDao;
    private ExpenseTypeDao expenseTypeDao;
    @Autowired
    private JdbcExpenseTypeDao jdbcExpenseTypeDao;

    public ExpenseTypeController(ExpenseDao expenseDao, UserDao userDao, ExpenseTypeDao expenseTypeDao) {
        this.expenseDao = expenseDao;
        this.userDao = userDao;
        this.expenseTypeDao = expenseTypeDao;
    }


    @RequestMapping(path = "/expenses/expenseTypes/allTypeTotals", method = RequestMethod.GET)
    public List<ExpenseType> totalExpensesEachType(Principal principal) throws JsonProcessingException {
        int actualUserId = userDao.findIdByUsername(principal.getName());
        List<ExpenseType> expenseTypeTotals = new ArrayList<>();
        if(actualUserId == userDao.findIdByUsername(principal.getName())) {
            expenseTypeTotals = jdbcExpenseTypeDao.totalExpensesEachType(actualUserId);
        }
        System.out.println(expenseTypeTotals.toString());
        return expenseTypeTotals;
    }

    @RequestMapping(path = "/expenses/expenseTypes", method = RequestMethod.GET)
    public List<ExpenseType> getExpenseTypes(Principal principal) {
        int actualUserId = userDao.findIdByUsername(principal.getName());
        List<ExpenseType> expenseTypes = new ArrayList<>();
        if(actualUserId == userDao.findIdByUsername(principal.getName())) {
            expenseTypes = jdbcExpenseTypeDao.getExpenseTypes();
        }
        return expenseTypes;
    }

    @RequestMapping(path = "/expenses/expenseTypeTotals", method = RequestMethod.GET)
    public List<Double> getExpenseTypeTotals(Principal principal) throws JsonProcessingException {
        int actualUserId = userDao.findIdByUsername(principal.getName());
        List<Double> totals = new ArrayList<>();
        List<ExpenseType> expenseTypeTotals = jdbcExpenseTypeDao.totalExpensesEachType(actualUserId);
        if(actualUserId == userDao.findIdByUsername(principal.getName())) {
            for(ExpenseType current : expenseTypeTotals) {
                totals.add(current.getTotalExpenses());
            }
            Collections.sort(totals);
        }
        return totals;
    }

    @RequestMapping(path = "/expenses/expenseTypeNames", method = RequestMethod.GET)
    public List<String> getExpenseTypeNames(Principal principal) throws JsonProcessingException {
        int actualUserId = userDao.findIdByUsername(principal.getName());
        List<String> names = new ArrayList<>();
        List<ExpenseType> expenseTypeTotals = jdbcExpenseTypeDao.totalExpensesEachType(actualUserId);
        if(actualUserId == userDao.findIdByUsername(principal.getName())) {
            for(ExpenseType current : expenseTypeTotals) {
                names.add(current.getTypeName());
            }
            Collections.sort(names);
        }
        return names;
    }


}
