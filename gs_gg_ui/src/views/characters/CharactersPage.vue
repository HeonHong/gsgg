<template lang="">
    <div class="out-div">
        캐릭터 소개 페이지
        <div>
            <CharacterBox :crtInfos="crtInfos"/>
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
import CharacterBox from './CharacterBox.vue';
import apiCall from '@/js/mixins/api/api-call';
export default {
    //data
    data() {
        return {
            crtInfos    : null,         //캐릭터 정보 배열
            pageNums    : 0,            //총 페이지 크기
            arrCrtInfos : [],           //30개로 나눈 캐릭터 정보 배열
        }
    },
    
    //mounted
    mounted() {
        let url = "/api/getCrtInfos";   //연결 url

        let success = (result) => {
            this.crtInfos = result.data;    //결과값을 캐릭터 정보 배열에 저장

            //페이징을 위한 수 세팅
            let setPageNums = () => {
                let perPageNum  = 30;                                   //한 페이지에 display 될 캐릭터의 개수
                let totNum      = this.crtInfos.length;                 //캐릭터 정보 배열 크기
                let totPageNum  =  Math.ceil(totNum / perPageNum);      //필요 페이지 크기

                this.pageNums   = totPageNum;

                for(let i=0; i < totNum; i++) {
                    let arrTmp = [];
                    arrTmp.push(this.crtInfos[i]);

                    if(i % 30 == 0) {
                        this.arrCrtInfos.push(arrTmp);
                        arrTmp = [];
                    }
                }

                console.log(this.arrCrtInfos[2].length)
            }

            setPageNums();
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
    },

    //methods
    methods: {
        crtInfosChg(num) {
            console.log(num)
        }
    },
}
</script>
<style>
.out-div{
    padding: 20px;
    height: 200vh;
}
#page-box{
    display: flex;
    border: 1px solid black;
    justify-content: center;
}
.page-num-box {
    width: 5vh;
    border: 1px solid red;
    text-align: center;
}
</style>