package com.budgeting.newbudgetapp.model;

public class ExpenseLimit {
    private String typeName;
    private double expenseLimit;
    private double totalExpenses;

    public ExpenseLimit(String typeName, double expenseLimit, double totalExpenses) {
        this.typeName = typeName;
        this.expenseLimit = expenseLimit;
        this.totalExpenses = totalExpenses;
    }

    public ExpenseLimit(String typeName, double expenseLimit) {
        this.typeName = typeName;
        this.expenseLimit = expenseLimit;
    }

    public ExpenseLimit() {
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getExpenseLimit() {
        return expenseLimit;
    }

    public void setExpenseLimit(double expenseLimit) {
        this.expenseLimit = expenseLimit;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }
}
