<template lang="">
    <div id="window">
        <div id="search-div">
            <input type="text" id="search-input" placeholder="챔피언 이름 검색(가렌, ㄱㄹ)" :value="searchVal" @keyup="onKeyupSearch"/>
        </div>
        <div id="sort-div">
            <div class="sort-box" v-for="data in this.sort_list" :key="data.key" >
                <input type="radio" name="crt-sort" class="crt-sort" v-model="selectedItem" :value="data" id={{data.key}} @click="emitSort(data.id)"/>
                <label for="test" id={{data.sort_name}}>{{data.sort_content}}</label>    
            </div>
        </div>
    </div>
</template>
<script>
import '@/css/charactersStyle/CharacterSortNav.css';           //css 파일
export default {
    //props
    props: {
        crtKorNames: Array
    },
    
    //data
    data() {
        return {
            sort_list: [
                {key: 1, id: "kor",     sort_name: "kor_name",     sort_content: "이름순 정렬"},
                {key: 2, id: "kor_rev", sort_name: "kor_name_rev", sort_content: "이름 역순 정렬"},
                {key: 3, id: "eng",     sort_name: "eng_name",     sort_content: "영어 이름순 정렬"},
                {key: 4, id: "eng_rev", sort_name: "eng_name_rev", sort_content: "영어 이름 역순 정렬"},
            ],
            selectedItem:   null,   
            searchVal:      null,   //검색 조건
            searchRresult:  [],     //검색 결과 이름 배열
            sortVal:        null,
        }
    },

    //mounted
    mounted() {
        this.selectedItem = this.sort_list.find(item => item.sort_name === 'kor_name')
    },

    //methos
    methods: {
        //캐릭터 정렬
        emitSort(param) {
            this.$emit('sortString', param);
        },

        //onKeyupSearch(캐릭터 검색)
        onKeyupSearch(event) {
            this.selectedItem = this.sort_list[0];  //캐릭터 정렬 순서 한글이름 순으로

            this.searchVal = event.target.value;
            this.searchRresult = this.crtKorNames.filter(data => data.indexOf(this.searchVal) > -1);

            this.$emit('resultArray', this.searchRresult);
        }
    }
}
</script>
<style>
</style>