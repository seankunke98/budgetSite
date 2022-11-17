import axios from 'axios';

export default {

    getExpenses() {
        return axios.get(`/expenses`)
    },
    getExpensesByUser() {
        return axios.get(`/expenses/userExpenses`)
    },
    addExpense(expense) {
        return axios.post(`/expenses/addExpense`, expense)
    },
    deleteExpensesById(expenseId) {
        return axios.delete(`/expenses/deleteExpenses/${expenseId}`)
    },
    editExpense(expense) {
        return axios.put(`/expenses/editExpense`, expense)
    },
    sumOfExpensesByType(typeId) {
        return axios.get(`/expenses/totalExpenses/${typeId}`)
    },
    sumOfExpensesEachType() {
        return axios.get(`/expenses/expenseTypes/allTypeTotals`)
    },
    getExpenseTypes() {
        return axios.get(`/expenses/expenseTypes`)
    },
    getTypeTotals() {
        return axios.get(`/expenses/expenseTypeTotals`)
    },
    getTypeNames() {
        return axios.get(`/expenses/expenseTypeNames`)
    },
    getLastMonthTotal() {
        return axios.get(`/expenses/expensesLastMonth`)
    },
    getCurrentMonthTotal() {
        return axios.get(`/expenses/expensesCurrentMonth`)
    },
    getFullYearTotals() {
        return axios.get(`/expenses/expensesFullYear`)
    },
    getFullYearTotalsWithMonth() {
        return axios.get(`/expenses/expensesFullYearWithMonth`)
    }


}