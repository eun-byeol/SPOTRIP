<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { listArticle } from "@/api/board";

import VSelect from "@/components/common/VSelect.vue";
import BoardListItem from "@/components/board/item/BoardListItem.vue";
import PageNavigation from "@/components/common/PageNavigation.vue";

// const router = useRouter();

const selectSido = ref([
  { text: "시도 선택", value: "" },
  { text: "전체", value: "all" },
  { text: "서울특별시", value: "1100000000" },
  { text: "인천광역시", value: "2800000000" },
  { text: "울산광역시", value: "3100000000" },
  { text: "대구광역시", value: "2700000000" },
  { text: "대전광역시", value: "3000000000" },
  { text: "광주광역시", value: "2900000000" },
  { text: "부산광역시", value: "2600000000" },
  { text: "세종특별자치시", value: "3611000000" },
  { text: "경기도", value: "4100000000" },
  { text: "강원도", value: "4200000000" },
  { text: "충청북도", value: "4300000000" },
  { text: "충청남도", value: "4400000000" },
  { text: "경상북도", value: "4700000000" },
  { text: "경상남도", value: "4800000000" },
  { text: "전라북도", value: "4500000000" },
  { text: "전라남도", value: "4600000000" },
  { text: "제주도", value: "5000000000" },
]);

const selectContentSubType = ref([ // contentSubType
  { text: "스포츠 유형", value: "" },
  { text: "전체", value: "all" },
  { text: "체력단련장", value: "체력단련장" },
  { text: "태권도", value: "태권도" },
  { text: "당구장", value: "당구장" },
  { text: "유도", value: "유도" },
  { text: "검도", value: "검도" },
  { text: "실내", value: "실내" },
  { text: "무도학원", value: "무도학원" },
  { text: "우슈", value: "우슈" },
  { text: "권투", value: "권투" },
  { text: "실외", value: "실외" },
  { text: "골프", value: "골프" },
  { text: "스크린", value: "스크린" },
  { text: "권투", value: "권투" },
  { text: "야구장", value: "야구장" },
  { text: "축구", value: "축구" },
  { text: "농구", value: "농구" },
  { text: "수영", value: "수영" }
]);

const articles = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const param = ref({
  pgno: currentPage.value,
  spp: VITE_ARTICLE_LIST_SIZE,
  sidoCode: "",
  category: "",
});

onMounted(() => {
  getArticleList();
});

const changeSido = (val) => {
  console.log("BoarList에서 선택한 조건 : " + val);
  param.value.sidoCode = val;
};

const changeContentSubType = (val) => {
  console.log("BoarList에서 선택한 조건 : " + val);
  param.value.category = val;
};

const getArticleList = () => {
  console.log("서버에서 글목록 얻어오자!!!", param.value);
  // API 호출
  listArticle(param.value, ({ data }) => {
    console.log(data)
    articles.value = data.articles;
    currentPage.value = data.currentPage;
    totalPage.value = data.totalPageCount;
  },
    (error) => {
      console.log(error)
    });
};

const onPageChange = (val) => {
  console.log(val + "번 페이지로 이동 준비 끝!!!");
  currentPage.value = val;
  param.value.pgno = val;
  getArticleList();
};

</script>

<template>
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-title text-center">동료 모집 게시글</h2>
      </div>
      <div class="col-lg-10">
        <div class="row align-self-center mb-2">
          <div class="col-md-12">
            <form class="d-flex justify-content-around">
              <VSelect :selectOption="selectSido" @onKeySelect="changeSido" />
              <VSelect :selectOption="selectContentSubType" @onKeySelect="changeContentSubType" />
              <div class="input-group" style="flex: left;">
                <button class="btn btn-dark" type="button" @click="getArticleList">검색</button>
              </div>
            </form>
          </div>
        </div>
        <table class="table table-hover">
          <thead>
            <tr class="text-center">
              <th scope="col">#</th>
              <th scope="col">제목</th>
              <th scope="col">종목</th>
              <th scope="col">출발일</th>
              <th scope="col">도착일</th>
              <th scope="col">작성자</th>
            </tr>
          </thead>
          <tbody>
            <BoardListItem v-for="(article, index) in articles" :key="article.id" :article="article" :index="index">
            </BoardListItem>
          </tbody>
        </table>
      </div>
      <PageNavigation :current-page="currentPage" :total-page="totalPage" @pageChange="onPageChange"></PageNavigation>
    </div>
  </div>
</template>

<style scoped></style>