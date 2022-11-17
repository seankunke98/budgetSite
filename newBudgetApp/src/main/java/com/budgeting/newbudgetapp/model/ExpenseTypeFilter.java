package com.budgeting.newbudgetapp.model;

public class ExpenseTypeFilter {

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof String)) {
            return false;
        }

        String a = (String) other;
        return a.equals("");
    }

}
