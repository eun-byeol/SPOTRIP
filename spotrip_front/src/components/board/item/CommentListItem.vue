<script setup>
import { useRoute, useRouter } from "vue-router";
import { defineProps } from 'vue';
import { deleteComment } from "@/api/board";

const route = useRoute();
const router = useRouter();

const props = defineProps({
  comment : Object
})

function onDeleteArticle() {
    console.log(props.comment.commentNo + " 댓글 삭제하러 가자!!!");
    const commentNo = props.comment.commentNo;
  deleteComment(commentNo,
    () => {
      alert("삭제완료")
      router.push({ name: "article-view", params: { articleno: props.comment.articleNo } });
      location.reload();
    },
    (error) => {
      console.log(error)
    }
  )
  
}
</script>

<template>
    <tr class="text-center">
      <td class="text-start">
        {{ comment.content }}
      </td>
      <td>{{ comment.userId }}</td>
      <td>{{ comment.registerTime }}</td>
      <td>
        <button type="button" class="btn btn-outline-danger" @click="onDeleteArticle">
              댓글삭제
        </button>
      </td>
    </tr>
  </template>


<style scoped>
</style>
