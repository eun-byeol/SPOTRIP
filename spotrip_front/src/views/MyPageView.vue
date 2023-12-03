<script setup>
import { ref, onBeforeMount } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getMember, updateMember, deleteMember } from "@/api/member";

const route = useRoute();
const router = useRouter();
const memberId = ref("");
const newPassword = ref("");

onBeforeMount(async() => {
    console.log('memberInfo>>>',memberInfo.value)
    await router.isReady()
    const {id} = route.params;
    memberId.value = id;
    getMemberInfo();
});

const member = ref({
  id: "",
  password: "",
  name: "",
  email: "",
  image: "",
  content: ""
});

const memberInfo = ref({
    id : "",
    password : "",
    name : "",
    email : "",
    image : "",
    content : ""
});
const passwordErrMsg = ref("");
const getMemberInfo = () => {
  console.log("서버에서 회원 정보를 불러오자!!!", memberId.value);
  // API 호출
  getMember(memberId.value, ({ data }) => { 
    console.log(data)
    memberInfo.value.id = data.id,
    memberInfo.value.password = data.password,
    memberInfo.value.name = data.name,
    memberInfo.value.email = data.email,
    memberInfo.value.image = data.image,
    memberInfo.value.content = data.content
  },
    (error) => { 
      console.log(error)
    });
};
function onSubmit() {
  if (passwordErrMsg.value) {
    alert(passwordErrMsg.value);
  } else {
    modifyMember();
  }
}
function modifyMember() {
  updateMember({
    id : memberId.value,
      password: newPassword.value, // 새로운 비밀번호 추가
      name: memberInfo.value.name,
      email: memberInfo.value.email,
      image: memberInfo.value.image,
      content: memberInfo.value.content
    },
    (response) => { 
      console.log(response)
      router.push({ name: "mypage" });
    },
    (error) => { 
      console.log(error)
    }
  )
}
function onDeleteMember() {
  deleteMember(memberId.value,
    (response) => {
      console.log(response)
      alert("다음에 또 와주실거죠?T.T");
      router.push({ name: "main" });
    },
    (error) => {
      console.log(error)
    });
}


</script>
<template>
    <div
      class="container d-flex justify-content-center align-items-center"
      style="min-height: 100vh"
    >
      <!-- 화면 중앙 정렬 -->
      <div class="col-md-6 rounded p-4">
        <h1 class="text-center">마이 페이지</h1>
        <form>
          <div class="form-group mb-4">
            <label for="id" >아이디</label>
            <input type="id" class="form-control" id="id" :value="memberInfo.id" disabled/>
          </div>
          <div class="form-group mb-4">
            <label for="password">비밀번호</label>
            <input type="password" class="form-control" id="password" :value="memberInfo.password" placeholder="새로운 비밀번호 입력" disabled/>
          </div>
          <div class="form-group mb-4">
            <label for="name">이름</label>
            <input type="name" class="form-control" id="name" :value="memberInfo.name" disabled/>
          </div>
          <div class="form-group mb-4">
            <label for="email">이메일</label>
            <input type="email" class="form-control" id="email" :value="memberInfo.email" disabled/>
          </div>
          <div class="form-group mb-4">
            <label for="image">이미지</label>
            <input type="image" class="form-control" id="image" :value="memberInfo.image" disabled/>
          </div>
          <div class="form-group mb-4">
            <label for="content">상세 정보</label>
            <input type="content" class="form-control" id="content" :value="memberInfo.content" disabled/>
          </div>
          <div class="form-group mb-4">
          <label for="newPassword">새로운 비밀번호</label>
          <input
            type="password"
            class="form-control"
            id="newPassword"
            v-model="newPassword"
            placeholder="새로운 비밀번호 입력"
          />
        </div>
          <div class="d-flex justify-content-center">
            <button type="submit" class="btn btn-primary mx-2" @click.prevent="onSubmit">수정</button>
            <button type="submit" class="btn btn-primary mx-2" @click.prevent="onDeleteMember">탈퇴</button>
          </div>
        </form>
      </div>
    </div>
</template>
<style scoped></style>