<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  setup() {
    return {}
  },
})
</script>

<template>
  <div class="ul_container">
    <div class="chat_item"></div>
    <div class="chat_item"></div>
  </div>
</template>

<style scoped>
.ul_container {
  display: flex;
  flex-direction: column;
  /* justify-content: center; */
  align-items: center;
  width: 100%;
  height: 100%;
  padding: 12px 6px;
  background-color: #0d0d0d;
  border-radius: 4px;
  box-shadow: 0 0 1px 1px #2e2e2e, inset 0 0 0 4px #484846, inset 0 0 5px 5px #484846;
}

.chat_item {
  display: flex;
  align-items: center;
  position: relative;
  width: 95%;
  height: 62px;
  margin-bottom: 8px;
  border: 2px solid #212121;
  background-color: #303131;
  box-shadow: 0 0 2px 2px #212121;
  border-radius: 4px;
}
</style>
