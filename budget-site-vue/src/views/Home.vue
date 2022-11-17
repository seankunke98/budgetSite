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

export default {
  name: "home-page",
  components: {
    PlanetChartVue,
    SparklineChartVue
  },
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