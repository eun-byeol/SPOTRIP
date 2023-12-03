<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { useMenuStore } from "@/stores/menu";

const router = useRouter();

const memberStore = useMemberStore();

const { isLogin } = storeToRefs(memberStore);
const { userLogin, getUserInfo } = memberStore;
const { changeMenuState } = useMenuStore();

const loginUser = ref({
  id: "",
  password: "",
});

const login = async () => {
  await userLogin(loginUser.value);
  let token = sessionStorage.getItem("accessToken");
  console.log(token);
  if (isLogin) {
    getUserInfo(token);
    changeMenuState();
  }
  router.push("/");
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-title text-center">
          로그인
        </h2>
      </div>
      <div class="col-lg-10">
        <form>
          <div class="form-check mb-3 float-end">
            <input class="form-check-input" type="checkbox" />
            <label class="form-check-label" for="saveid"> 아이디저장 </label>
          </div>
          <div class="mb-3 text-start">
            <label for="id" class="form-label">아이디 : </label>
            <input type="text" class="form-control" v-model="loginUser.id" placeholder="아이디..." />
          </div>
          <div class="mb-3 text-start">
            <label for="userpwd" class="form-label">비밀번호 : </label>
            <input type="password" class="form-control" v-model="loginUser.password" @keyup.enter="login"
              placeholder="비밀번호..." />
          </div>
          <div class="col-auto text-center">
            <button type="button" class="btn btn-outline-primary mb-3" @click="login">
              로그인
            </button>
            <button type="button" class="btn btn-outline-success ms-1 mb-3">비밀번호 찾기</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.bg-title {
  color: black;
}
</style>
