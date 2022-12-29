<template>
    <v-container>
    <v-card class="mb-10" width="1000" elevation="5">
        <v-row no-gutters justify="center">
        <v-card elevation="5" :color="(setColor)" width="800" height="100" class="mt-2">  
            <v-col cols="12" class="mt-n4">  
            <v-card-title class="text-subtitle-1" >Total Percentage Spent This Month - Medical:
                <v-spacer></v-spacer>
                <!-- <v-dialog v-model="dialog" width="300">
                    <template v-slot:activator="{ on, attrs }">
                <v-btn
                v-if="medicalProgress.totalExpenses < 50"
                v-bind="attrs"
                v-on="on"
                >Choose bar color</v-btn>
            </template>
            <v-card><v-color-picker show-swatches hide-canvas hide-sliders></v-color-picker></v-card>
            </v-dialog> -->
            </v-card-title>
        </v-col>
        <v-col>
    <v-progress-linear
        :value="(medicalProgress.totalExpenses == 0)?defaultProgress:Math.floor(100 * (medicalProgress.totalExpenses / medicalProgress.expenseLimit))"
        color="indigo accent-1"
        height="25"
        class="mt-n6"
        rounded
    >
        <template v-slot:default="{ value }">
            <strong>{{ value }}%</strong>
        </template>

    </v-progress-linear>
</v-col>
</v-card>
</v-row>
    <br>
    <v-row no-gutters justify="center">
    <v-card elevation="5" :color="(setColor)" width="800" height="100">
        <v-col cols="12" class="mt-n4">
        <v-card-title class="text-subtitle-1">Total Percentage Spent This Month - Home:
            <v-spacer></v-spacer>
            <!-- <v-dialog v-model="dialog" width="300">
                    <template v-slot:activator="{ on, attrs }">
                <v-btn
                v-if="homeProgress.totalExpenses < 50"
                v-bind="attrs"
                v-on="on"
                >Choose bar color</v-btn>
            </template>
            <v-card><v-color-picker show-swatches hide-canvas hide-sliders></v-color-picker></v-card>
            </v-dialog> -->
        </v-card-title>
    </v-col>
    <v-col>
    <v-progress-linear
        :value="(homeProgress.totalExpenses == 0)?defaultProgress:Math.floor(100 * (homeProgress.totalExpenses / homeProgress.expenseLimit))"
        color="green darken-3"
        height="25"
        class="mt-n6"
        rounded
    >
        <template v-slot:default="{ value }">
            <strong>{{ value }}%</strong>
        </template>
    </v-progress-linear>
</v-col>
</v-card>
</v-row>
    <br>
    <v-row no-gutters justify="center">
    <v-card elevation="5" :color="(setColor)" width="800" height="100">
        <v-col cols="12" class="mt-n4"> 
        <v-card-title class="text-subtitle-1">Total Percentage Spent This Month - Automotive:
            <v-spacer></v-spacer>
            <!-- <v-dialog v-model="dialog" width="300">
                    <template v-slot:activator="{ on, attrs }">
                <v-btn
                v-if="automotiveProgress.totalExpenses < 50"
                v-bind="attrs"
                v-on="on"
                >Choose bar color</v-btn>
            </template>
            <v-card><v-color-picker show-swatches hide-canvas hide-sliders></v-color-picker></v-card>
            </v-dialog> -->
        </v-card-title>
    </v-col>
    <v-col>
    <v-progress-linear
        :value="(automotiveProgress.totalExpenses == 0)?defaultProgress:Math.floor(100 * (automotiveProgress.totalExpenses / automotiveProgress.expenseLimit))"
        color="blue"
        height="25"
        class="mt-n6"
        rounded
    >
        <template v-slot:default="{ value }">
            <strong>{{ value }}%</strong>
        </template>
    </v-progress-linear>
