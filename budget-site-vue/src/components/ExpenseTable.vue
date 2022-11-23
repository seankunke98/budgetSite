<template>
  <v-container>
  <v-data-table
  id="expense-table"
  
    :headers="headers"
    :items="tableExpenses"
    :single-select="singleSelect"
    item-key="expenseId"
    sort-by="name"
    show-select
    class="elevation-1"
    :loading="loading"
    :options.sync="options"
    v-model="selected"
  >
    <template v-slot:top>
      <v-toolbar
        flat
      >
        <v-toolbar-title>My Expenses</v-toolbar-title>
        <v-divider class="mx-4"
          inset
          vertical></v-divider>
        <v-switch
        v-model="singleSelect"
        label="Single select"
        class="pa-3"
      ></v-switch>
        <v-divider
          class="mx-4"
          inset
          vertical
        ></v-divider>
        <v-spacer></v-spacer>
        <v-dialog
          v-model="dialog"
          max-width="fit-content"
        >
          <template v-slot:activator="{ on, attrs }">
            
            <v-btn
        color="primary"
        @click.prevent="deleteSelectedExpenses"
      >
        Delete Selected
      </v-btn>
      <v-divider
            class="mx-4"
          inset
          vertical></v-divider>
            <v-btn
        color="primary"
        @click.prevent="initialize"
      >
        Reset
      </v-btn>
      <v-divider
            class="mx-4"
          inset
          vertical></v-divider>
            <v-btn
              color="primary"
              dark
              class="mx-4"
              v-bind="attrs"
              v-on="on"
            >
              New Expense
            </v-btn>      
          </template>
          <v-card>
            <v-card-title>
              <span class="text-h5">{{ formTitle }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row justify="center">
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                    <v-text-field
                    v-model="editedExpense.expenseName"
                      label="Expense Name"
                      clearable
                      required
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                    <v-text-field
                    v-model="editedExpense.expenseAmount"
                      label="Expense Amount"
                      type="number"
                      clearable
                      required
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                  <v-select
          :items="expenseTypeOptions"
          v-model="editedExpense.expenseTypeName"
          :item-value="'expenseTypeName'"
          :item-text="'expenseTypeName'"
          label="Type of Expense"
          clearable
          required
        ></v-select>
                  </v-col>
                  <v-col cols="12"
                    sm="6"
                    md="4"
                    >
                    <v-date-picker
                    v-model="editedExpense.expenseDate"
          full-width
        ></v-date-picker></v-col>
                </v-row>
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                color="blue darken-1"
                text
                @click.prevent="close"
              >
                Cancel
              </v-btn>
              <v-btn
                color="blue darken-1"
                text
                @click.prevent="save"
              >
                Save
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogDelete" max-width="500px">
          <v-card>
            <v-card-title class="text-h5">Are you sure you want to delete this item?</v-card-title>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="closeDelete">Cancel</v-btn>
              <v-btn color="blue darken-1" text @click="deleteItemConfirm">OK</v-btn>
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
    <template v-slot:[`item.actions`]="{ item }">
      <v-icon
        small
        class="mr-2"
        @click.prevent="editItem(item)"
      >
        mdi-pencil
      </v-icon>
      <v-icon
        small
        @click.prevent="deleteItem(item)"
      >
        mdi-delete
      </v-icon>
    </template>
  </v-data-table>

</v-container>
</template>

<script>

import ExpenseService from '../services/ExpenseService'
  export default {
    data: () => ({
      loading: true,
      totalExpenses: 0,
      options: {},
      tableExpenses: [],
      dialog: false,
      currentSessionExpenses: [],
      expenseTypeOptions: [],
      selectedIds: [],
      selected: [],
      selectedType: '',
      dialogDelete: false,
      singleSelect: false,
      headers: [
        {
          text: 'Expense Name',
          align: 'start',
          sortable: false,
          value: 'expenseName',
        },
        { text: 'Expense Amount', value: 'expenseAmount' },
        { text: 'Expense Type', value: 'expenseTypeName' },
        { text: 'Expense Date', value: 'expenseDate' },
        { text: 'Actions', value: 'actions', sortable: false }
      ],
      
      editedIndex: -1,
      currentSessionIndex: -1,
      editedExpense: {
        expenseId: '',
        expenseName: '',
        expenseAmount: '',
        expenseTypeName: '',
        expenseDate: ''
      },
      // newExpense: {
      //   expenseName: '',
      //   expenseAmount: '',
      //   expenseTypeId: '',
      //   expenseTypeName: '',
      //   expenseDate: ''
      // },
      defaultExpense: {
        expenseName: '',
        expenseAmount: '',
        expenseTypeName: '',
        expenseDate: ''
      },
    }),

    computed: {
      formTitle () {
        return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
      },
      // ...mapState({
      //   stateExpenses: 'expenses'
      // }),
      // expenses: {
      //   get() {
      //     return this.stateExpenses
      //   },
      //   set(newExpenses) {
      //     return newExpenses
      //   }
      // }
    },

    watch: {
      dialog (val) {
        val || this.close()
      },
      dialogDelete (val) {
        val || this.closeDelete()
      },
    },

    created () {
      this.initialize(),
      this.setExpenseTypes()
    },
    // unmounted() {
    //   this.setUpdatedExpenses()
    // },
    updated() {
      
    },
    methods: {
      async initialize () {
        this.loading = true
        ExpenseService.getExpensesByUser().then(response => {
          if(response.status == 200) {
            this.tableExpenses = response.data
          }
          this.loading = false;
        })
      },
      setUpdatedExpenses () {
        ExpenseService.commitExpenseChanges(this.tableExpenses).then(response => {
          if(response.status == 200) {
            this.$store.commit("SET_EXPENSES", this.tableExpenses)
          }
        })
      },
      setExpenseTypes () {
        ExpenseService.getExpenseTypes().then(response => {
          if(response.status == 200) {
            this.$store.commit("SET_EXPENSE_TYPES", response.data)
            this.expenseTypeOptions = response.data
          }
        })
      },
      editItem (item) {
        this.editedIndex = this.tableExpenses.indexOf(item)
        console.log(item)
        this.editedExpense = Object.assign({}, item)
        this.dialog = true
      },

      deleteSelectedExpenses() {
        this.tableExpenses = this.tableExpenses.filter(item => !this.selected.includes(item))
      },

      deleteSelectedIds() {
        this.selectedIds = this.selected.map(item => item.expenseId)
        ExpenseService.deleteMultipleExpenses(this.selectedIds).then((response) => {
          if(response.status == 200) {
            console.log("Success")
          }
        })
      },

      deleteItem (item) {
        this.editedIndex = this.tableExpenses.indexOf(item)
        this.editedExpense = Object.assign({}, item)
        this.dialogDelete = true
      },

      deleteItemConfirm () {
        ExpenseService.deleteExpensesById(this.editedExpense.expenseId).then((response) => {
          if(response.status == 200) {
            console.log(location)
            this.tableExpenses.splice(this.editedIndex, 1)
          }
        })
        this.closeDelete()
        // let id = this.editedExpense.expenseId;
        // ExpenseService.deleteExpensesById(id).then(response => {
        //   if(response.status == 200 || response.status == 204) {
        //     this.$store.commit("DELETE_EXPENSE", response.data)
        //   }
        // })
        // this.initialize()
      },

      close () {
        this.dialog = false
        this.$nextTick(() => {
          this.editedExpense = Object.assign({}, this.defaultExpense)
          this.editedIndex = -1
        })
      },

      closeDelete () {
        this.dialogDelete = false
        this.$nextTick(() => {
          this.editedExpense = Object.assign({}, this.defaultExpense)
          this.editedIndex = -1
        })
      },
      save () {
        if (this.editedIndex > -1) {
          console.log(this.editedExpense)
          const expense = this.editedExpense
          ExpenseService.editExpense(expense).then((response) => {
            if(response.status == 200) {
              this.tableExpenses[this.editedIndex] = this.editedExpense
            }
          })
        } else {
          console.log(this.editedExpense)
          const expense = this.editedExpense
          ExpenseService.addExpense(expense).then((response) => {
            if(response.status == 200) {
              this.tableExpenses.push(this.editedExpense)
            }
          })
        }
        this.close()
      },
        // if(this.editedIndex > -1) {
        //   // this.$set(this.expenses[this.editedIndex], '')
        //   // Object.assign(this.tableExpenses[this.editedIndex], this.editedExpense)
        //   ExpenseService.editExpense(this.editedExpense).then(response => {
        //     if(response.status == 200) {
        //       this.expenses.$set(this.editedIndex, 1, this.editedExpense)
        //       // Object.assign(this.tableExpenses[this.editedIndex], this.editedExpense)
        //     }
        //   })
        // } else {
        //   ExpenseService.addExpense(this.editedExpense).then(response => {
        //     if(response.status == 201) {
        //       this.$set(this.expenses, this.editedIndex, this.editedExpense)
        //     }
        //   })
        //   this.$nextTick(() => {
        //   this.expenses.push(this.editedExpense)
        // })
        // }
        // this.close()
    },
  }
</script>

<style scoped>

div.container {
  max-height: 50%;
}

</style>