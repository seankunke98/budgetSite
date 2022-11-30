package com.budgeting.newbudgetapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = TotalExpenseFilter.class)
public class ExpenseType {

    private int expenseTypeId;
    private String typeName;
    private double totalExpenses;

    public ExpenseType(int expenseTypeId, String typeName) {
        this.expenseTypeId = expenseTypeId;
        this.typeName = typeName;
    }
    public ExpenseType(int expenseTypeId, double totalExpenses) {
        this.expenseTypeId = expenseTypeId;
        this.totalExpenses = totalExpenses;
    }

    public ExpenseType() {
    }

    public ExpenseType(String typeName, double totalExpenses) {
        this.typeName = typeName;
        this.totalExpenses = totalExpenses;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public int getExpenseTypeId() {
        return expenseTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setExpenseTypeId(int expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}
