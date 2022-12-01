import axios from 'axios';

export default {
    setExpenseLimits(expenseLimit) {
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
    }
}