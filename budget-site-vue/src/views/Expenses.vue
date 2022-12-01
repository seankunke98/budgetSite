<template>
  <v-container>
    <expense-table />
    <v-card class="d-flex flex-row-reverse justify-space-between">
      <v-row no-gutters style="flex-wrap: nowrap;">
        <v-col>
    <expense-limit-progress-vue />
  </v-col>
  </v-row>
  <v-row no-gutters style="flex-wrap: nowrap;">
    <v-col>
    <expense-limit-table-vue />
  </v-col>
  </v-row>
  </v-card>
  </v-container>
</template>

<script>
import ExpenseTable from '@/components/ExpenseTable.vue';
import ExpenseLimitTableVue from '@/components/ExpenseLimitTable.vue';
import ExpenseLimitProgressVue from '@/components/ExpenseLimitProgress.vue';
export default {
  name: "expenses-page",
  components: {
    ExpenseTable,
    ExpenseLimitTableVue,
    ExpenseLimitProgressVue
  }
}
</script>


<style scoped>


div#expenses-main {
  background-image: linear-gradient(to right, #2c3e50, #4ca1af);
  height: 100%;
}

/* @media only screen and (max-width: 600px) {
  div#expenses-main {
    display: flex;
    background-image: linear-gradient(to right, #2c3e50, #4ca1af);
    width: fit-content;
    margin: 0 auto;
  }
  div#table-container {
    display: flex;
    flex-direction: row;
    width: fit-content;
    height: 50vh;
    justify-content: center;
    align-content: center;
    align-items: center;
  }
}
@media only screen and (min-width: 600px) {
  div#expenses-main {
    display: flex;
    background-image: linear-gradient(to right, #2c3e50, #4ca1af);
    width: fit-content;
    margin: 0 auto;
  }
  div#table-container {
    display: flex;
    flex-direction: row;
    width: fit-content;
    height: 50vh;
    justify-content: center;
    align-content: center;
    align-items: center;
  }
}

@media only screen and (min-width: 768px) {
  div#expenses-main {
    display: flex;
    background-image: linear-gradient(to right, #2c3e50, #4ca1af);
    width: fit-content;
    margin: 0 auto;
  }
  div#table-container {
    display: flex;
    flex-direction: row;
    width: fit-content;
    height: 50vh;
    justify-content: center;
    align-content: center;
    align-items: center;
  }
}

@media only screen and (min-width: 992px) {
}

@media only screen and (min-width: 1200px) {
} */
</style>