<template>
  <v-container>
    <expense-table />
    <expense-limit-progress-vue />
    <expense-limit-table-vue />
    <expense-limit-form-vue />
  </v-container>
</template>

<script>
import ExpenseTable from '@/components/ExpenseTable.vue';
import ExpenseLimitFormVue from '@/components/ExpenseLimitForm.vue';
import ExpenseLimitTableVue from '@/components/ExpenseLimitTable.vue';
import ExpenseLimitProgressVue from '@/components/ExpenseLimitProgress.vue';
export default {
  name: "expenses-page",
  components: {
    ExpenseTable,
    ExpenseLimitFormVue,
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