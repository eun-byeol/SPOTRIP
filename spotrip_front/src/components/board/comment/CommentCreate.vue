<script setup>
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { defineProps } from 'vue';
import { registComment } from "@/api/board";

const route = useRoute();
const router = useRouter();

const props = defineProps({
  comment: Object
})

const com = ref({
  articleNo: props.comment.articleNo,
  content: "", // content 프로퍼티 초기화
  userId: props.comment.userId,
  registerTime: "",
});


function writeComment() {
  console.log(props.comment.articleNo);
  console.log("댓글 등록하자!!", props.comment.userId);
  // API 호출
  registComment(props.comment,
    () => {
      alert("등록완료")
      router.push({ name: "article-view", params: { articleno: props.comment.articleNo } });
      location.reload();
    },
    (error) => {
      console.log(error)
    }
  );
}
</script>

<template>
  <div>
    <ul class="comment_box">
      <li class="comment-form">
        <form id="commentFrm" @submit.prevent="writeComment">
          <h4 class="comment-text">참여 신청하기<span></span></h4>
          <span class="ps_box">
            <input placeholder="내용을 입력해주세요." class="int" v-model="comment.content" />
          </span>
          <input type="submit" class="comment_btn" value="등록" />
        </form>
      </li>
      <li id="comment-list"></li>
    </ul>
  </div>
</template>

<style scoped>
ul,
li {
  list-style: none;
}

.comment_box {
  display: flex;
  flex-direction: column;
  flex-wrap: nowrap;
  padding: 30px;
  width: 600px;
  margin: 0 auto;
}

.comment-text {
  text-align: left;
}

.comment>li {
  margin-top: 20px;
}

.comment>li:nth-child(1) {
  margin: 0px;
}

.comment-row {
  display: flex;
  justify-content: space-between;
  flex-direction: row;
}

.comment-row {
  margin-top: 20px;
  width: 100%;
}

.comment-row>li:nth-child(2) {
  flex-shrink: 0;
  flex-grow: 1;
  padding-left: 25px;
  z-index: 1;
  width: 100%;
}

.comment-row>li:nth-child(2) {
  width: 85px;
}

.comment-form>form {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-between;
}

.comment-form>form>h4 {
  width: 100%;
  margin: 14px 0 14px 0;
}

.comment-content {
  cursor: pointer;
  word-break: break-all;
  padding-right: 25px;
}

.ps_box {
  display: block;
  position: relative;
  width: 80%;
  height: 51px;
  border: solid 1px #dadada;
  padding: 10px 14px 10px 14px;
  background: #fff;
  box-sizing: border-box;
}

.ps_box>input {
  outline: none;
}

.int {
  display: block;
  position: relative;
  width: 100%;
  height: 29px;
  padding-right: 25px;
  line-height: 29px;
  border: none;
  background: #fff;
  font-size: 15px;
  box-sizing: border-box;
  z-index: 10;
}

.comment_btn {
  width: 18%;
  padding: 18px 0 16px;
  text-align: center;
  box-sizing: border-box;
  text-decoration: none;
  border: none;
  background: #333;
  color: #fff;
  font-size: 14px;
}

.comment-delete-btn {
  display: inline-block;
  margin-left: 7px;
  cursor: pointer;
}

.comment-update-input {
  border: none;
  border-bottom: 1px solid #333;
  font-size: 16px;
  color: #666;
  outline: none;
}
</style>