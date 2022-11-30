package com.budgeting.newbudgetapp.model;

public class ExpenseLimit {
    private int expenseLimitId;
    private String typeName;
    private double expenseLimit;
    private double totalExpenses;

    public ExpenseLimit(int expenseLimitId, double expenseLimit, double totalExpenses) {
        this.expenseLimitId = expenseLimitId;
        this.expenseLimit = expenseLimit;
        this.totalExpenses = totalExpenses;
    }

    public ExpenseLimit(int expenseLimitId, String typeName, double expenseLimit) {
        this.expenseLimitId = expenseLimitId;
        this.expenseLimit = expenseLimit;
        this.typeName = typeName;
    }

    public ExpenseLimit() {
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getExpenseLimitId() {
        return expenseLimitId;
    }

    public void setExpenseLimitId(int expenseLimitId) {
        this.expenseLimitId = expenseLimitId;
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
