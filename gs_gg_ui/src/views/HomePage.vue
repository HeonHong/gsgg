<template lang="">
    <!-- header 및 logo -->
    <div class="header">
        <h1 @click="goToMainPage">GS.GG</h1>
    </div>

    <!-- 메인 탭 버튼 -->
    <div class="tab-btn-grp">
        <TabButtons v-for="objBtn in arrTabBtns" :key="objBtn"
        :objBtn="objBtn" 
        @componentChg="componentChg($event)"/>
    </div>


    <!-- test 영역 테스트 후 삭제 예정-->
    <!-- <EpInput label='검색'></EpInput>
    <button @click="test">test button12</button>
    <button @click="test2">test button2</button>  -->
    <!--  -->

    <EpButton :width="500" :height="100" :color="'#0FC2C0'" :label="'common button'" @btnClick="btnFunc"/>

    <!-- 컴포넌트 캐싱 -->
    <!-- 캐싱하지 않는 경우 routerView만 사용해도 무방  -->
    <router-view v-slot="{Component}">
        <keep-alive>
            <component :is="Component" ></component>
        </keep-alive>
    </router-view>
    <LoadingBar/>
</template>
<script>
//import
import axios from 'axios';
import TabButtons from '@/components/TabButtons.vue';
import apiCall from "@/js/mixins/api/api-call.js"

//공통 버튼 테스트
import EpButton from '@/components/EpButton.vue';
import LoadingBar from '@/components/LoadingBar.vue';

export default {
    mixins:[apiCall],

    //methods
    methods: {
        //헤더 GS.GG 클릭 시 Main 화면으로 이동
        goToMainPage() {
            this.$router.push({ path: '/'});
        },

        //메인 탭 클릭시 component change 이벤트
        componentChg(idNum){
            this.$router.push(this.arrTabBtns[idNum].pagePath);
        },

        //테스트 메서드
        test() {
            console.log('axios start');
            axios.get("/api")
                .then(res => console.log(res))
        },

        test2() {
            console.log('axios2 start');
            axios.get('/api/test')
                .then(res => console.log(res))
        },
        btnFunc(){
            console.log("눌렸냐?");
        }
    },

    //components
    components: {
        TabButtons: TabButtons,  //메인 탭
        EpButton: EpButton,
        LoadingBar,
    },

    //data
    data() {
        return {
            //메인 탭
            arrTabBtns: [
                {id: 0, tabName: "tab1", pagePath: '/'},
                {id: 1, tabName: "tab2"},
                {id: 2, tabName: "tab3"},
                {id: 3, tabName: "챔피언 소개", pagePath: '/characters'},
            ],
        }
    }
}
</script>

<style>
:root{
    --color1:#283959;
    --color2:#A6A9F5;
    --color3:#936CF5;
    --color4:#699AF5;
    --color5:#0FC2C0;
}
.header{
    height:100px;
    background:var(--color1);
    color:white;
    cursor: pointer;
}
.search{
    display:flex;
    justify-content: flex-end;
}    
.tab-btn-grp{
    display: flex;
    width: fit-content;
}
</style>