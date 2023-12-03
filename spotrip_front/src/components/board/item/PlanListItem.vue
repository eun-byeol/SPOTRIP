<template>
  <div class="slot-container">
    <draggable :list="list" :disabled="!enabled" item-key="name" class="list-group" @start="dragging = true"
      @end="dragging = false">
      <template #item="{ element }" class="list-group-item">
        <div class="row slot-item" :class="{ 'not-draggable': !enabled }">
          <div class="col-10">{{ element.title }}</div>
          <i class="col bi bi-x-lg" @click="removeAt(idx)"></i>
        </div>
      </template>
    </draggable>
  </div>
  <div class="finish-button">
    <div class="d-grid gap-2">
      <MDBBtn color="dark" @click="submitPlanList()">선택 완료</MDBBtn>
      <!-- <button class="btn btn-primary" type="button" @click="submitPlanList()">선택 완료</button> -->
    </div>
  </div>
</template>

<script>
import draggable from "vuedraggable";
import { MDBBtn } from "mdb-vue-ui-kit";

export default {
  props: {
    selectedAttractionPlan: {
      type: Array,
      required: true
    }
  },
  components: {
    draggable,
    MDBBtn
  },
  methods: {
    removeAt(idx) {
      this.list.splice(idx, 1);
    },
    submitPlanList() {
      this.$emit('selectPlanList');
    }
  },
  data() {
    return {
      enabled: true,
      dragging: false,
      list: this.selectedAttractionPlan
    };
  }
};
</script>

<style scoped>
.buttons {
  margin-top: 35px;
}

.ghost {
  opacity: 0.5;
  background: #c8ebfb;
}

.not-draggable {
  cursor: no-drop;
}

.slot-container {
  justify-content: space-between;
  align-items: center;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 20px;
  border-radius: 6px;
}

.slot-item {
  color: black;
  padding: 20px;
  margin-right: 20px;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin: 5px;
  background-color: #f9f9f9;
}

.delete-button {
  background-color: #000;
  /* 무채색 배경 */
  color: #fff;
  /* 흰색 텍스트 */
  border: none;
  padding: 5px;
  border-radius: 50%;
  cursor: pointer;
  font-size: 12px;
  /* 버튼 크기 조절을 위한 폰트 크기 설정 */
}

.finish-button {
  margin-top: 10%;
}
</style>
