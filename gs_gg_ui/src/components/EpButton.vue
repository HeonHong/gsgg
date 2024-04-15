<template>
    <!-- <div class="ep-button">
        <button @click="btnClick" class="common-btn">{{this.label}}</button>
    </div> -->

    <button ref="buttonRef" class="dynamic-button">{{ props.label }}</button>
    <!-- <input type="button" ref="buttonRef" class="dynamic-button" :value="props.label" > -->

</template>


<script>
import { ref, toRefs, watchEffect } from 'vue';
// import commonUtils from "../js/common-utils"

export default {
    setup(props) {
        // 부모 컴포넌트로부터 props 받기
        // const props = defineProps({
        // width:  String,
        // height: String,
        // color:  String,
        // label:  String
        // });


        const { width, height, color, fontColor} = toRefs(props);

        const buttonRef = ref(null);

        // props의 변화를 감지하고, CSS 변수 업데이트
        watchEffect(() => {
            if (buttonRef.value) {
                console.log(width);
                buttonRef.value.style.setProperty('--button-width', width.value);
                buttonRef.value.style.setProperty('--button-height', height.value);
                // buttonRef.value.style.setProperty('--button-color', color); 
                // color는 문자열이 아니라 참조값이라서 color.value로 가져와야함.
                if(color?.value!=undefined) buttonRef.value.style.setProperty('--button-color', color.value); 
                if(fontColor?.value!=undefined) buttonRef.value.style.setProperty('--button-font-color', fontColor.value); 
            }
        });

        return {
            props,
            buttonRef
        }

    },
    props: ['width', 'height', 'color', 'label','fontColor'],
    methods:{
        emitClick(){
            this.$emit('click')
        }
    }
}

</script>


<style scoped>
.dynamic-button {
    width: var(--button-width, 10rem);
    /* 기본값 설정 */
    height: var(--button-height, 4rem);
    /* 기본값 설정 */
    background-color: var(--button-color,var(--color1));
    /* background-color: var(--color1); */
    /* 기본값 설정 */
    border-radius: 5px;
    border:none;
    color:var(--button-font-color,var(--color6));
}

/* .common-btn{
        width: var(--dynamic-width);
        height: var(--dynamic-height);
        background: var(--dynamic-color);
        font-family: var(--main-font);
        border: none;
        border-radius: 5%;
    } */
</style>