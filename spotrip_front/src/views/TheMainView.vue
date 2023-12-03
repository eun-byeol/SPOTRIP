<script setup>
import { MDBBtn } from 'mdb-vue-ui-kit';
import { ref } from 'vue';
import { useRouter } from "vue-router";

const router = useRouter();

const moveBoard = () => {
  router.push({ name: "board" });
};
</script>

<template>
  <div class="p-5 text-center bg-image" style="background-image: url('/src/assets/main_image.jpg'); height: 1200px;">
    <div class="mask" style="background-color: rgba(0, 0, 0, 0.6);">
      <div class="d-flex justify-content-center align-items-center h-100">
        <div class="text-white">
          <h1 class="mb-3">너 내 동료가 돼라</h1>
          <h4 class="mb-3">우리 모두 스포츠 여행 떠나 볼까요?</h4>
          <MDBBtn size="lg" style="margin-top:20px" outline="light" @click="moveBoard">동료 찾으러 가기</MDBBtn>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>