</v-col>
</v-card>
</v-row>
    <br>
    <v-row no-gutters justify="center">
    <v-card elevation="5" :color="(setColor)" width="800" height="100">
        <v-col cols="12" class="mt-n4"> 
        <v-card-title class="text-subtitle-1">Total Percentage Spent This Month - Grocery:
            <v-spacer></v-spacer>
            <!-- <v-dialog v-model="dialog" width="300">
                    <template v-slot:activator="{ on, attrs }">
                <v-btn
                v-if="groceryProgress.totalExpenses < 50"
                v-bind="attrs"
                v-on="on"
                >Choose bar color</v-btn>
            </template>
            <v-card><v-color-picker show-swatches hide-canvas hide-sliders></v-color-picker></v-card>
            </v-dialog> -->
        </v-card-title>
    </v-col>
    <v-col>
    <v-progress-linear
        :value="(groceryProgress.totalExpenses == 0)?defaultProgress:Math.floor(100 * (groceryProgress.totalExpenses / groceryProgress.expenseLimit))"
        color="purple"
        height="25"
        class="mt-n6"
        rounded
    >
        <template v-slot:default="{ value }">
            <strong>{{ value }}%</strong>
        </template>
    </v-progress-linear>
</v-col>
</v-card>
</v-row>
    <br>
    <v-row no-gutters justify="center">
    <v-card elevation="5" :color="(setColor)" width="800" height="100">
        <v-col cols="12" class="mt-n4"> 
        <v-card-title class="text-subtitle-1">Total Percentage Spent This Month - Takeout:
            <v-spacer></v-spacer>
            <!-- <v-dialog v-model="dialog" width="300">
                    <template v-slot:activator="{ on, attrs }">
                <v-btn
                v-if="takeoutProgress.totalExpenses < 50"
                v-bind="attrs"
                v-on="on"
                >Choose bar color</v-btn>
            </template>
            <v-card><v-color-picker show-swatches hide-canvas hide-sliders></v-color-picker></v-card>
            </v-dialog> -->
        </v-card-title>
    </v-col>
    <v-col>
    <v-progress-linear
        :value="(takeoutProgress.totalExpenses == 0)?defaultProgress:Math.floor(100 * (takeoutProgress.totalExpenses / takeoutProgress.expenseLimit))"
        color="orange"
        height="25"
        class="mt-n6"
        rounded
    >
    <template v-slot:default="{ value }">
            <strong>{{ value }}%</strong>
        </template>
    </v-progress-linear>
</v-col>
</v-card>
</v-row>
    <br>
    <v-row no-gutters justify="center">
    <v-card elevation="5" :color="(setColor)" width="800" height="100">
        <v-col cols="12" class="mt-n4"> 
        <v-card-title class="text-subtitle-1">Total Percentage Spent This Month - Entertainment:
            <v-spacer></v-spacer>
            <!-- <v-dialog v-model="dialog" width="300">
                    <template v-slot:activator="{ on, attrs }">
                <v-btn
                v-if="entertainmentProgress.totalExpenses < 50"
                v-bind="attrs"
                v-on="on"
                >Choose bar color</v-btn>
            </template>
            <v-card><v-color-picker show-swatches hide-canvas hide-sliders></v-color-picker></v-card>
            </v-dialog> -->
        </v-card-title>
    </v-col>
    <v-col>
    <v-progress-linear
        :value="(entertainmentProgress.totalExpenses == 0)?defaultProgress:Math.floor(100 * (entertainmentProgress.totalExpenses / entertainmentProgress.expenseLimit))"
        color="yellow"
        height="25"
        class="mt-n6"
        rounded
    >
        <template v-slot:default="{ value }">
            <strong>{{ value }}%</strong>
        </template>
    </v-progress-linear>
</v-col>
</v-card>
</v-row>
    <br>
    <v-row no-gutters justify="center">
    <v-card elevation="5" :color="(setColor)" width="800" height="100" class="mb-4">
        <v-col cols="12" class="mt-n4">
        <v-card-title class="text-subtitle-1">Total Percentage Spent This Month - Overall:
            <v-spacer></v-spacer>
            <!-- <v-dialog v-model="dialog" width="300">
                    <template v-slot:activator="{ on, attrs }">
                <v-btn
                v-if="totalProgress.totalExpenses < 50"
                v-bind="attrs"
                v-on="on"
                >Choose bar color</v-btn>
            </template>
            <v-card><v-color-picker show-swatches hide-canvas hide-sliders></v-color-picker></v-card>
            </v-dialog> -->
        </v-card-title>
    </v-col>
    <v-col>
    <v-progress-linear
        :value="(totalProgress.totalExpenses == 0)?defaultProgress:Math.floor(100 * (totalProgress.totalExpenses / totalProgress.expenseLimit))"
        color="black"
        height="25"
        class="mt-n6"
        rounded
    >
        <template v-slot:default="{ value }">
            <strong>{{ value }}%</strong>
        </template>
    </v-progress-linear>
