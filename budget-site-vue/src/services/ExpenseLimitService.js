import axios from 'axios';

export default {
    addExpenseLimits(expenseLimit) {
        return axios.post(`/expenseLimits/setExpenseLimit`, expenseLimit)
    },
    getExpenseLimits() {
        return axios.get(`/expenseLimits/getExpenseLimits`)
    },
    removeExpenseLimit(expenseLimitId) {
        return axios.delete(`/expenseLimits/removeExpenseLimit/${expenseLimitId}`)
    },
    getAvailableExpenseTypes() {
        return axios.get(`/expenseLimits/availableTypes`)
    },
    updateExpenseLimit(expenseLimit) {
        return axios.put(`/expenseLimits/updateExpenseLimit`, expenseLimit)
    },
    getExpenseLimitsWithTotals() {
        return axios.get(`/expenseLimits/getExpenseLimitsWithTotals`)
    },
    getExpenseLimitProgress() {
        return axios.get(`/expenseLimits/getExpenseLimitProgress`)
    },
}