<script setup>
import { registMember} from "@/api/member";
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { compileScript } from "vue/compiler-sfc";

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });

const member = ref({
  id: "",
  password: "",
  name: "",
  email: "",
  image: "",
  content: ""
});



const idErrMsg = ref("");
const passwordErrMsg = ref("");
watch(
  () => member.value.id,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      idErrMsg.value = "아이디를 확인해 주세요!!!";
    } else idErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => member.value.password,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 500) {
      passwordErrMsg.value = "비밀번호를 확인해 주세요!!!";
    } else passwordErrMsg.value = "";
  },
  { immediate: true }
);

function onSubmit() {
  // event.preventDefault();

  if (idErrMsg.value) {
    alert(idErrMsg.value);
  } else if (passwordErrMsg.value) {
    alert(passwordErrMsg.value);
  } else {
    writeMember();
  }
}

function writeMember() {
  console.log("회원등록하자!!", member.value);
  registMember(member.value,
    (response) => {
      console.log(response)
      alert("회원가입 완료!"); // 회원가입 성공 시 메시지 출력
      router.push({ name: "main" });
    },
    (error) => {
      console.log(error)
      alert("회원가입에 실패했습니다."); // 회원가입 실패 시 메시지 출력
    });
   // API 호출
};




</script>

<template>
  <form @submit.prevent="onSubmit">
    <div
      class="container d-flex justify-content-center align-items-center"
      style="min-height: 100vh"
    >
      <!-- 화면 중앙 정렬 -->
      <div class="col-md-6 rounded p-4">
        <h1 class="text-center">회원가입</h1>
        
          <div class="form-group mb-4">
            <label for="id">아이디</label>
            <input type="text" class="form-control" id="id" v-model="member.id" placeholder="아이디 입력" autocomplete="username"/>
          </div>
          <div class="form-group mb-4">
            <label for="password">비밀번호</label>
            <input type="password" class="form-control"  id="password" v-model="member.password" placeholder="비밀번호 입력" autocomplete="current-password"/>
          </div>
          <div class="form-group mb-4">
            <label for="name">이름</label>
            <input type="text" class="form-control"  id="name" v-model="member.name" placeholder="이름 입력" autocomplete="name"/>
          </div>
          <div class="form-group mb-4">
            <label for="email">이메일</label>
            <input type="email" class="form-control"  id="email" v-model="member.email" placeholder="이메일 입력" autocomplete="email"/>
          </div>
          <div class="form-group mb-4">
            <label for="image">이미지</label>
            <input type="text" class="form-control"  id="image" v-model="member.image" placeholder="이미지 등록" autocomplete="off"/>
          </div>
          <div class="form-group mb-4">
            <label for="content">내용</label>
            <input type="text" class="form-control"  id="content" v-model="member.content" placeholder="내용 입력" autocomplete="off"/>
          </div>
          <div class="d-flex justify-content-center">
            <!-- 가입 버튼을 가운데 정렬 -->
            <button type="submit" class="btn btn-primary" >가입</button>          
          </div>
      </div>
    </div>
  </form>
</template>
<style scoped></style>