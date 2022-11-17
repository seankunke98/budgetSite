package com.budgeting.newbudgetapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = TotalExpenseFilter.class)
public class ExpenseType {

    private int typeId;
    private String typeName;

    private double totalExpenses;

    public ExpenseType(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public ExpenseType(int typeId, String typeName, double totalExpenses) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.totalExpenses = totalExpenses;
    }

    public ExpenseType() {
    }

    public int getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
