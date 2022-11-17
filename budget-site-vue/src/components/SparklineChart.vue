<template>
    <v-container fluid>
    <v-sparkline
    :fill="fill"
    :gradient="selectedGradient"
    label-size="3"
    :smooth="radius || false"
    :labels="labels"
    :value="value"
    :padding="padding"
    stroke-linecap="round"
    color="purple"
    auto-draw
    >
</v-sparkline>
    
    

    <v-divider></v-divider>

    <v-row dense align="center">
    <v-col cols="6" md="6" class="pl-10 pt-10">
        <v-row class="fill-height">
        <v-item-group v-model="selectedGradient" mandatory>
            <v-row>
            <v-item
                v-for="(gradient, i) in gradients"
                :key="i"
                v-slot="{ active, toggle }"
                :value="gradient"
                
            >
                <v-card
                :style="{
                    background:
                    gradient.length > 1
                        ? `linear-gradient(0deg, ${gradient})`
                        : gradient[0],
                    border: '2px solid',
                    borderColor: active ? '#222' : 'white',
                }"
                width="30"
                height="30"
                class="mr-2"
                @click.native="toggle"
                ></v-card>
            </v-item>
            </v-row>
        </v-item-group>
        </v-row>
    </v-col>
    <v-col cols="6" class="pl-10 pt-10">
        <v-row class="fill-height">
        <v-switch v-model="fill" label="Filled"></v-switch>
        </v-row>
    </v-col>

    <v-col cols="12" md="6" class="pl-10">
        <v-slider
        v-model="radius"
        label="Radius"
        min="0"
        max="25"
        thumb-label
        ></v-slider>
    </v-col>

    <v-col cols="12" md="6" >
        <v-slider
        v-model="padding"
        label="Padding"
        min="0"
        max="25"
        thumb-label
        ></v-slider>
    </v-col>
    </v-row>

    <v-divider></v-divider>

    <v-card-actions class="justify-center">
    <v-btn
        block
        text
    >
        Go to Report
    </v-btn>
    </v-card-actions>

    </v-container>
</template>

<script>
const gradients = [
["#222"],
["#42b3f4"],
["red", "orange", "yellow"],
["purple", "violet"],
["#00c6ff", "#F0F", "#FF0"],
["#f72047", "#ffd200", "#1feaea"],
];

import ExpenseService from '../services/ExpenseService'

export default {
data: () => ({
    fill: true,
    selectedGradient: gradients[4],
    loading: false,
    gradients,
    padding: 10,
    radius: 10,
    value: [],
    labels: []
}),
methods: {
    async getValues() {
        this.loading = true;
        ExpenseService.getFullYearTotalsWithMonth().then(response => {
            if(response.status == 200) {
                console.log(response.data)
                this.$store.commit("SET_EXPENSE_DATE_TOTALS", response.data)
            }
            for (let i = 0; i < this.$store.state.totalExpensesByDate.length; i++) {
        this.value.push(
        this.$store.state.totalExpensesByDate[i].totalExpenses
        )
        this.labels.push(
        this.$store.state.totalExpensesByDate[i].monthName
        );
    }
            this.loading = false
        })
    },
},
mounted() {
    this.getValues()
}

};
</script>

<style>
</style>