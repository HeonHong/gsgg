<template lang="">
    <div class="out-div">
        <div id="sort-nav">
            <nav style="width: 100%;">
                <CharacterSortNav :crt-kor-names="crtKorNames" @result-array="recieveResult" @sort-string="sortString"/>
            </nav>
        </div>
        <div id="right-div">
            <div id="crt-box-outline">
                <div id="crt-box" v-for="data, i in crtInfosProps" :key="i">
                    <CharacterBox :crt-infos="data" :key="selectedIndex" @crt_id="selectedCrt" />
                </div>
            </div>
            <div id="page-box">
                <div class="page-num-box" 
                v-for="num in this.pageNums" :key="num"
                @click="crtInfosChg(num)">
                {{num}}
                </div>
            </div>
        </div>

        <div id="popup-div" v-if="this.isPopupOpen == true">
            <CharacterDescPopup @close-popup="this.isPopupOpen = false;" :objCrt="objCrt"/>
        </div>
    </div>
    
</template>
<script>
import CharacterBox from './CharacterBox.vue';              //캐립터 정보가 담긴 box component
import CharacterSortNav from './CharacterSortNav.vue';      //검색 조건을 조정할 수 있는 바
import CharacterDescPopup from './CharacterDescPopup.vue';  //캐릭터 상세 정보 팝업
import apiCall from '@/js/mixins/api/api-call';             //서버 통신 api-call
import '@/css/charactersStyle/CharacterPage.css';           //css 파일
export default {
    //data
    data() {
        return {
            crtInfosOri     : null,         //서버 통신된 배열 그대로(초기 통신 이후 수정 안함)
            crtInfos        : null,         //캐릭터 정보 배열
            crtKorNames     : [],           //캐릭터 한국 이름 배열
            pageNums        : 0,            //총 페이지 크기
            arrCrtInfos     : [],           //30개로 나눈 캐릭터 정보 배열
            crtInfosProps   : [],           //최종 캐릭터 정보 배열 - box 전달됨 / 페이징 되어 있음
            selectedIndex   : 0,            //선택된 페이지 인덱스
            isPopupOpen     : false,        //팝업 오픈 상태
            objCrt          : {}            //한개의 챔피언을 담을 객체
        }
    },
    
    //mounted
    mounted() {
        let url = "/api/getCrtInfos";   //연결 url

        let success = (result) => {
            this.crtInfosOri = result.data ; //origin 배열에 저장
            this.crtInfos = result.data;    //결과값을 캐릭터 정보 배열에 저장

            //한글 이름 배열 세팅
            this.crtKorNames = result.data.map(crt => crt.KOR_NAME);

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
        CharacterBox:       CharacterBox,
        CharacterSortNav:   CharacterSortNav,
        CharacterDescPopup: CharacterDescPopup,
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
            //전달용 배열 초기화
            this.arrCrtInfos = [];
            
            for(let i=0; i < totNum; i++) {
                arrTmp.push(this.crtInfos[i]);

                if(arrTmp.length == perPageNum) {       //임시배열의 크기가 페이지당 캐릭터의 수와 같을 경우(30개가 찼을 경우)
                    this.arrCrtInfos.push(arrTmp);
                    arrTmp = [];

                } else if(i == totNum - 1) {            //마지막 페이지 캐릭터의 수가 30개 보다 작을 경우
                    this.arrCrtInfos.push(arrTmp);
                }
            }
        },

        //recieveResult(챔피언 검색 결과)
        recieveResult(result) {
            this.sortString("kor");

            this.crtInfos = this.crtInfosOri.filter(data => result.includes(data.KOR_NAME));

             //페이징 세팅
             this.setPageNumsAndArrCrtInfos();

            //props 전달을 위한 배열 초기값 설정
            this.crtInfosProps = this.arrCrtInfos[0];

            // document.getElementById('page-box').style.display = 'none';
            
        },

        //sortString(챔피언 정렬)
        sortString(result) {
            var sortFunc;
            switch (result) {
                case "kor":
                    sortFunc = (a, b) => {
                        if(a.KOR_NAME > b.KOR_NAME) return 1;
                        if(a.KOR_NAME < b.KOR_NAME) return -1;
                        if(a.KOR_NAME === b.KOR_NAME) return 0;
                    };
                break;
            
                case "kor_rev":
                    sortFunc = (a, b) => {
                        if(a.KOR_NAME < b.KOR_NAME) return 1;
                        if(a.KOR_NAME > b.KOR_NAME) return -1;
                        if(a.KOR_NAME === b.KOR_NAME) return 0;
                    };
                break;

                case "eng":
                    sortFunc = (a, b) => {
                        if(a.ENG_NAME > b.ENG_NAME) return 1;
                        if(a.ENG_NAME < b.ENG_NAME) return -1;
                        if(a.ENG_NAME === b.ENG_NAME) return 0;
                    };
                break;

                case "eng_rev":
                    sortFunc = (a, b) => {
                        if(a.ENG_NAME < b.ENG_NAME) return 1;
                        if(a.ENG_NAME > b.ENG_NAME) return -1;
                        if(a.ENG_NAME === b.ENG_NAME) return 0;
                    };
                break;
                    
                default:
                    break;
                    
                }

            var tmpArr = [];
            tmpArr = this.crtInfos.sort((a, b) => sortFunc(a, b));
            this.crtInfos = tmpArr;

            //페이징 세팅
            this.setPageNumsAndArrCrtInfos();

            //props 전달을 위한 배열 초기값 설정
            this.crtInfosProps = this.arrCrtInfos[0];
        },

        //캐릭터 상세 팝업 오픈
        showPopup() {
            console.log("qwe")
        },

        selectedCrt(result) {
            this.isPopupOpen = this.isPopupOpen ? false : true;

            //캐릭터 배열에서 선택된 캐릭터 filter 후 객체에 세팅
            this.objCrt = this.crtInfosOri.filter(data => data.CRT_ID === result);
        }
    },
}
</script>
<style>
</style>