<template>
    <button ref="buttonRef" class="dynamic-button">{{ props.label }}</button>
  </template>


<script setup>
import { ref, defineProps, watchEffect } from 'vue';

// 부모 컴포넌트로부터 props 받기
const props = defineProps({
    width:  String,
    height: String,
    color:  String,
    label:  String
});

const buttonRef = ref(null);

// props의 변화를 감지하고, CSS 변수 업데이트
watchEffect(() => {
  if (buttonRef.value) {
        buttonRef.value.style.setProperty('--button-width', props.width);
        buttonRef.value.style.setProperty('--button-height', props.height);
        buttonRef.value.style.setProperty('--button-color', props.color);
  }
});

</script>
<style scoped>
.dynamic-button {
    width: var(--button-width, 100px); /* 기본값 설정 */
    height: var(--button-height, 40px); /* 기본값 설정 */
    background-color: var(--button-color, blue); /* 기본값 설정 */
}
</style>