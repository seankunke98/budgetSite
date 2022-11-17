import Vue from "vue"
import Vuex from "vuex"
import axios from "axios"



Vue.use(Vuex)

const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if (currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    token: currentToken || '',
    user: currentUser || {},
    expenses: [],
    expenseTypes: [],
    totalExpensesByType: [],
    totalExpensesByDate: [],
    typeTotals: [],
    typeNames: []
  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user', JSON.stringify(user));
    },
    SET_EXPENSE_TYPES(state, data) {
      state.expenseTypes = data;
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    },
    SET_EXPENSES(state, data) {
      state.expenses = data;
    },
    ADD_EXPENSE(state, newExpense) {
      state.expenses.unshift(newExpense)
    },
    UPDATE_EXPENSE(state, editedIndex, editedExpense) {
      state.expenses[editedIndex] = editedExpense;
    },
    DELETE_EXPENSE(state, expenseId) {
      state.expenses = state.expenses.filter((expense) => {
        return expense.expenseId !== expenseId
      })
    },
    SET_EXPENSE_TYPE_TOTALS(state, data) {
      state.totalExpensesByType = data;
    },
    SET_EXPENSE_DATE_TOTALS(state, data) {
      state.totalExpensesByDate = data;
    },
    SET_TOTALS(state, data) {
      state.typeTotals = data
    },
    SET_TYPE_NAMES(state, data) {
      state.typeNames = data
    }
  },
  getters: {
    
    
  },
  strict: true
})
