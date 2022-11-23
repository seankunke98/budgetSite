package com.budgeting.newbudgetapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = TotalExpenseFilter.class)
public class ExpenseType {

    private int expenseTypeId;
    private String expenseTypeName;

    private double totalExpenses;

    public ExpenseType(int expenseTypeId, String expenseTypeName) {
        this.expenseTypeId = expenseTypeId;
        this.expenseTypeName = expenseTypeName;
    }

    public ExpenseType(int expenseTypeId, String expenseTypeName, double totalExpenses) {
        this.expenseTypeId = expenseTypeId;
        this.expenseTypeName = expenseTypeName;
        this.totalExpenses = totalExpenses;
    }

    public ExpenseType() {
    }

    public int getExpenseTypeId() {
        return expenseTypeId;
    }

    public String getExpenseTypeName() {
        return expenseTypeName;
    }

    public void setExpenseTypeId(int expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }

    public void setExpenseTypeName(String expenseTypeName) {
        this.expenseTypeName = expenseTypeName;
    }

    public double getTotalExpenses() {
        double expenses = 0;
        if(totalExpenses > 0) {
            expenses = totalExpenses;
        }
        return expenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        if(totalExpenses > 0) {
            this.totalExpenses = totalExpenses;
        }
    }
}
