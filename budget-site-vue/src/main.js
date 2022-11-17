import Vue from 'vue'
import App from './App.vue'
import router from './router/index'
import store from './store/index'
import axios from 'axios'
import vuetify from './plugins/vuetify'
import { Bar, Pie } from 'vue-chartjs'
import VueApexCharts from 'vue-apexcharts'


Vue.component('apex-chart', VueApexCharts)

// register VueFusionCharts component
Vue.use(Bar, Pie, VueApexCharts)


Vue.config.productionTip = false

axios.defaults.baseURL = process.env.VUE_APP_REMOTE_API;



new Vue({
    router,
    store,
    vuetify,
    render: h => h(App),
}).$mount('#app')