</v-col>
</v-card>
</v-row>
<v-divider></v-divider>
<v-card-actions>
        <v-btn color="primary" @click="getLimits"> Refresh </v-btn>
    </v-card-actions>
</v-card>
</v-container>
</template>

<script>
import ExpenseLimitService from '@/services/ExpenseLimitService';
export default {
    name: 'limit-progress',
    data: () => ({
        medicalProgress: {
            typeName: '',
            typeId: 0,
            expenseLimit: 0,
            totalExpenses: 0
        },
        homeProgress: {
            typeName: '',
            typeId: 0,
            expenseLimit: 0,
            totalExpenses: 0
        },
        automotiveProgress: {
            typeName: '',
            typeId: 0,
            expenseLimit: 0,
            totalExpenses: 0
        },
        groceryProgress: {
            typeName: '',
            typeId: 0,
            expenseLimit: 0,
            totalExpenses: 0
        },
        takeoutProgress: {
            typeName: '',
            typeId: 0,
            expenseLimit: 0,
            totalExpenses: 0
        },
        entertainmentProgress: {
            typeName: '',
            typeId: 0,
            expenseLimit: 0,
            totalExpenses: 0
        },
        totalProgress: {
            typeName: '',
            typeId: 0,
            expenseLimit: 0,
            totalExpenses: 0
        },
        defaultProgress: 0,
        dialog: false,
        limits: [],
    }),
    computed: {
        formTitle() {
            return this.editedIndex === -1 ? "Set New Limit" : "Edit Current Item";
        },
        setColor() {
            let color = '';
            this.limits.forEach(limit => {
                if(limit.totalExpenses >= 49) {
                    color = "green lighten-1";
            }
                else if(limit.totalExpenses >= 50 && limit.totalExpenses <= 79) {
                    color = "orange lighten-1";
            }   else if(limit.totalExpenses >= 80) {
                    color = "red darken-1";
            }
            });
            return color;
        },
    },
    created() {
        this.getLimits()
    },
    methods: {
        getLimits() {
            ExpenseLimitService.getExpenseLimitProgress().then((response) => {
            if(response.status == 200) {
                console.log(response.data)
                this.limits = response.data
            }
            for(let i = 0; i < this.limits.length; i++) {
                if(this.limits[i].typeName == "Medical") {
                    this.medicalProgress = this.limits[i]
                } 
                if(this.limits[i].typeName == 'Home') {
                    this.homeProgress = this.limits[i]
                } 
                if(this.limits[i].typeName == 'Automotive') {
                    this.automotiveProgress = this.limits[i]
                } 
                if(this.limits[i].typeName == 'Grocery') {
                    this.groceryProgress = this.limits[i]
                } 
                if(this.limits[i].typeName == 'Takeout') {
                    this.takeoutProgress = this.limits[i]
                } 
                if(this.limits[i].typeName == 'Entertainment') {
                    this.entertainmentProgress = this.limits[i]
                } 
                if (this.limits[i].typeName == 'Total Expenses') {
                    this.totalProgress = this.limits[i]
                }
            }
        })
        },
        setColors(limit) {
            let color = ''
                if(limit.totalExpenses <= 50 && limit.totalExpenses <= 79) {
                color = "orange lighten-1";
            } else if(limit.totalExpenses >= 80) {
                color = "red darken-1";
            } else {
                color = "green lighten-1";
            }
            return color
        },
    },
}
</script>

<style>
.medical {
    background-color: floralwhite;
}

</style>