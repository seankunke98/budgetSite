import Vue from 'vue'
import App from './App.vue'
import router from './router/index'
import store from './store/index'
import axios from 'axios'
import vuetify from './plugins/vuetify'
import { Bar, Pie } from 'vue-chartjs'
import VueApexCharts from 'vue-apexcharts'
import { ValidationProvider, ValidationObserver, extend } from 'vee-validate'
import * as rules from 'vee-validate/dist/rules';


Object.keys(rules).forEach(rule => {
    extend(rule, rules[rule]);
});

// with typescript
for (let [rule, validation] of Object.entries(rules)) {
    extend(rule, {
    ...validation
    });
}

Vue.component('apex-chart', VueApexCharts, ValidationProvider, ValidationObserver)

// register VueFusionCharts component
Vue.use(Bar, Pie, VueApexCharts)


Vue.config.productionTip = false

axios.defaults.baseURL = process.env.VUE_APP_REMOTE_API;
require('./vee-validate')


new Vue({
    router,
    store,
    vuetify,
    render: h => h(App),
}).$mount('#app')