<script setup>
import { ref, onMounted } from "vue";
import { listAttractions } from "@/api/attraction";
import { listSido, listGugun } from "@/api/map";

import VKakaoMap from "@/components/common/VKakaoMap.vue";
import VSelect from "@/components/common/VSelect.vue";
import PlanListItem from "@/components/board/item/PlanListItem.vue";

const sidoList = ref([]);
const gugunList = ref([{ text: "구군선택", value: "" }]);
const attractions = ref([]);
const selectAttraction = ref({});
const props = defineProps({ type: String, selecedAttractionPlan: Array });

const param = ref({
  gugunCode: "",
  contentTypeId: 0,
  contentSubType: ""
});

const contentTypeList = ref([ // contentType
  { text: "여행지 유형", value: "" },
  { text: "스포츠시설", value: 7 },
  { text: "관광지", value: 12 },
  { text: "문화시설", value: 14 },
  { text: "축제공연행사", value: 15 },
  { text: "여행코스", value: 25 },
  { text: "레포츠", value: 28 },
  { text: "숙박", value: 32 },
  { text: "쇼핑", value: 38 },
  { text: "음식점", value: 39 }
]);

const contentSubTypeList = ref([ // contentSubType
  { text: "세부 유형", value: "" },
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

const selectedAttractonPlan = ref([]);

const emit = defineEmits(['selectPlanList']);

onMounted(() => {
  getSidoList();
});

// 옵션 1 - 시도 목록 가져오기
const getSidoList = () => {
  listSido(
    ({ data }) => {
      let options = [];
      options.push({ text: "시도선택", value: "" });
      data.forEach((sido) => {
        options.push({ text: sido.sidoName, value: sido.sidoCode });
      });
      sidoList.value = options;
    },
    (err) => {
      console.log(err);
    }
  );
};

// 옵션 2 - 구군 목록 가져오기
const onChangeSido = (val) => {
  listGugun(
    { sido: val },
    ({ data }) => {
      let options = [];
      options.push({ text: "구군선택", value: "" });
      data.forEach((gugun) => {
        options.push({ text: gugun.gugunName, value: gugun.gugunCode });
      });
      gugunList.value = options;
    },
    (err) => {
      console.log(err);
    }
  );
};

// 검색 - 구군 선택했을 때 여행지 정보 가져오기
const onChangeGugun = (val) => {
  param.value.gugunCode = val;
};

// 검색 - 여행지 유형 선택했을 때 여행지 정보 가져오기
const onChangeContentType = (val) => {
  param.value.contentTypeId = val;
  if (val != 7) { // 스포츠 시설이 아니면 정보 조회하기
    param.value.contentSubType = "";
    getAttractions();
  }
};

// 검색 - 여행지 세부 유형 선택했을 때 여행지 정보 가져오기
const onChangeContentSubType = (val) => {
  param.value.contentSubType = val;
  getAttractions();
};

// 여행지 정보 조회
const getAttractions = () => {
  if (param.value.contentTypeId === "") param.value.contentTypeId = 0; //정수로 변경해서 보내기
  listAttractions(
    param.value,
    ({ data }) => {
      console.log("데이터 조회 !! ", data);
      attractions.value = data;
    },
    (err) => {
      console.log(err);
    }
  );
};


const viewAttraction = (attraction) => {
  selectAttraction.value = attraction;
};

const addAttractionPlan = (attraction) => {
  selectedAttractonPlan.value.push(attraction);
  console.log("담긴 여행지 목록", selectedAttractonPlan.value);
}

const showBoardForm = () => {
  // alert("확인!");
  emit('selectPlanList', selectedAttractonPlan.value);
}

</script>

<template>
  <div class="container text-center">
    <div class="row">
      <div class="col-8 text-center mt-3">
        <!-- <div class="alert alert-success" role="alert">여행지 검색</div> -->
        <div class="row mb-2">
          <div class="col">
            <VSelect :selectOption="sidoList" @onKeySelect="onChangeSido" />
          </div>
          <div class="col">
            <VSelect :selectOption="gugunList" @onKeySelect="onChangeGugun" />
          </div>
          <div class="col">
            <VSelect :selectOption="contentTypeList" @onKeySelect="onChangeContentType" />
          </div>
          <div class="col">
            <VSelect :selectOption="contentSubTypeList" @onKeySelect="onChangeContentSubType"
              :disabled="param.contentTypeId != 7" /> <!-- 스포츠 시설일 때만 변경 가능 -->
          </div>
        </div>
        <VKakaoMap :attractions="attractions" :selectAttraction="selectAttraction" />

        <table class="table table-hover">
          <thead>
            <tr class="text-center">
              <th scope="col">여행지명</th>
              <th scope="col">상세주소</th>
              <th scope="col">전화번호</th>
              <th scope="col"></th>
            </tr>
          </thead>
          <tbody>
            <tr class="text-center" v-for="attraction in attractions" :key="attraction.id"
              @click="viewAttraction(attraction)">
              <th>{{ attraction.title }}</th>
              <td>{{ attraction.addr1 }}</td>
              <td>{{ attraction.tel }}</td>
              <td><button @click="addAttractionPlan(attraction)" class="btn btn-light btn-rounded"
                  data-mdb-ripple-init>+</button></td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="col-4 text-center mt-3">
        <!-- <div class="alert alert-success" role="alert">선택한 여행지 목록</div> -->
        <!-- 선택한 여행지 목록 -->
        <PlanListItem :selected-attraction-plan="selectedAttractonPlan" @selectPlanList="showBoardForm" />
      </div>
    </div>
  </div>
</template>

<style scoped>
mark.purple {
  background: linear-gradient(to top, #c354ff 20%, transparent 30%);
}
</style>
