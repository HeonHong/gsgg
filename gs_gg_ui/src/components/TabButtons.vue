<template lang="">
    <div>
        <button class="tabBtn" 
            @click="componentChg(objBtn.id)">{{objBtn.tabName}}</button>

            <!-- <button @click="console.log(this.objBtn)">{{this.objBtn.tabName}}</button> -->
    </div>
</template>

<script>
import apiCall from "@/js/mixins/api/api-call.js"

export default {
    mixins:[apiCall],

    methods: {
        componentChg(idNum) {
            this.pageIDNum = idNum;
            this.$emit("componentChg", idNum);
        }
    },

    props: {
        objBtn: Object
    },
}
</script>

<style scoped>
    .tabBtn {
        width: 100px;
        height: 30px;
        background-color: #fff; /* 초기 배경 색상 */
        border: 1px solid #ccc; /* 테두리 스타일 및 색상 */
        cursor: pointer;
        transition: background-color 0.3s; /* 호버 시 배경 색상 전환을 */
    }
    .tabBtn:hover {
        background-color: #ccc;
    }
</style>