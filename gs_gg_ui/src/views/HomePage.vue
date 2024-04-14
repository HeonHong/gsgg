<!-- HomePage.vue -->
<template lang="">
    <div class="main-container">
    <!-- header 및 logo -->
    <div class="header">
        <div class="logo-img">
            <img src="@/assets/GS_GG Logo_NoBG.png" @click="goToMainPage"/>
        </div>
        <div class="header-login">
            <div v-if="!isLoggedIn">
            <span @click="login">로그인</span>&nbsp;&nbsp;&nbsp;
            <span>회원가입</span>&nbsp;&nbsp;
            </div>
            <div v-if="isLoggedIn">
            <img :src="profileImage"/>
            <span>{{name}}</span>&nbsp;&nbsp;&nbsp;
            <span @click="isLoggedIn=false">로그아웃</span>&nbsp;&nbsp;
            </div>
        </div>
    </div>

    <!-- 메인 탭 버튼 -->
    <div class="tab-btn-grp" :style="{'grid-template-columns': gridTemplateColumns }">
        <TabButtons v-for="objBtn in arrTabBtns" :key="objBtn"
        :objBtn="objBtn" 
        @componentChg="componentChg"/>
    </div>

    <!-- 컴포넌트 캐싱 -->
    <!-- 캐싱하지 않는 경우 routerView만 사용해도 무방  -->
    <router-view v-slot="{Component}">
        <keep-alive>
            <component :is="Component" ></component>
        </keep-alive>
    </router-view>
    <LoadingBar/>
</div>
</template>

<script>
//import
import axios from 'axios';
import TabButtons from '@/components/TabButtons.vue';
import apiCall from "@/js/mixins/api/api-call.js"
import commonUtils from "@/js/common-utils.js"

//공통 버튼 테스트
import LoadingBar from '@/components/LoadingBar.vue';

export default {
    mixins: [apiCall],
    methods: {
        //헤더 GS.GG 클릭 시 Main 화면으로 이동
        goToMainPage() {
            this.$router.push({ path: '/' });
        },
        //메인 탭 클릭시 component change 이벤트
        componentChg(idNum) {
            this.$router.push(this.arrTabBtns[idNum].pagePath);
        },

        //테스트 메서드
        test() {
            console.log('axios start');
            this.$axios.get("/api")
                .then(res => console.log(res))
        },

        test2() {
            console.log('axios2 start');
            axios.get('/api/test')
                .then(res => console.log(res))
        },
        btnFunc() {
        },
        login() {
            this.$router.push('/loginpage')
        }
    },

    //components
    components: {
        TabButtons,  //메인 탭
        LoadingBar,
    },
    computed: {
        gridTemplateColumns() {
            return `repeat(${this.arrTabBtns.length},1fr)`
        }
    },
    //data
    data() {
        return {
            //메인 탭
            arrTabBtns: [
                { id: 0, tabName: "전적검색", pagePath: '/' },
                { id: 1, tabName: "챔피언 소개", pagePath: '/characters' },
                { id: 2, tabName: "갈등을 빚자", pagePath: '/userVs' },
                { id: 3, tabName: "TestPage", pagePath: '/test' },
                { id: 4, tabName: "SocketTest", pagePath: '/sockettest' },
            ],
            isLoggedIn: false,
            name: '',
            profileImage: '',
        }
    },
    mounted() {
        let kakaoInfo = localStorage.getItem('kakaoInfo');
        console.log(kakaoInfo);
        if (!commonUtils.isNull(kakaoInfo)) {
            let userInfo = JSON.parse(kakaoInfo);
            this.isLoggedIn = true;
            this.name = userInfo.properties.nickname;
            this.profileImage = userInfo.properties.profile_image;
        }
        // localStorage.clear();
    },

}
</script>

<style>
</style>