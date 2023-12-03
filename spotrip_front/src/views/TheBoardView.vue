<script setup></script>

<template>
  <div class="container text-center mt-3">
    <img src="@/assets/grass-lawn-soccer-football-stadium-player-618649-pxhere.com.jpg" alt="main 화면 이미지"
      class="main-image">
    <router-view></router-view>
  </div>
</template>

<style>
.main-image {
  width: 100%;
  height: auto;
}

.bg-title {
  color: black;
}
</style>

<style scoped>
.container {
  position: relative;
  text-align: center;
  color: white;
}
</style>
