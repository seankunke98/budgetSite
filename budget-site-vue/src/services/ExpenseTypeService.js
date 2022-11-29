
import axios from 'axios';

export default {
sumOfExpensesByType(typeId) {
    return axios.get(`/expenses/totalExpenses/${typeId}`)
},
sumOfExpensesEachType() {
    return axios.get(`/expenses/expenseTypes/allTypeTotals`)
},
getExpenseTypes() {
    return axios.get(`/expenses/expenseTypes`)
},
}