package com.budgeting.newbudgetapp.model;

public class TimeBasedTotal {

    private String monthName;

    private double totalExpenses;

    public TimeBasedTotal(String monthName, double totalExpenses) {
        this.monthName = monthName;
        this.totalExpenses = totalExpenses;
    }

    public TimeBasedTotal() {
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }
}
