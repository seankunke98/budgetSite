<template>
  <v-card class="mb-10" elevation="6">
    <v-data-table
      id="expense-table"
      :headers="headers"
      :items="tableExpenses"
      item-key="expenseId"
      class="elevation-1"
      :loading="loading"
      :options.sync="options"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>My Expenses</v-toolbar-title>
          <v-divider class="mx-4" inset vertical></v-divider>
          <v-alert
            v-model="createSuccess"
            type="success"
            class="mt-4"
            dense
            shaped
            text
            transition="fade-transition"
            >Expense added!</v-alert
          >
          <v-alert
            v-model="createFailure"
            type="error"
            class="mt-4"
            dense
            shaped
            text
            transition="fade-transition"
            >Failed to add expense, please try again.</v-alert
          >
          <v-alert
            v-model="updateSuccess"
            type="success"
            class="mt-4"
            dense
            shaped
            text
            transition="fade-transition"
            >Expense updated!</v-alert
          >
          <v-alert
            v-model="updateFailure"
            type="error"
            class="mt-4"
            dense
            shaped
            text
            transition="fade-transition"
            >Update failed, please try again.</v-alert
          >
          <v-alert
            v-model="deleteSuccess"
            type="success"
            class="mt-4"
            dense
            shaped
            text
            transition="fade-transition"
            >Expense(s) deleted!</v-alert
          >
          <v-alert
            v-model="deleteFailure"
            type="error"
            class="mt-4"
            dense
            shaped
            text
            transition="fade-transition"
            >Failed to delete expense(s), please try again.</v-alert
          >
          <v-spacer></v-spacer>
          <v-dialog v-model="dialog" max-width="fit-content">
            <template v-slot:activator="{ on, attrs }">
              <v-btn color="primary" @click.prevent="initialize"> Refresh </v-btn>
              <v-divider class="mx-4" inset vertical></v-divider>
              <!-- <v-btn color="primary" @click.prevent="deleteSelectedExpenses">
                Delete Selected
              </v-btn> -->
              <v-divider class="mx-4" inset vertical></v-divider>
              <v-btn color="primary" dark v-bind="attrs" v-on="on">
                New Expense
              </v-btn>
              <v-divider class="mx-4" inset vertical></v-divider>
              <v-btn color="primary" link v-bind:to="{ name: 'home-page' }">
                Home
              </v-btn>
            </template>
            <v-card>
              <v-card-title>
                <span class="text-h5">{{ formTitle }}</span>
              </v-card-title>

              <v-card-text>
                <v-container>
                  <v-row justify="center">
                    <v-col cols="12" sm="6" md="4">
                      <v-text-field
                        v-model="editedExpense.expenseName"
                        label="Expense Name"
                        clearable
                        required
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="4">
                      <v-text-field
                        v-model="editedExpense.expenseAmount"
                        label="Expense Amount"
                        type="number"
                        clearable
                        required
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="4">
                      <v-select
                        :items="expenseTypeOptions"
                        v-model="editedExpense.typeName"
                        :item-value="'typeName'"
                        :item-text="'typeName'"
                        label="Type of Expense"
                        clearable
                        required
                      ></v-select>
                    </v-col>
                    <v-col cols="12" sm="6" md="4">
                      <v-date-picker
                        v-model="editedExpense.expenseDate"
                        full-width
                      ></v-date-picker
                    ></v-col>
                  </v-row>
                </v-container>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click.prevent="close">
                  Cancel
                </v-btn>
                <v-btn color="blue darken-1" text @click.prevent="save">
                  Save
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
          <v-dialog v-model="dialogDelete" max-width="500px">
            <v-card>
              <v-card-title class="text-h5"
                >Are you sure you want to delete this item?</v-card-title
              >
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="closeDelete"
                  >Cancel</v-btn
                >
                <v-btn color="blue darken-1" text @click="deleteItemConfirm"
                  >OK</v-btn
                >
                <v-spacer></v-spacer>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-toolbar>
      </template>
      <!-- <template v-slot:[`item.data-table-select`]="{item}">
        <v-simple-checkbox
          v-model="item.expenseId"
        ></v-simple-checkbox>
      </template> -->
      <template v-slot:body="{items}">
            <tbody>
                <tr v-for="item in items" :key="item.expenseId">
                  <!-- <v-checkbox v-model="selected" :value="item" style="padding-left: 15px" /> -->
                    <td>{{ item.expenseName }}</td>
                    <td>${{ item.expenseAmount }}</td>
                    <td>{{ item.typeName }}</td>
                    <td>{{ item.expenseDate }}</td>
                    <td>
                    <v-icon small class="mr-2" @click="editItem(item)">
                    mdi-pencil
                    </v-icon>
                    <v-icon small @click="deleteItem(item)"> mdi-delete </v-icon>
                    </td>
                </tr>
            </tbody>
            </template>
      <template v-slot:[`item.actions`]="{ item }">
        <v-icon small class="mr-2" @click.prevent="editItem(item)">
          mdi-pencil
        </v-icon>
        <v-icon small @click.prevent="deleteItem(item)"> mdi-delete </v-icon>
      </template>
    </v-data-table>
  </v-card>
</template>

