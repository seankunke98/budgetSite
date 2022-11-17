<template>
  <v-container class="chart-wrapper">
    <apex-chart
      ref="pie"
      type="pie"
      v-if="series && series.length"
      :options="chartOptions"
      :series="series"
      width="600"
    ></apex-chart>
  </v-container>
</template>

<script>
import ExpenseService from "@/services/ExpenseService";

export default {
  name: "DonutExample",
  data: () => ({
      loading: true,
      series: [],
    chartOptions: {
        labels: [],
        noData: "No Expenses",
        plotOptions: {
          pie: {
            startAngle: 0,
            endAngle: 360,
            expandOnClick: true,
          },
        },
        chart: {
          animations: {
            enabled: true,
            easing: "easeinout",
            speed: 500,
            dynamicAnimation: {
              enabled: true,
              speed: 350,
            },
          },
        },
        legend: {
          show: true,
          showForSingleSeries: false,
          showForNullSeries: true,
          showForZeroSeries: true,
          position: "bottom",
          horizontalAlign: "center",
          fontSize: "18px",
          onItemClick: {
            toggleDataSeries: true,
          },
          onItemHover: {
            highlightDataSeries: true,
          },
        },
        responsive: [{
          breakpoint: 1000,
          options: {
            height: 200,
            width: 200,
            yaxis: 200
          }
        }]
    },
  }),
  mounted() {
    this.getTotalsByType();
  },

  methods: {
    async getTotalsByType() {
      ExpenseService.sumOfExpensesEachType().then((response) => {
        if (response.status == 200) {
          this.$store.commit("SET_EXPENSE_TYPE_TOTALS", response.data);
        }
        for (let i = 0; i < this.$store.state.totalExpensesByType.length; i++) {
        this.series.push(this.$store.state.totalExpensesByType[i].totalExpenses);
      }
      for (let i = 0; i < this.$store.state.totalExpensesByType.length; i++) {
        this.chartOptions.labels.push(this.$store.state.totalExpensesByType[i].typeName);
      }
        this.loading = false;
      });
    },
  },
  computed: {
  //   series() {
  //     let totals = [];
  //     for (let i = 0; i < this.$store.state.totalExpensesByType.length; i++) {
  //       totals.push(this.$store.state.totalExpensesByType[i].totalExpenses);
  //     }
  //     return totals;
  //   },
  //   chartOptions: function () {
  //     let names = [];
  //     for (let i = 0; i < this.$store.state.totalExpensesByType.length; i++) {
  //       names.push(this.$store.state.totalExpensesByType[i].typeName);
  //     }
  //     return {
  //       labels: names,
  //       noData: "No Expenses",
  //       plotOptions: {
  //         pie: {
  //           startAngle: 0,
  //           endAngle: 360,
  //           expandOnClick: true,
  //         },
  //       },
  //       chart: {
  //         animations: {
  //           enabled: true,
  //           easing: "easeinout",
  //           speed: 500,
  //           dynamicAnimation: {
  //             enabled: true,
  //             speed: 350,
  //           },
  //         },
  //       },
  //       legend: {
  //         show: true,
  //         showForSingleSeries: false,
  //         showForNullSeries: true,
  //         showForZeroSeries: true,
  //         position: "bottom",
  //         horizontalAlign: "center",
  //         fontSize: "18px",
  //         onItemClick: {
  //           toggleDataSeries: true,
  //         },
  //         onItemHover: {
  //           highlightDataSeries: true,
  //         },
  //       },
  //       responsive: [{
  //         breakpoint: 1000,
  //         options: {
  //           height: 200,
  //           width: 200,
  //           yaxis: 200
  //         }
  //       }]
  //     };
  //   },
  },
  created() {},
};
</script>

<style scoped>
div.chart-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  max-height: 50%;
  max-width: 100%;
}

</style>