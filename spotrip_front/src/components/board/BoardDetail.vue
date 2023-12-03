<script setup>
import { deleteArticle, detailArticle } from "@/api/board";
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import VKakaoMap from "@/components/common/VKakaoMap.vue";
// import CommentList from "./comment/CommentList.vue";
// import CommentCreate from "./comment/CommentCreate.vue";

const route = useRoute();
const router = useRouter();

const { articleid } = route.params;

const attractions = ref([]);
const selectAttraction = ref({});

const article = ref({
  id: "",
  memberId: "",
  title: "",
  content: "",
  recruits: 0,
  category: "",
  image: "",
  departureDate: "",
  arrivalDate: "",
  views: 0,
  createdTime: "",
  updatedTime: "",
  sidoCode: "",
});

const comment = ref({
  articleId: articleid,
  userId: "",
  content: "",
})

onMounted(() => {
  getArticle();
});

const getArticle = () => {
  console.log(articleid + "번글 얻으러 가자!!!");
  detailArticle(articleid,
    ({ data }) => {
      // console.log("얻어올 게시글 정보: ", data);
      // 게시글 상세 내용
      article.value.id = data.board.id;
      article.value.memberId = data.board.memberId;
      article.value.title = data.board.title;
      article.value.content = data.board.content;
      article.value.recruits = data.board.recruits;
      article.value.category = data.board.category;
      article.value.image = data.board.image;
      article.value.departureDate = data.board.departureDate;
      article.value.arrivalDate = data.board.arrivalDate;
      article.value.views = data.board.views;
      article.value.createdTime = data.board.createdTime;
      article.value.updatedTime = data.board.updatedTime;
      article.value.sidoCode = data.board.sidoCode;

      // 여행지 정보
      attractions.value = data.attractions;
      // console.log("가져온 게시글 내용", article.value);
      // console.log("가져온 여행지 목록", attractions.value[0].id);
    },
    (error) => {
      console.log(error)
    })
};

function moveList() {
  router.push({ name: "article-list" });
}

function moveModify() {
  router.push({ name: "article-modify", params: { articleid } });
}

function onDeleteArticle() {
  console.log(articleid + "번글 삭제하러 가자!!!");

  deleteArticle(articleid,
    (response) => {
      console.log(response)
      router.push({ name: "article-list" });
    },
    (error) => {
      console.log(error)
    });
}

const viewAttraction = (attraction) => {
  console.log("선택한 여행 목록", attraction);
  selectAttraction.value = attraction;
};

</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">스포츠 여행 계획</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <div class="row my-2">
          <h2 class="text-secondary px-5">{{ article.title }}</h2>
        </div>
        <div class="row">
          <div class="col-md-8">
            <div class="clearfix align-content-center">
              <img class="avatar me-2 float-md-start bg-light p-2"
                src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg" />
              <p>
                <span class="fw-bold">{{ article.memberId }}</span> <br />
                <span class="text-secondary fw-light">
                  {{ article.createdTime }} 조회 : {{ article.views }}
                </span>
              </p>
            </div>
          </div>

          <div>
            <p><span>모집인원:</span> {{ article.recruits }}명</p>
            <p><span>카테고리:</span> {{ article.category }}</p>
            <p><span>출발일시:</span> {{ article.departureDate }}</p>
            <p><span>도착일시:</span> {{ article.arrivalDate }}</p>
          </div>
          <div class="divider mb-3"></div>
          <div class="text-secondary">
            {{ article.content }}
          </div>
          <div class="divider mt-3 mb-3"></div>
          <div v-if="attractions.length != 0">
            <VKakaoMap :attractions="attractions" :selectAttraction="selectAttraction" type="detail" />
            <table class="table table-hover">
              <thead>
                <tr class="text-center">
                  <th scope="col">일정순서</th>
                  <th scope="col">여행지명</th>
                  <th scope="col">구분</th>
                  <th scope="col">상세주소</th>
                  <th scope="col">전화번호</th>
                </tr>
              </thead>
              <tbody>
                <tr class="text-center" v-for="(attraction, index) in attractions" :key="attraction.id"
                  @click="viewAttraction(attraction)">
                  <td>{{ index + 1 }}</td>
                  <td>{{ attraction.title }}</td>
                  <td>{{ attraction.contentSubType }}</td>
                  <td>{{ attraction.addr1 }}</td>
                  <td>{{ attraction.tel }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <!-- <div class="col-md-4 align-self-center text-end">댓글 : {{ article.commentcnt }}</div> -->

      <div class="divider mt-3 mb-3"></div>
      <div class="d-flex justify-content-end">
        <button type="button" class="btn btn-outline-primary mb-3" @click="moveList">
          글목록
        </button>
        <button type="button" class="btn btn-outline-success mb-3 ms-1" @click="moveModify">
          글수정
        </button>
        <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="onDeleteArticle">
          글삭제
        </button>
      </div>
    </div>
  </div>
  <!-- <div class="col-lg-11">
                                                                                                <div class="row justify-content-start">
                                                                                                  <div class="d-flex justify-content-start">
                                                                                                    <CommentCreate :comment="comment" />
                                                                                                  </div>
                                                                                                </div>
                                                                                              </div>
                                                                                              <CommentList :comment="comment" /> -->
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