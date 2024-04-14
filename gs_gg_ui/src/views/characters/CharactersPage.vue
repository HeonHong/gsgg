<template lang="">
    <div class="out-div">
        <div id="sort-nav">
            <nav>
                <CharacterSortBar/>
            </nav>
        </div>
        <div id="crt-box-outline">
            <div id="crt-box" v-for="data, i in crtInfosProps" :key="i">
                <CharacterBox :crt-infos="data" :key="selectedIndex"/>
            </div>
        </div>
    </div>
    <div>
        <div id="page-box">
            <div class="page-num-box" 
            v-for="num in this.pageNums" :key="num"
            @click="crtInfosChg(num)">
            {{num}}
            </div>
        </div>
    </div>
</template>
<script>
import CharacterBox from './CharacterBox.vue';              //캐립터 정보가 담긴 box component
import CharacterSortBar from './CharacterSortBar.vue';      //검색 조건을 조정할 수 있는 바
import apiCall from '@/js/mixins/api/api-call';             //서버 통신 api-call
import '@/css/charactersStyle/CharacterPage.css';           //css 파일
export default {
    //data
    data() {
        return {
            crtInfos        : null,         //캐릭터 정보 배열
            pageNums        : 0,            //총 페이지 크기
            arrCrtInfos     : [],           //30개로 나눈 캐릭터 정보 배열
            crtInfosProps   : [],
            selectedIndex   : 0,
        }
    },
    
    //mounted
    mounted() {
        let url = "/api/getCrtInfos";   //연결 url

        let success = (result) => {
            this.crtInfos = result.data;    //결과값을 캐릭터 정보 배열에 저장

            //페이징 세팅
            this.setPageNumsAndArrCrtInfos();

            //props 전달을 위한 배열 초기값 설정
            this.crtInfosProps = this.arrCrtInfos[0];

        };

        let fail = (data) => {
            console.log('fail', data);
        };

        //챔피언 정보 api call
        apiCall.get(url, '', success, fail);
    },

    //components
    components: {
        CharacterBox: CharacterBox,
        CharacterSortBar: CharacterSortBar,
    },

    //methods
    methods: {
        crtInfosChg(num) {
            this.selectedIndex = num-1;
            this.crtInfosProps = [...this.arrCrtInfos[this.selectedIndex]]  //페이지 이동시 props 전달을 위한 새로운 배열 생성
            window.scrollTo(0,0);   //스크롤 맨 위로 이동
        },

        //페이징을 위한 수, 캐릭터 배열 세팅
        setPageNumsAndArrCrtInfos() {
            let perPageNum  = 30;                                   //한 페이지에 display 될 캐릭터의 개수
            let totNum      = this.crtInfos.length;                 //캐릭터 정보 배열 크기
            let totPageNum  =  Math.ceil(totNum / perPageNum);      //필요 페이지 크기

            this.pageNums   = totPageNum;
            
            //캐릭터 정보를 페이지 별로 나눔(30개씩)
            let arrTmp = [];

            for(let i=0; i < totNum; i++) {
                arrTmp.push(this.crtInfos[i]);

                if(arrTmp.length == perPageNum) {       //임시배열의 크기가 페이지당 캐릭터의 수와 같을 경우
                    this.arrCrtInfos.push(arrTmp);
                    arrTmp = [];

                } else if(i == totNum - 1) {            //마지막 페이지 캐릭터의 수가 30개 보다 작을 경우
                    this.arrCrtInfos.push(arrTmp);
                }
            }
        }
    },
}
</script>
<style>
</style>