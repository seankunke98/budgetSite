<template>
  <v-app id="app">
    
    <v-app-bar elevation="10" app>
      <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>
      <v-toolbar-title class="mx-auto">Personal Budgeter</v-toolbar-title>
      <v-app-bar-nav-icon>
        <v-switch
        prepend-icon="mdi-theme-light-dark"
          v-model="$vuetify.theme.dark"
          inset
          class="pt-5"
        ></v-switch></v-app-bar-nav-icon>
        <v-divider vertical class="mx-8"> </v-divider>
      <v-toolbar-title v-if="$store.state.token != ''" class="pl-3" >Welcome, {{ $store.state.user.username }}!</v-toolbar-title>
    </v-app-bar>
    <v-navigation-drawer
      v-model="drawer"
      app
      temporary
      dark
    >
      <v-list nav>
        <v-list-item v-if="$store.state.token != ''" href="/home" link >
          <v-list-item-icon>
            <v-icon> mdi-view-dashboard </v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>Home</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item v-if="$store.state.token != ''" link v-bind:to="{ name: 'expenses-page' }">
          <v-list-item-icon>
            <v-icon>  $  </v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>Expense Tracker</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item link >
          <v-list-item-icon>
            <v-icon>mdi-information</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>About</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item v-if="$store.state.token != ''" link v-bind:to="{ name: 'logout-page' }">
          <v-list-item-icon>
            <v-icon>mdi-logout</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>Logout</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <v-main>
      <v-container fluid>
    <router-view />
  </v-container>
  </v-main>
  </v-app>
</template>

<script>

export default {
  components: {
    
  },
  data () {
    return {
    drawer: false,
    switch: false,
    darkmode: false
    }
  },
  methods: {
    resetAuth() {
      if (location.pathname == '/') {
        this.$store.commit("LOGOUT")
      }
    }
  },
  created() {
    this.resetAuth()
  }
}
</script>
<style>

div.container.container--fluid {
  height: 100%;
}



</style>
