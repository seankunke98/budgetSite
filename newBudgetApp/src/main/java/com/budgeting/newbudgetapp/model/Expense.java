package com.budgeting.newbudgetapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = ExpenseTypeFilter.class)
public class Expense {

    private int expenseId;

    private String expenseName;

    private double expenseAmount;

    private int expenseTypeId;

    private String typeName;

    private LocalDate expenseDate;

    public Expense(int expenseId, String expenseName, double expenseAmount, int expenseTypeId, String typeName, LocalDate expenseDate) {
        this.expenseId = expenseId;
        this.expenseName = expenseName;
        this.expenseAmount = expenseAmount;
        this.expenseTypeId = expenseTypeId;
        this.typeName = typeName;
        this.expenseDate = expenseDate;
    }

    public Expense() {
    }

    public String getTypeName() {
        return typeName;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
                ", expenseTypeName='" + typeName + '\'' +
                ", expenseDate=" + expenseDate +
                '}';
    }
}
