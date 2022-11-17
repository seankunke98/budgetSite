package com.example.model;

public class SelectedExpenses {

    private int[] selectedExpenses;

    public SelectedExpenses(int[] selectedExpenses) {
        this.selectedExpenses = selectedExpenses;
    }

    public SelectedExpenses() {
    }

    public int[] getSelectedExpenses() {
        return selectedExpenses;
    }

    public void setSelectedExpenses(int[] selectedExpenses) {
        this.selectedExpenses = selectedExpenses;
    }
}
