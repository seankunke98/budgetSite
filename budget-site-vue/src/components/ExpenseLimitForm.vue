<template>
    <v-card elevation="15" tile >
        <v-card-title>Enter a spending limit for certain expense types or for your overall spending.</v-card-title>
        <v-card-text>
    <v-form ref="form" v-model="valid" lazy-validation>
        <v-text-field
        v-model="expenseLimit.expenseLimit"
        :rules="limitRules"
        label="Monthly Expense Limit"
        required
        ></v-text-field>

        <v-select
        v-model="expenseLimit.expenseTypeName"
        :items="items"
        :rules="[(v) => !!v || 'Expense Type is required']"
        label="Expense Type"
        :item-value="'expenseTypeName'"
        :item-text="'expenseTypeName'"
        required
        ></v-select>
    
        <v-card-actions>
        <v-btn :disabled="expenseLimit.expenseLimit == '' || expenseLimit.expenseTypeName == ''" color="success" class="mr-4" @click="setExpenseLimits">
            Submit
        </v-btn>

        <v-btn color="error" class="mr-4" @click="reset"> Reset Form </v-btn>

        <v-btn color="warning" @click="resetValidation"> Reset Validation </v-btn>
    </v-card-actions>
    </v-form>
</v-card-text>

</v-card>
</template>

<script>
import ExpenseLimitService from '@/services/ExpenseLimitService';
export default {
    name: "limit-form",
    data: () => ({
        valid: false,
        limitRules: [
            (v) => !!v || "Limit is required",
            (v) => v > 0 || "Limit must be above 0",
        ],
        expenseLimit: {
            expenseLimit: "",
            expenseTypeName: ""
        },
        items: [],
        }),
    methods: {
        setExpenseTypes() {
            for (let i = 0; i < this.$store.state.expenseTypes.length; i++) {
            this.items.push(this.$store.state.expenseTypes[i]);
        }
        },
        setExpenseLimits() {
            console.log(this.expenseLimit)
            ExpenseLimitService.setExpenseLimits(this.expenseLimit).then((response) => {
                if(response.status == 200) {
                    console.log(response.data)
                    this.reset()
                }
            })
        },
        validate () {
            this.$refs.form.validate()
        },
        reset () {
            this.$refs.form.reset()
        },
        resetValidation () {
            this.$refs.form.resetValidation()
        },
        },
    created() {
        this.setExpenseTypes()
    }
};
</script>

<style>
</style>