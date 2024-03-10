
<template>
    <!-- <div class="ep-button">
        <button @click="btnClick" class="common-btn">{{this.label}}</button>
    </div> -->

    <button ref="buttonRef" class="dynamic-button">{{ props.label }}</button>

</template>


<script>
import { ref, toRefs, watchEffect } from 'vue';
// import commonUtils from "../js/common-utils"

export default {
setup(props){
    // 부모 컴포넌트로부터 props 받기
    // const props = defineProps({
    // width:  String,
    // height: String,
    // color:  String,
    // label:  String
    // });


    const {width,height,color}=toRefs(props);

    const buttonRef = ref(null);

    // props의 변화를 감지하고, CSS 변수 업데이트
    watchEffect(() => {
    if (buttonRef.value) {
        buttonRef.value.style.setProperty('--button-width', width);
        buttonRef.value.style.setProperty('--button-height', height);
        buttonRef.value.style.setProperty('--button-color', color);
    }
    });

    return{
        props,
        buttonRef
    }

},



    props: ['width', 'height', 'color', 'label'],
    // data(){
    //     return{
    //         stWidth:null,
    //         stHeight:null,
    //     }
    // },
    // methods:{
    //     btnClick(){
    //         this.$emit('btnClick');
    //     }
    // },
    // mounted() {
    //     //width
    //     if(!commonUtils.isNull(this.width)) {
    //         this.stWidth = this.width + 'px';
    //         this.$el.style.setProperty('--dynamic-width', this.stWidth);
    //     }
    // }

}

</script>


<style scoped>
.dynamic-button {
    width: var(--button-width, 100px); /* 기본값 설정 */
    height: var(--button-height, 40px); /* 기본값 설정 */
    background-color: var(--button-color, blue); /* 기본값 설정 */
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