<template>
    <v-card class="mb-10" elevation="6">
        <v-data-table
        :headers="headers"
        :items="limits"
        :loading="loading"
        :options.sync="options"
        item-key="expenseLimitId"
        sort-by="name"
        class="elevation-1"
        >
            <template v-slot:top>
                <v-toolbar flat>
                    <v-toolbar-title>My Expense Limits</v-toolbar-title>
                    <v-divider class="mx-4" inset vertical></v-divider>
                    <v-spacer></v-spacer>
                        <v-dialog v-model="dialog" max-width="fit-content">
                        <template v-slot:activator="{ on, attrs }">
                        <v-divider class="mx-4" inset vertical></v-divider>
                        <v-btn color="primary" @click.prevent="initialize"> Reset </v-btn>
                        <v-divider class="mx-4" inset vertical></v-divider>
                        <v-btn color="primary" dark v-bind="attrs" v-on="on">
                        Set New Expense Limit
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
                            <v-select
                            v-model="editedItem.typeName"
                            :items="remainingOptions"
                            label="Expense Type"
                            :item-value="'typeName'"
                            :item-text="'typeName'"
                            required
                            ></v-select>
                            </v-col>
                            <v-col cols="12" sm="6" md="4">
                            <v-text-field
                            v-model="editedItem.expenseLimit"
                            prefix="$"
                            type="number"
                            label="Spending Limit"
                            ></v-text-field>
                            </v-col>
                            <v-col cols="12" sm="6" md="4"> </v-col>
                        </v-row>
                    </v-container>
            </v-card-text>

                <v-card-actions>
                <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="close">
                    Cancel
                    </v-btn>
                <v-btn color="blue darken-1" text @click="save(); setAvailableExpenseTypes();"> Save </v-btn>
                </v-card-actions>
            </v-card>
            </v-dialog>
            <v-dialog v-model="dialogDelete" max-width="500px">
            <v-card>
                <v-card-title class="text-h5"
                >Are you sure you want to delete this item?</v-card-title>
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
        <template v-slot:body>
            <tbody v-if="limits.length < 1">
                <tr v-for="item in expenseTypeOptions" :key="item.typeName">
                    <td>{{ item.typeName }}</td>
                    <td>${{ item.totalExpenses }}</td>
                    <td>${{ item.expenseLimit }}</td>
                    <td>
                    <v-icon small class="mr-2" @click="editItem(item)">
                    mdi-pencil
                    </v-icon>
                    <v-icon small @click="deleteItem(item)"> mdi-delete </v-icon>
            </td>
            </tr>
            </tbody>
            <tbody v-else>
                <tr v-for="item in limits" :key="item.typeName">
                    <td>{{ item.typeName }}</td>
                    <td>${{ item.totalExpenses }}</td>
                    <td>${{ item.expenseLimit }}</td>
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
                <v-icon small class="mr-2" @click="editItem(item)"> mdi-pencil </v-icon>
                <v-icon small @click="deleteItem(item)"> mdi-delete </v-icon>
            </template>
            <template v-slot:no-data>
                <v-btn color="primary" @click="initialize"> Reset </v-btn>
            </template>
        </v-data-table>
    </v-card>
</template>

<script>
import ExpenseLimitService from "@/services/ExpenseLimitService";
export default {
    name: "expense-limit-table",
    data: () => ({
        dialog: false,
        dialogDelete: false,
        options: {},
        headers: [
            {
            text: "Expense Type",
            align: "start",
            sortable: false,
            value: "typeName",
            },
            { text: "Amount Spent ($)", value: "totalExpenses" },
            { text: "Spending Limit ($)", value: "expenseLimit" },
            { text: "Actions", value: "actions", sortable: false },
        ],
        expenseTypeOptions: [],
        remainingOptions: [],
        limits: [],
        editedIndex: -1,
        loading: false,
        editedItem: {
            expenseLimitId: "",
            typeName: "",
            expenseLimit: "",
        },
        defaultItem: {
            typeName: "",
            expenseLimit: "",
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
        this.initialize();
        this.setExpenseTypes();
    },
    mounted() {
        this.setAvailableExpenseTypes();
    },
    methods: {
        initialize() {
        ExpenseLimitService.getExpenseLimits().then((response) => {
            if (response.status == 200) {
                console.log(response.data);
                this.limits = response.data;
            }
            });
        },
        setExpenseTypes() {
            for (let i = 0; i < this.$store.state.expenseTypes.length; i++) {
                this.expenseTypeOptions.push(this.$store.state.expenseTypes[i]);
            }
        },
        setAvailableExpenseTypes() {
            ExpenseLimitService.getAvailableExpenseTypes().then((response) => {
                if(response.status == 200) {
                    console.log(response.data);
                    this.remainingOptions = response.data
                }
            })
        },
        editItem(item) {
            this.editedIndex = this.limits.indexOf(item);
            this.editedItem = Object.assign({}, item);
            this.dialog = true;
        },

        deleteItem(item) {
            this.editedIndex = this.limits.indexOf(item);
            this.editedItem = Object.assign({}, item);
            this.dialogDelete = true;
        },

        deleteItemConfirm() {
            this.limits.splice(this.editedIndex, 1);
            ExpenseLimitService.removeExpenseLimit(this.editedItem.expenseLimitId).then((response) => {
                if (response.status == 200) {
                    console.log("Success");
                }
            });
            this.closeDelete();
        },

        close() {
            this.dialog = false;
            this.$nextTick(() => {
                this.editedItem = Object.assign({}, this.defaultItem);
                this.editedIndex = -1;
            });
        },

        closeDelete() {
            this.dialogDelete = false;
            this.$nextTick(() => {
                this.editedItem = Object.assign({}, this.defaultItem);
                this.editedIndex = -1;
            });
        },

        save() {
            if (this.editedIndex > -1) {
                Object.assign(this.limits[this.editedIndex], this.editedItem);
            } else {
                this.limits.push(this.editedItem);
                ExpenseLimitService.setExpenseLimits(this.editedItem).then((response) => {
                    if (response.status == 200) {
                        console.log(response.data);
                        }
                    }
                );
            }
            this.close();
        },
    },
};
</script>

<style>
</style>