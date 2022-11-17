<template>
  <v-container>
  <v-data-table
  id="expense-table"
  v-model="selected"
    :headers="headers"
    :items="expenses"
    :single-select="singleSelect"
    item-key="expenseId"
    sort-by="name"
    show-select
    class="elevation-1"
    :loading="loading"
    :options.sync="options"
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
              dark
              class="mb-2"
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

            <v-card-text v-if="formTitle == 'New Item'">
              <v-container>
                <v-row justify="center">
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                    <v-text-field
                      v-model="newExpense.expenseName"
                      label="Expense Name"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                    <v-text-field
                      v-model="newExpense.expenseAmount"
                      label="Expense Amount"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                  <v-select
          :items="expenseTypeOptions"
          v-model="newExpense.expenseTypeId"
          :item-value="'typeId'"
          :item-text="'typeName'"
          label="Type of Expense"
        ></v-select>
                  </v-col>
                  <v-col cols="12"
                    sm="6"
                    md="4"
                    >
                    <v-date-picker
          v-model="newExpense.expenseDate"
          full-width
        ></v-date-picker></v-col>
                </v-row>
              </v-container>
            </v-card-text>
            <v-card-text v-else>
              <v-container>
                <v-row>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                    <v-text-field
                      v-model="editedExpense.expenseName"
                      label="Expense Name"
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
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                  <v-select
          v-model="editedExpense.expenseTypeId"
          :items="expenseTypeOptions"
          :item-value="'typeId'"
          :item-text="'typeName'"
          label="Type of Expense"
        ></v-select>
                  </v-col>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                  <v-date-picker
          v-model="editedExpense.expenseDate"
          full-width
        ></v-date-picker>
                  </v-col>
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
    <template v-slot:no-data>
      <v-btn
        color="primary"
        @click.prevent="initialize"
      >
        Reset
      </v-btn>
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
      dialog: false,
      selected: [],
      expenseTypeOptions: [],
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
      expenses: [],
      editedIndex: -1,
      editedExpense: {
        expenseId: '',
        expenseName: '',
        expenseAmount: '',
        expenseTypeId: '',
        expenseTypeName: '',
        expenseDate: ''
      },
      newExpense: {
        expenseName: '',
        expenseAmount: '',
        expenseTypeId: '',
        expenseTypeName: '',
        expenseDate: ''
      },
      defaultExpense: {
        expenseId: '',
        expenseName: '',
        expenseAmount: '',
        expenseTypeId: '',
        expenseTypeName: '',
        expenseDate: ''
      },
    }),

    computed: {
      formTitle () {
        return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
      },
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

    methods: {
      initialize () {
        this.loading = true
        ExpenseService.getExpensesByUser().then(response => {
          if(response.status == 200) {
            this.expenses = response.data
            this.$store.commit("SET_EXPENSES", response.data)
          }
          this.loading = false;
        })
      },
      setExpenseTypes () {
        ExpenseService.getExpenseTypes().then(response => {
          if(response.status == 200) {
            this.$store.commit("SET_EXPENSE_TYPES", response.data)
          }
          this.expenseTypeOptions = this.$store.state.expenseTypes
        })
      },
      editItem (expense) {
        this.editedIndex = this.expenses.indexOf(expense)
        this.editedExpense = Object.assign({}, expense)
        this.dialog = true
      },

      deleteItem (expense) {
        this.editedIndex = this.expenses.indexOf(expense)
        this.editedExpense = Object.assign({}, expense)
        this.dialogDelete = true
        this.initialize()
      },

      deleteItemConfirm () {
        this.expenseId = this.editedExpense.expenseId;
        ExpenseService.deleteExpensesById(this.expenseId).then(response => {
          if(response.status == 200 || response.status == 204) {
            this.expenses.splice(response.data)
            this.$store.commit("DELETE_EXPENSE", response.data)
          }
        })
        this.closeDelete()
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
        if(this.formTitle == 'New Item') {
          ExpenseService.addExpense(this.newExpense).then(response => {
            if(response.status == 201) {
              this.expenses.push(response.data)
              this.$store.commit("ADD_EXPENSE", response.data)
            }
          })
        } else {
          Object.assign(this.expenses[this.editedIndex], this.editedItem)
          ExpenseService.editExpense(this.editedExpense).then(response => {
            if(response.status == 200) {
              this.$store.commit("UPDATE_EXPENSE", response.data)
            }
          })
        }
        this.close()
      },
      
    },
    
  }
</script>

<style scoped>

div.container {
  max-height: 50%;
}

</style>