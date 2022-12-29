package com.budgeting.newbudgetapp.model;

import java.util.Objects;

public class ExpenseLimit {
    private int expenseLimitId;
    private String typeName;
    private double expenseLimit;
    private double totalExpenses;

    private int typeId;


    public ExpenseLimit(int expenseLimitId, String typeName, int typeId, double expenseLimit) {
        this.expenseLimitId = expenseLimitId;
        this.expenseLimit = expenseLimit;
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public ExpenseLimit(int expenseLimitId, String typeName, double expenseLimit, double totalExpenses, int typeId) {
        this.expenseLimitId = expenseLimitId;
        this.typeName = typeName;
        this.expenseLimit = expenseLimit;
        this.totalExpenses = totalExpenses;
        this.typeId = typeId;
    }

    public ExpenseLimit() {
    }

    public int getTypeId() {
        if(Objects.equals(typeName, "Medical")) {
            return 1;
        } else if (Objects.equals(typeName, "Home")) {
            return 2;
        } else if (Objects.equals(typeName, "Automotive")) {
            return 3;
        } else if (Objects.equals(typeName, "Grocery")) {
            return 4;
        } else if (Objects.equals(typeName, "Takeout")) {
            return 5;
        } else if (Objects.equals(typeName, "Entertainment")) {
            return 6;
        } else {
            return 7;
        }
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;

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
