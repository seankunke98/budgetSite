import axios from 'axios';

export default {
    setExpenseLimits(expenseLimit) {
        return axios.post(`/expenses/setExpenseLimit`, expenseLimit)
    },
    getExpenseLimits() {
        return axios.get(`/expenses/getExpenseLimits`)
    }
}