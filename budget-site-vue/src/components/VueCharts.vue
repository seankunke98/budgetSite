<template>
  <div class="container">
    <Bar v-if="loaded" :chart-data="chartData" />
  </div>
</template>
  
  <script>
import { Bar } from "vue-chartjs";
// import ExpenseService from "@/services/ExpenseService";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
} from "chart.js";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
);

export default {
  name: "BarChart",
  components: { Bar },
  data: () => ({
    loaded: false,
    chartData: {
        labels: [ '' ],
        datasets: [
            {
                data: []
            }
        ]
    }
  }),
  async mounted() {
    this.loaded = false;

    try {
      const { expenses } = this.$store.state.totalExpensesByType
      this.chartData.datasets.data = expenses;

      this.loaded = true;
    } catch (e) {
      console.error(e);
    }
  },
};
</script>