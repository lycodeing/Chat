<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  setup() {
    return {}
  },
})
</script>

<template>
  <div class="cp_container">
    <div class="info"></div>
    <div class="send">
      <div class="send_tool"></div>
      <div class="send_textarea"></div>
      <div class="send_submit">
        <div class="send_btn">发送</div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.cp_container {
  display: flex;
  flex-direction: column;
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: #0d0d0d;
  border-radius: 4px;
  box-shadow: 0 0 1px 1px #2e2e2e, inset 0 0 0 4px #484846, inset 0 0 5px 5px #484846;
}

.info {
  flex: 4;
  border-radius: 6px;
  box-shadow: inset 0 0 2px 4px #484846;
}

.send {
  flex: 3;
  display: flex;
  flex-direction: column;
}
.send_tool {
  height: 38px;
  background-color: #484846;
}
.send_textarea {
  flex: 1;
  border-radius: 6px;
  box-shadow: inset 0 0 2px 4px #484846;
}
.send_submit {
  display: flex;
  align-items: center;
  height: 38px;
  padding-right: 12px;
  background-color: #484846;
}
.send_btn {
  width: 78px;
  height: 32px;
  margin-left: auto;
  line-height: 32px;
  text-align: center;
  color: #fff;
  border: 2px solid #343433;
  border-radius: 4px;
  background: linear-gradient(#646464, #585858);
  font-size: 14px;
  cursor: pointer;
  user-select: none;
}
.send_btn:active {
  background: linear-gradient(#454545, #252525);
}
</style>
