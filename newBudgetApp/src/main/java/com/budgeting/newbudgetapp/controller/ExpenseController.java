package com.budgeting.newbudgetapp.controller;


import com.budgeting.newbudgetapp.dao.ExpenseDao;
import com.budgeting.newbudgetapp.dao.ExpenseTypeDao;
import com.budgeting.newbudgetapp.dao.JdbcExpenseDao;
import com.budgeting.newbudgetapp.dao.UserDao;
import com.budgeting.newbudgetapp.model.Expense;
import com.budgeting.newbudgetapp.model.TimeBasedTotal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ExpenseController {


    @Autowired
    private JdbcExpenseDao jdbcExpenseDao;
    private final ExpenseDao expenseDao;
    private UserDao userDao;
    private ExpenseTypeDao expenseTypeDao;

    public ExpenseController(ExpenseDao expenseDao, UserDao userDao, ExpenseTypeDao expenseTypeDao) {
        this.expenseDao = expenseDao;
        this.userDao = userDao;
        this.expenseTypeDao = expenseTypeDao;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/expenses")
    public List<Expense> getAllExpenses() {
        return jdbcExpenseDao.getExpenses();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/expenses/addExpense")
    public Expense addExpense(@Valid @RequestBody Expense expense, Principal principal) {
        jdbcExpenseDao.addExpense(expense);
        jdbcExpenseDao.addUserExpense(userDao.findIdByUsername(principal.getName()), expense.getExpenseId());
        return expense;
    }

    @RequestMapping(path = "/expenses/totalExpenses", method = RequestMethod.GET)
    public double sumTotalExpenses(Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        return jdbcExpenseDao.totalExpenses(userId);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses/{expenseId}")
    public void deleteExpensesById(@Valid @PathVariable int expenseId) {
        jdbcExpenseDao.deleteExpense(expenseId);
    }

    @RequestMapping(path = "/expenses/userExpenses", method = RequestMethod.GET)
    public List<Expense> listExpensesByUser(Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        return jdbcExpenseDao.getExpensesByUserId(userId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/expenses/deleteAll", method = RequestMethod.DELETE)
    public void deleteAllExpenses() {
        expenseDao.deleteAll();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/expenses/deleteExpenses/deleteMultipleExpenses", method = RequestMethod.POST)
    public void deleteMultipleExpenses(@RequestBody Expense[] expenses) {
        for(Expense current : expenses) {
            int expenseId = current.getExpenseId();
            expenseDao.deleteExpense(expenseId);
        }
    }

    @RequestMapping(path = "/expenses/editExpense", method = RequestMethod.PUT)
    public void editExpense(@Valid @RequestBody Expense expense) {
        jdbcExpenseDao.updateExpense(expense);
    }

    @RequestMapping(path = "/expenses/expensesLastMonth", method = RequestMethod.GET)
    public double totalExpensesLastMonth() {
        return expenseDao.totalLastMonth();
    }

    @RequestMapping(path = "/expenses/expensesCurrentMonth", method = RequestMethod.GET)
    public double totalExpensesCurrentMonth() {
        return expenseDao.totalCurrentMonth();
    }

    @RequestMapping(path = "/expenses/expensesFullYear", method = RequestMethod.GET)
    public List<Double> totalExpensesFullYear() {
        return expenseDao.totalsFullYear();
    }

    @RequestMapping(path = "/expenses/expensesFullYearWithMonth", method = RequestMethod.GET)
    public List<TimeBasedTotal> totalExpensesFullYearWithMonth(Principal principal) {
        int actualUserId = userDao.findIdByUsername(principal.getName());
        System.out.println(expenseDao.totalsFullYearWithMonth(actualUserId));
        return expenseDao.totalsFullYearWithMonth(actualUserId);
    }

    @RequestMapping(path = "/expenses/addAllExpenses", method = RequestMethod.POST)
    public void addAllAddedExpenses(@RequestBody Expense[] expenses, Principal principal) {
        List<Expense> expenses1 = Arrays.asList(expenses);
        jdbcExpenseDao.multipleExpenseInsert(expenses1);
        for (Expense expense : expenses1) {
            jdbcExpenseDao.addUserExpense(userDao.findIdByUsername(principal.getName()), expense.getExpenseId());
        }
    }


}