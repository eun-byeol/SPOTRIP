<script setup>
import { registArticle, modifyArticle, getModifyArticle } from "@/api/board";
import { ref, watch, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const route = useRoute();
const props = defineProps({ type: String, selecedAttractionPlan: Array });
const isUseId = ref(false);
const todayDate = new Date().toISOString().substring(0, 10);

const article = ref({
  memberId: "",
  title: "",
  content: "",
  recruits: 0,
  category: "",
  image: "",
  departureDate: todayDate,
  arrivalDate: todayDate,
  sidoCode: ""
});

const boardCreateInfo = ref({
  board: null,
  attractionIds: []
});

const subjectErrMsg = ref("");
const contentErrMsg = ref("");

// 수정폼인 경우 -> 기존 데이터 불러오기
onMounted(() => {
  console.log(">>>>>>재업로드>>>>>>>");
  if (props.type === 'modify') {
    getArticle();
  }
});


watch(
  () => article.value.title,
  (value) => {
    let len = article.value.length;
    if (len == 0 || len > 30) {
      subjectErrMsg.value = "제목을 확인해 주세요!!!";
    } else subjectErrMsg.value = "";
  },
  { immediate: true }
);

watch(
  () => article.value.content,
  (value) => {
    let len = article.value.length;
    if (len == 0 || len > 500) {
      contentErrMsg.value = "내용을 확인해 주세요!!!";
    } else contentErrMsg.value = "";
  },
  { immediate: true }
);

// 글 작성하기 - type이 regist이면 writeArticle, 아니면 updateArticle
function onSubmit() {
  // event.preventDefault();
  if (subjectErrMsg.value) {
    alert(subjectErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.type === "regist" ? writeArticle() : updateArticle();
  }
}
// const { articleid } = route.params;
function getArticle() {
  console.log("글 수정을 위한 글 조회 시도!!!");
  console.log("라우트에서 받아온 articleno: ", route.params.articleid);
  getModifyArticle(route.params.articleid,
    ({ data }) => {
      console.log(data);
      article.value.memberId = data.board.memberId;
      article.value.title = data.board.title;
      article.value.content = data.board.content;
      article.value.recruits = data.board.recruits;
      article.value.category = data.board.category;
      article.value.image = data.board.image;
      article.value.departureDate = data.board.departureDate;
      article.value.arrivalDate = data.board.arrivalDate;
      article.value.sidoCode = data.board.sidoCode;
    },
    (error) => {
      console.log(error)
    });
}

function writeArticle() {
  console.log("글 등록 시도!!!");
  boardCreateInfo.value.board = article.value;

  const list = [];
  console.log("> 받아온 여행지 목록", props.selecedAttractionPlan);

  for (let i = 0; i < props.selecedAttractionPlan.length; i++) {
    list.push(props.selecedAttractionPlan[i].id);
  }
  boardCreateInfo.value.attractionIds = list;

  console.log("글등록하자!!", boardCreateInfo.value);
  registArticle(boardCreateInfo.value,
    (response) => {
      console.log(response)
      router.push({ name: "article-list" });
    },
    (error) => {
      console.log(error)
    });
  // API 호출
};

function updateArticle() {
  console.log("글 수정 시도!!!");
  boardCreateInfo.value.board = article.value;

  const list = [];
  console.log("> 받아온 여행지 목록", props.selecedAttractionPlan);

  for (let i = 0; i < props.selecedAttractionPlan.length; i++) {
    list.push(props.selecedAttractionPlan[i].id);
  }
  boardCreateInfo.value.attractionIds = list;

  console.log("글수정하자!!", boardCreateInfo.value);
  modifyArticle(boardCreateInfo.value,
    (response) => {
      console.log(response)
      router.push({ name: "article-list" });
    },
    (error) => {
      console.log(error)
    });
  // API 호출
};

function moveList() {
  router.push({ name: "article-list" });
}

</script>

<template>
  <form @submit.prevent="onSubmit">
    <div class="mb-3">
      <label for="memberId" class="form-label">작성자 ID : </label>
      <input type="text" name="memberId" class="form-control" v-model="article.memberId" :disabled="isUseId"
        placeholder="작성자ID..." />
    </div>
    <div class="mb-3">
      <label for="title" class="form-label">제목 : </label>
      <input type="text" name="title" class="form-control" v-model="article.title" placeholder="제목..." />
    </div>
    <div class="mb-3">
      <label for="departureDate" class="form-label">출발 날짜 : </label>
      <input type="date" name="departureDate" :min="todayDate" class="form-control" v-model="article.departureDate" />
    </div>
    <div class="mb-3">
      <label for="arrivalDate" class="form-label">도착 날짜 : </label>
      <input type="date" name="arrivalDate" :min="todayDate" class="form-control" v-model="article.arrivalDate" />
    </div>
    <div class="mb-3">
      <label for="recruits" class="form-label">모집 인원 : </label>
      <input type="number" name="recruits" min="0" class="form-control" v-model="article.recruits"
        placeholder="모집 인원..." />
    </div>
    <div class="mb-3">
      <label for="content" class="form-label">상세 내용 : </label>
      <textarea class="form-control" name="content" v-model="article.content" rows="20"
        placeholder="모집 대상 및 상세 내용..."></textarea>
    </div>
    <div class="col-auto text-center">
      <button type="submit" class="btn btn-outline-primary mb-3" v-if="type === 'regist'">글작성</button>
      <button type="submit" class="btn btn-outline-success mb-3" v-else>글수정</button>
      <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="moveList">
        목록으로이동...
      </button>
    </div>
  </form>
</template>

<style scoped></style>