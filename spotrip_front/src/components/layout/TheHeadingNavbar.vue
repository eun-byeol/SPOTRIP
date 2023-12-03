<script setup>
// import BoardFormItem from "@components/board/item/BoardFormItem.vue";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import { useMenuStore } from "@/stores/menu";
import { ref, watch, onMounted } from "vue";
import { MDBBtn } from 'mdb-vue-ui-kit';

const router = useRouter();
const menuStore = useMenuStore();
const memberStore = useMemberStore();

const { menuList } = storeToRefs(menuStore);
const { changeMenuState } = menuStore;
const { userLogout, isLogin } = memberStore;
const memberInfo = ref({})
const { userInfo } = storeToRefs(memberStore);

const logout = () => {
  userLogout(sessionStorage.getItem("id"));

  sessionStorage.removeItem("accessToken");
  sessionStorage.removeItem("refreshToken");
  sessionStorage.removeItem("id");
  if (!isLogin) {
    changeMenuState();
  }
}

const moveWrite = () => {
  router.push({ name: "article-write" });
};

function check(menu) {
  console.log("네비바 확인!!!!!!!!!", menu);
  console.log(menu.show);
  return menu.show;
}

function checklogout(menu) {
  //console.log(menu, menu.routeName, menu.routeName==='logout')
  return menu.routeName === 'logout';
}

const getId = () => {
  return sessionStorage.getItem("id")
}

console.log(userInfo.value)
</script>

<template>
  <nav class="navbar navbar-expand-lg bg-body-tertiary sticky-top">
  <div class="container-fluid">
    <router-link :to="{ name: 'main' }" class="navbar-brand">
      <img src="@/assets/logo.png" class="rounded mx-auto d-block" alt="..." style="width: 300px; height: auto;" />
      </router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarScroll"
        aria-controls="navbarScroll" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarScroll">
        <ul class="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll" style="--bs-scroll-height: 100px">
          <li class="nav-item">
          <router-link :to="{ name: 'board' }" class="nav-link">동료 찾기</router-link>
        </li>

          <ul class="navbar-nav ms-auto my-2 my-lg-0 navbar-nav-scroll" style="--bs-scroll-height: 100px">
          <template v-for="menu in menuList" :key="menu.routeName">
            <template v-if="menu.show">
              <template v-if="menu.routeName === 'logout'">
                <li class="nav-item">
                  <router-link to="/" @click.prevent="logout" class="nav-link">
                    {{ menu.name }}</router-link>
                </li>
              </template>
              <template v-else-if="menu.routeName === 'mypage'">
                  <li class="nav-item">
                    <router-link :to="{ name: menu.routeName, params: { id: getId } }" class="nav-link">
                      {{ menu.name }}</router-link>
                  </li>
                </template>
                <template v-else>
                  <li class="nav-item">
                    <router-link :to="{ name: menu.routeName }" class="nav-link">{{
                      menu.name
                    }}</router-link>
                  </li>
                </template>
              </template>
            </template>
          </ul>
          <!--<div id="status-login" >
                                    <li class="nav-item mx-0 mx-lg-1">
                                      <router-link class="nav-link py-3 px-0 px-lg-3 rounded" :to="{name: 'main'}" @click.prevent="logout">로그아웃</router-link>
                                    </li>
                                    <li class="nav-item mx-0 mx-lg-1">
                                      <router-link class="nav-link py-3 px-0 px-lg-3 rounded" :to="{name:'mypage', params:{'id':'wjddn0308'}}">마이페이지</router-link>
                                    </li>
                                  </div>
                                  <div id="status-logout">
                                    <li class="nav-item mx-0 mx-lg-1">
                                      <router-link class="nav-link py-3 px-0 px-lg-3 rounded" :to="{name:'signup'}">회원가입</router-link>
                                    </li>
                                    <li class="nav-item mx-0 mx-lg-1">
                                      <router-link a class="nav-link py-3 px-0 px-lg-3 rounded" :to="{name:'login'}">로그인</router-link>
                                    </li>
                                  </div>-->

        </ul>
      </div>

      <div class="col-md-2 text-start">

        <MDBBtn class="btn text-white" style="background-color: #800000" @click="moveWrite">글쓰기</MDBBtn>
        <!-- <button type="button" class="btn btn-outline-primary btn-sm" @click="moveWrite">
                              글쓰기
                            </button> -->
      </div>
      <!-- <div class="row justify-content-center">
                                <div class="col-lg-10">
                                  <h2 class="my-3 py-3 shadow-sm bg-light text-center">
                                    <mark class="sky">글쓰기</mark>
                                  </h2>
                                </div>
                                <div class="col-lg-10 text-start">
                                  <BoardFormItem type="regist" />
                                </div>
                              </div> -->
    </div>
  </nav>
</template>

<style scoped></style>