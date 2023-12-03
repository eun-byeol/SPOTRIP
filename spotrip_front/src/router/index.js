import { createRouter, createWebHistory } from "vue-router";
import TheMainView from "../views/TheMainView.vue";
// import TheBoardView from "../views/TheBoardView.vue";
// import TheElectricChargingStation from "@/views/TheElectricChargingStation.vue";

import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

const onlyAuthUser = async (to, from, next) => {
  const memberStore = useMemberStore();
  const { userInfo, isValidToken } = storeToRefs(memberStore);
  const { getUserInfo } = memberStore;

  let token = sessionStorage.getItem("accessToken");

  if (userInfo.value != null && token) {
    await getUserInfo(token);
  }
  if (!isValidToken.value || userInfo.value === null) {
    next({ name: "user-login" });
  } else {
    next();
  }
};
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: TheMainView,
    },
    {
      path: "/mypage/:id",
      name: "mypage",
      component: () => import("../views/MyPageView.vue")
    },
    {
      path: "/signup",
      name: "signup",
      component: () => import("../views/SignupView.vue")
    },
    {
      path: "/login",
      name: "login",
      component: () => import("../views/LoginView.vue")
    },
    
    // {
    //   path: "/estations",
    //   name: "estations",
    //   component: TheElectricChargingStation,
    // },
    {
      path: "/board",
      name: "board",
      component: () => import("../views/TheBoardView.vue"),
      redirect: { name: "article-list" },
      children: [
        {
          path: "list",
          name: "article-list",
          component: () => import("@/components/board/BoardList.vue"),
        },
        {
          path: "view/:articleid",
          name: "article-view",
          component: () => import("@/components/board/BoardDetail.vue"),
        },
        {
          path: "write",
          name: "article-write",
          component: () => import("@/components/board/BoardWrite.vue"),
        },
        {
          path: "modify/:articleid",
          name: "article-modify",
          component: () => import("@/components/board/BoardModify.vue"),
        },
      ],
    },
  ],
});

export default router;