<script>
import ExpenseService from "../services/ExpenseService";
import ExpenseTypeService from "../services/ExpenseTypeService";
export default {
  data: () => ({
    loading: true,
    options: {},
    tableExpenses: [],
    dialog: false,
    expenseTypeOptions: [],
    dialogDelete: false,
    createSuccess: false,
    updateSuccess: false,
    deleteSuccess: false,
    deleteFailure: false,
    updateFailure: false,
    createFailure: false,
    headers: [
      {
        text: "Expense Name",
        align: "start",
        sortable: false,
        value: "expenseName",
      },
      { text: "Expense Amount", value: "expenseAmount" },
      { text: "Expense Type", value: "typeName" },
      { text: "Expense Date", value: "expenseDate" },
      { text: "Actions", value: "actions", sortable: false },
    ],
    editedIndex: -1,
    editedExpense: {
      expenseId: "",
      expenseName: "",
      expenseAmount: "",
      typeName: "",
      expenseDate: "",
    },
    defaultExpense: {
      expenseName: "",
      expenseAmount: "",
      typeName: "",
      expenseDate: "",
    },
  }),
  computed: {
    formTitle() {
      return this.editedIndex === -1 ? "New Item" : "Edit Item";
    },
  },
  watch: {
    dialog(val) {
      val || this.close();
    },
    dialogDelete(val) {
      val || this.closeDelete();
    },
  },
  created() {
    this.initialize(), this.setExpenseTypes();
  },
  mounted() {

  },
  updated() {},
  methods: {
    async initialize() {
      this.loading = true;
      ExpenseService.getExpensesByUser().then((response) => {
        if (response.status == 200) {
          this.tableExpenses = response.data;
        }
        this.loading = false;
      });
    },
    setUpdatedExpenses() {
      ExpenseService.commitExpenseChanges(this.tableExpenses).then(
        (response) => {
          if (response.status == 200) {
            this.$store.commit("SET_EXPENSES", this.tableExpenses);
          }
        }
      );
    },
    setExpenseTypes() {
      ExpenseTypeService.getExpenseTypes().then((response) => {
        if (response.status == 200) {
          this.$store.commit("SET_EXPENSE_TYPES", response.data);
        }
        for (let i = 0; i < this.$store.state.expenseTypes.length - 1; i++) {
          this.expenseTypeOptions.push(this.$store.state.expenseTypes[i]);
        }
      });
    },
    editItem(item) {
      this.editedIndex = this.tableExpenses.indexOf(item);
      this.editedExpense = Object.assign({}, item);
      this.dialog = true;
    },
    // deleteSelectedExpenses() {
    //   ExpenseService.deleteMultipleExpenses(this.selected).then((response) => {
    //     if (response.status == 204) {
    //       this.tableExpenses = this.tableExpenses.filter(
    //         (item) => !this.selected.includes(item)
    //       );
    //       this.deleteSuccess = true;
    //       setTimeout(() => (this.deleteSuccess = false), 4000);
    //     } else {
    //       this.deleteFailure = true;
    //       setTimeout(() => (this.deleteFailure = false), 4000);
    //     }
    //   });
    // },
    // deleteSelectedIds() {
    //   this.selectedIds = this.selected.map((item) => item.expenseId);
    //   ExpenseService.deleteMultipleExpenses(this.selectedIds).then(
    //     (response) => {
    //       if (response.status == 204) {
    //         console.log("Success");
    //       }
    //     }
    //   );
    // },
    deleteItem(item) {
      this.editedIndex = this.tableExpenses.indexOf(item);
      this.editedExpense = Object.assign({}, item);
      this.dialogDelete = true;
    },
    deleteItemConfirm() {
      this.tableExpenses.splice(this.editedIndex, 1);
      ExpenseService.deleteExpensesById(this.editedExpense.expenseId).then(
        (response) => {
          if (response.status == 204) {
            this.deleteSuccess = true;
            setTimeout(() => (this.deleteSuccess = false), 4000);
          } else {
            this.deleteFailure = true;
            setTimeout(() => (this.deleteFailure = false), 4000);
          }
        }
      );
      this.closeDelete();
    },
    close() {
      this.dialog = false;
      this.$nextTick(() => {
        this.editedExpense = Object.assign({}, this.defaultExpense);
        this.editedIndex = -1;
      });
    },
    closeDelete() {
      this.dialogDelete = false;
      this.$nextTick(() => {
        this.editedExpense = Object.assign({}, this.defaultExpense);
        this.editedIndex = -1;
      });
    },
    save() {
      if (this.editedIndex > -1) {
        console.log(this.editedExpense);
        this.tableExpenses.splice(this.editedIndex, 1, this.editedExpense);
        ExpenseService.editExpense(this.editedExpense).then((response) => {
          if (response.status == 200) {
            this.updateSuccess = true;
            setTimeout(() => (this.updateSuccess = false), 4000);
          } else {
            this.updateFailure = false;
            setTimeout(() => (this.updateFailure = true), 4000);
          }
        });
      } else {
        console.log(this.editedExpense);
        this.tableExpenses.unshift(this.editedExpense);
        ExpenseService.addExpense(this.editedExpense).then((response) => {
          if (response.status == 201) {
            this.createSuccess = true;
            setTimeout(() => (this.createSuccess = false), 4000);
          } else {
            this.createFailure = true;
            setTimeout(() => (this.createFailure = false), 4000);
          }
        });
      }
      this.close();
    },
  },
};
</script>

<style scoped>
div.container {
  max-height: 50%;
}
</style>