<template>
<v-card class="mb-10" max-width="2000">
    <v-container>
        <v-card elevation="5" height="100">
            <v-card-title>Total Percentage Spent This Month - Medical:</v-card-title>
    <v-progress-linear
        :value="(medicalProgress.length == 0)?defaultProgress:Math.floor(100 * (medicalProgress.totalExpenses / medicalProgress.expenseLimit))"
        color="red"
        height="25"
    >
        <template v-slot:default="{ value }">
            <strong>{{ value }}%</strong>
        </template>
    </v-progress-linear>
</v-card>
    <br>
    <v-card elevation="5" max-height="1000">
        <v-card-title>Total Percentage Spent This Month - Home:</v-card-title>
    <v-progress-linear
        :value="(homeProgress.length == 0)?defaultProgress:Math.floor(100 * (homeProgress.totalExpenses / homeProgress.expenseLimit))"
        color="green"
        height="25"
    >
        <template v-slot:default="{ value }">
            <strong>{{ value }}%</strong>
        </template>
    </v-progress-linear>
</v-card>
    <br>
    <v-card elevation="5" max-height="1000">
        <v-card-title>Total Percentage Spent This Month - Automotive:</v-card-title>
    <v-progress-linear
        :value="(automotiveProgress.length == 0)?defaultProgress:Math.floor(100 * (automotiveProgress.totalExpenses / automotiveProgress.expenseLimit))"
        color="blue"
        height="25"
    >
        <template v-slot:default="{ value }">
            <strong>{{ value }}%</strong>
        </template>
    </v-progress-linear>
</v-card>
    <br>
    <v-card elevation="5" max-height="1000">
        <v-card-title>Total Percentage Spent This Month - Grocery:</v-card-title>
    <v-progress-linear
        :value="(groceryProgress.length == 0)?defaultProgress:Math.floor(100 * (groceryProgress.totalExpenses / groceryProgress.expenseLimit))"
        color="purple"
        height="25"
    >
        <template v-slot:default="{ value }">
            <strong>{{ value }}%</strong>
        </template>
    </v-progress-linear>
</v-card>
    <br>
    <v-card elevation="5" max-height="1000">
        <v-card-title>Total Percentage Spent This Month - Takeout:</v-card-title>
    <v-progress-linear
        :value="(takeoutProgress.length == 0)?defaultProgress:Math.floor(100 * (takeoutProgress.totalExpenses / takeoutProgress.expenseLimit))"
        color="orange"
        height="25"
    >
    </v-progress-linear>
</v-card>
    <br>
    <v-card elevation="5" max-height="1000">
        <v-card-title>Total Percentage Spent This Month - Entertainment:</v-card-title>
    <v-progress-linear
        :value="(entertainmentProgress.length == 0)?defaultProgress:Math.floor(100 * (entertainmentProgress.totalExpenses / entertainmentProgress.expenseLimit))"
        color="yellow"
        height="25"
    >
        <template v-slot:default="{ value }">
            <strong>{{ value }}%</strong>
        </template>
    </v-progress-linear>
</v-card>
    <br>
    <v-card elevation="5" max-height="1000">
        <v-card-title>Total Percentage Spent This Month - Overall:</v-card-title>
    <v-progress-linear
        :value="(totalProgress.length == 0)?defaultProgress:Math.floor(100 * (totalProgress.totalExpenses / totalProgress.expenseLimit))"
        color="black"
        height="25"
    >
        <template v-slot:default="{ value }">
            <strong>{{ value }}%</strong>
        </template>
    </v-progress-linear>
</v-card>
<v-card class="d-flex justify-center" flat>
    <v-card-actions>
        <v-btn color="primary" @click="getLimits"> Refresh </v-btn>
    </v-card-actions>
</v-card>
</v-container>
</v-card>
</template>

<script>
import ExpenseLimitService from '@/services/ExpenseLimitService';
export default {
    name: 'limit-progress',
    data: () => ({
        medicalProgress: {
            expenseLimit: 0,
            totalExpenses: 0
        },
        homeProgress: {
            expenseLimit: 0,
            totalExpenses: 0
        },
        automotiveProgress: {
            expenseLimit: 0,
            totalExpenses: 0
        },
        groceryProgress: {
            expenseLimit: 0,
            totalExpenses: 0
        },
        takeoutProgress: {
            expenseLimit: 0,
            totalExpenses: 0
        },
        entertainmentProgress: {
            expenseLimit: 0,
            totalExpenses: 0
        },
        totalProgress: {
            expenseLimit: 0,
            totalExpenses: 0
        },
        limits: [],
        defaultProgress: 0
    }),
    computed: {
        formTitle() {
            return this.editedIndex === -1 ? "Set New Limit" : "Edit Current Item";
        },
    },
    created() {
        this.getLimits()
    },
    mounted() {
        
    },
    methods: {
        getLimits() {
            ExpenseLimitService.getExpenseLimits().then((response) => {
            if(response.status == 200) {
                console.log(response.data)
                this.limits = response.data
            }
            for(let i = 0; i < this.limits.length; i++) {
                if(this.limits[i].typeName == "Medical") {
                    this.medicalProgress.expenseLimit = this.limits[i].expenseLimit
                    this.medicalProgress.totalExpenses = this.limits[i].totalExpenses
                    console.log(this.medicalProgress)
                } else if(this.limits[i].typeName == 'Home') {
                    this.homeProgress.expenseLimit = this.limits[i].expenseLimit
                    this.homeProgress.totalExpenses = this.limits[i].totalExpenses
                    console.log(this.homeProgress)
                } else if(this.limits[i].typeName == 'Automotive') {
                    this.automotiveProgress.expenseLimit = this.limits[i].expenseLimit
                    this.automotiveProgress.totalExpenses = this.limits[i].totalExpenses
                    console.log(this.automotiveProgress)
                } else if(this.limits[i].typeName == 'Grocery') {
                    this.groceryProgress.expenseLimit = this.limits[i].expenseLimit
                    this.groceryProgress.totalExpenses = this.limits[i].totalExpenses
                    console.log(this.groceryProgress)
                } else if(this.limits[i].typeName == 'Takeout') {
                    this.takeoutProgress.expenseLimit = this.limits[i].expenseLimit
                    this.takeoutProgress.totalExpenses = this.limits[i].totalExpenses
                } else if(this.limits[i].typeName == 'Entertainment') {
                    this.entertainmentProgress.expenseLimit = this.limits[i].expenseLimit
                    this.entertainmentProgress.totalExpenses = this.limits[i].totalExpenses
                } else {
                    this.totalProgress.expenseLimit = this.limits[i].expenseLimit
                    this.totalProgress.totalExpenses = this.limits[i].totalExpenses
                }
            }
        })
        },
        
    }
}
</script>

<style>

</style>