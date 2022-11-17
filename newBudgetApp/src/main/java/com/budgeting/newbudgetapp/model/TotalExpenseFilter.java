package com.budgeting.newbudgetapp.model;

public class TotalExpenseFilter {

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof Double)) {
            return false;
        }

        double a = (double) other;
        return a <= 0;
    }




}
