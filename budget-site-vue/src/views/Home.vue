<template>
  <div id="home-main">
    <v-container>
      <planet-chart-vue />
      <sparkline-chart-vue />
    </v-container>
  </div>
</template>

<script>
import SparklineChartVue from "@/components/SparklineChart.vue";
import PlanetChartVue from "@/components/PlanetChart.vue";
import ExpenseService from "@/services/ExpenseService";
import ExpenseLimitService from "@/services/ExpenseLimitService";
import ExpenseTypeService from "@/services/ExpenseTypeService";

export default {
  name: "home-page",
  components: {
    PlanetChartVue,
    SparklineChartVue
  },
  methods: {
    getExpensesOnLogin() {
      ExpenseService.getExpensesByUser().then((response) => {
        if(response.status == 200) {
          this.$store.commit("SET_EXPENSES", response.data)
        }
      })
    },
    setExpenseTypes () {
        ExpenseTypeService.getExpenseTypes().then(response => {
          if(response.status == 200) {
            this.$store.commit("SET_EXPENSE_TYPES", response.data)
          }
        })
      },
      setExpenseLimitsForUser() {
            ExpenseLimitService.getExpenseLimits().then((response) => {
                if(response.status == 200) {
                    this.$store.commit('SET_LIMITS', response.data)
                }
            })
        },
  },
  created() {
    this.getExpensesOnLogin()
    this.setExpenseTypes()
    this.setExpenseLimitsForUser()
  }
};
</script>

<style scoped>
div#home-main {
  height: 100%;
}
/* div.container {
  height: 100%;
} */
</style>