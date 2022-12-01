<template>
<v-card class="mb-10">
    <v-progress-linear
        
        value="medicalProgress"
        color="red"
        height="25"
    >
        <template v-slot:default="{ value }">
            <strong>{{ value }}%</strong>
        </template>
    </v-progress-linear>
    <br>
    <v-progress-linear
        value="homeProgress"
        color="green"
        height="25"
    >
        <template v-slot:default="{ value }">
            <strong>{{ value }}%</strong>
        </template>
    </v-progress-linear>
    <br>
    <v-progress-linear
        value="automotiveProgress"
        color="blue"
        height="25"
    >
        <template v-slot:default="{ value }">
            <strong>{{ value }}%</strong>
        </template>
    </v-progress-linear>
    <br>
    <v-progress-linear
        value="groceryProgress"
        color="purple"
        height="25"
    >
        <template v-slot:default="{ value }">
            <strong>{{ value }}%</strong>
        </template>
    </v-progress-linear>
    <br>
    <v-progress-linear
        v-model="takeoutProgress"
        color="orange"
        height="25"
    >
    </v-progress-linear>
    <br>
    <v-progress-linear
        v-model="entertainmentProgress"
        color="yellow"
        height="25"
    >
        <template v-slot:default="{ value }">
            <strong>{{ value }}%</strong>
        </template>
    </v-progress-linear>
    <br>
    <v-progress-linear
        v-model="totalProgress"
        color="black"
        height="25"
    >
        <template v-slot:default="{ value }">
            <strong>{{ value }}%</strong>
        </template>
    </v-progress-linear>
</v-card>
</template>

<script>
import ExpenseLimitService from '@/services/ExpenseLimitService';
export default {
    name: 'limit-progress',
    data: () => ({
        medicalProgress: 0,
        homeProgress: 0,
        automotiveProgress: 0,
        groceryProgress: 0,
        takeoutProgress: 0,
        entertainmentProgress: 0,
        totalProgress: 0,
        limits: []
    }),
    created() {
        this.getLimits()
    },
    methods: {
        getLimits() {
            ExpenseLimitService.getExpenseLimits().then((response) => {
            if(response.status == 200) {
                this.limits = response.data
            }
        })
        },
        setProgress() {
            
        }
    }
}
</script>

<style>

</style>