<template>
  <div id="home-main">
    <v-container fluid>
      <planet-chart-vue />
      <sparkline-chart-vue />
        </v-container>
  </div>
</template>

<script>
import SparklineChartVue from "@/components/SparklineChart.vue";
import PlanetChartVue from "@/components/PlanetChart.vue";
import ExpenseService from "@/services/ExpenseService";

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
        ExpenseService.getExpenseTypes().then(response => {
          if(response.status == 200) {
            this.$store.commit("SET_EXPENSE_TYPES", response.data)
          }
        })
      },
  },
  created() {
    this.getExpensesOnLogin()
    this.setExpenseTypes()
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