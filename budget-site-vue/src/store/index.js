import Vue from "vue"
import Vuex from "vuex"
import axios from "axios"



Vue.use(Vuex)

const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'))
const currentUserExpenses = JSON.parse(sessionStorage.getItem('expenses'))
const allExpenseTypes = JSON.parse(sessionStorage.getItem('expenseTypes'))


if (currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    token: currentToken || '',
    user: currentUser || {},
    expense: {
      expenseId: 0,
      expenseName: 'Name',
      expenseAmount: 0,
      expenseTypeId: 0,
      expenseTypeName: 'Name',
      expenseDate: '2022-01-01'
    },
    expenses: currentUserExpenses || [],
    expenseTypes: allExpenseTypes || [],
    totalExpensesByType: [],
    totalExpensesByDate: [],
    expenseLimits: []
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
    SET_EXPENSE_TYPES(state, expenseTypes) {
      state.expenseTypes = expenseTypes;
      let parsed = JSON.stringify(expenseTypes)
      let parsedObj = JSON.parse(parsed);
      sessionStorage.setItem('expenseTypes', JSON.stringify(parsedObj));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      localStorage.removeItem('expenses')
      localStorage.removeItem('expenseTypes')
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    },
    SET_EXPENSES(state, expenses) {
      state.expenses = expenses;
      let parsed = JSON.stringify(expenses)
      let parsedObj = JSON.parse(parsed);
      sessionStorage.setItem('expenses', JSON.stringify(parsedObj));
    },
    ADD_EXPENSES(state, expenses) {
      state.expenses = state.expenses.concat(expenses)
    },
    ADD_EXPENSE(state, expense) {
      state.expenses = state.expenses.push(expense)
    },
    UPDATE_EXPENSE(state, expense) {
      console.log(expense)
      const index = state.expenses.findIndex(value => value.expenseId === expense.expenseId)
      state.expenses[index] = expense
      },
    DELETE_EXPENSE(state, index) {
      let expenses = state.expenses
      state.expenses.splice(index, 1);
      sessionStorage.setItem('expenses', JSON.stringify(expenses));
    },
    SET_EXPENSE_TYPE_TOTALS(state, data) {
      state.totalExpensesByType = data;
    },
    SET_EXPENSE_DATE_TOTALS(state, data) {
      state.totalExpensesByDate = data;
    },
    SET_LIMITS(state, limits) {
      state.expenseLimits = limits
      let parsed = JSON.stringify(limits)
      let parsedObj = JSON.parse(parsed);
      sessionStorage.setItem('expenseLimits', JSON.stringify(parsedObj));
    }
  },
  getters: {
    expenses: (state) => {
      return state.expenses
    },
    expenseTypes: (state) => {
      return state.expenseTypes
    },
  },
  actions: {
    addExpenses({ commit }, expensesToAdd) {
      commit('ADD_EXPENSES', expensesToAdd)
    },
    addExpense(state, expense) {
      state.commit('ADD_EXPENSE', expense)
    },
    updateExpense({ commit }, updatedExpense) {
      commit('UPDATE_EXPENSE', updatedExpense)
    }
  },
  strict: true
})
