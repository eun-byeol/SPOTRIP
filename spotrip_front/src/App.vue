<script setup>
import { RouterView } from "vue-router";
import TheHeadingNavbar from "./components/layout/TheHeadingNavbar.vue";
// import TheHeadingNavbar from "@/components/layout/TheHeadingNavbar.vue";
//import NavigationBar from "./components/common/NavigationBar.vue";
</script>

<template>
  <div>
    <!--<NavigationBar></NavigationBar>-->
    <TheHeadingNavbar></TheHeadingNavbar>
    <router-view></router-view>
  </div>
</template>

<style scoped>
#app {
  font-family: Roboto, Helvetica, Arial, sans-serif;
}
</style>
