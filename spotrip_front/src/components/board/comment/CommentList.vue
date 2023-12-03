<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { defineProps } from 'vue';
import { listComment } from "@/api/board";

import CommentListItem from "../item/CommentListItem.vue";
import PageNavigation from "@/components/common/PageNavigation.vue";

const route = useRoute();
const router = useRouter();

const props = defineProps({
  comment: Object
})

const comments = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;

onMounted(() => {
  console.log("댓글 받아오기");
  getCommentList();
});

const getCommentList = () => {
  console.log("서버에서 댓글목록 얻어오자!!!", props.comment.articleNo);
  listComment(props.comment.articleNo,
    ({ data }) => {
      console.log("댓글 목록 : ", data);
      comments.value = data;
      console.log("저장된 댓글 목록: ", comments.value);
    },
    (error) => {
      console.log(error)
    }
  );
};

</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-11">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">참여 신청하기</mark>
        </h2>
      </div>
      <div class="col-lg-10">
        <table class="table table-hover">
          <thead>
            <tr class="text-center">
              <th scope="col">내용</th>
              <th scope="col">작성자</th>
              <th scope="col">작성일</th>
              <th scope="col">삭제</th>
            </tr>
          </thead>
          <tbody>
            <CommentListItem v-for="comment in comments" :key="comment.commentNo" :comment="comment"></CommentListItem>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<style scoped></style>