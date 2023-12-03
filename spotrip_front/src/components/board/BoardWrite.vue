<script setup>
import { ref } from 'vue';
import BoardFormItem from "./item/BoardFormItem.vue";
import AttractionMap from "@/components/board/item/AttractionMap.vue";

const selecedAttractionPlan = ref([]);

const isShowBoardForm = ref(false);

const showBoardForm = (plans) => {
  console.log("전달된 여행지 목록 데이터", plans);
  selecedAttractionPlan.value = plans;
  isShowBoardForm.value = true;
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-title text-center">
          동료 모집 글쓰기
        </h2>
      </div>
      <!-- 여행지 계획 세우기 -->
      <div v-if="!isShowBoardForm">
        <AttractionMap @selectPlanList="showBoardForm" type="regist" />
      </div>
      <!-- 상세 게시글 폼 -->
      <div v-if="isShowBoardForm" class="col-lg-10 text-start">
        <BoardFormItem :selecedAttractionPlan="selecedAttractionPlan" v-model="selecedAttractionPlan" type="regist" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
