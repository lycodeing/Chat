<script lang="ts">
import { defineComponent } from 'vue'
import userList from '@/components/userList.vue'
import chatPanel from './components/chatPanel.vue'

export default defineComponent({
  components: {
    userList,
    chatPanel,
  },
  setup() {
    return {}
  },
})
</script>

<template>
  <div class="app">
    <div class="ul_box">
      <userList></userList>
    </div>
    <div class="content_box">
      <chatPanel></chatPanel>
    </div>
  </div>
</template>

<style scoped>
.app {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  display: flex;
  gap: 4px;
  background-color: #21211f;
  padding: 2px;
}
.ul_box {
  width: 250px;
}
.content_box {
  flex: 1;
  position: relative;
}
</style>
