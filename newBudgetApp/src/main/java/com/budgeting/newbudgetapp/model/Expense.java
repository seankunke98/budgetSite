package com.budgeting.newbudgetapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = ExpenseTypeFilter.class)
public class Expense {

    private int expenseId;

    private String expenseName;

    private double expenseAmount;

    private int expenseTypeId;

    private String expenseTypeName;

    private LocalDate expenseDate;

    public Expense(int expenseId, String expenseName, double expenseAmount, int expenseTypeId, String expenseTypeName, LocalDate expenseDate) {
        this.expenseId = expenseId;
        this.expenseName = expenseName;
        this.expenseAmount = expenseAmount;
        this.expenseTypeId = expenseTypeId;
        this.expenseTypeName = expenseTypeName;
        this.expenseDate = expenseDate;
    }

    public Expense() {
    }

    public String getExpenseTypeName() {
        return expenseTypeName;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public void setExpenseTypeName(String expenseTypeName) {
        this.expenseTypeName = expenseTypeName;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public int getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(int expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseId=" + expenseId +
                ", expenseName='" + expenseName + '\'' +
                ", expenseAmount=" + expenseAmount +
                ", expenseTypeId=" + expenseTypeId +
                ", expenseTypeName='" + expenseTypeName + '\'' +
                ", expenseDate=" + expenseDate +
                '}';
    }
}
