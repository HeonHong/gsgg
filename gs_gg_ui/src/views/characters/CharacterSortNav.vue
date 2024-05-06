<template lang="">
    <div id="window">
        <div id="search-div">
            <input type="text" id="search-input" placeholder="챔피언 이름 검색(가렌, ㄱㄹ)"/>
        </div>
        <div id="sort-div">
            <div class="sort-box" v-for="data in this.sort_list" :key="data.key">
                <input type="radio" name="crt-sort" class="crt-sort" v-model="selectedItem" :value="data" id={{data.key}} @click="testconsole(data.sort_name)"/>
                <label for="test" id={{data.sort_name}}>{{data.sort_content}}</label>    
            </div>
        </div>
    </div>
</template>
<script>
import '@/css/charactersStyle/CharacterSortNav.css';           //css 파일
export default {
    //data
    data() {
        return {
            sort_list: [
                {key: 1, sort_name: "kor_name",     sort_content: "이름순 정렬"},
                {key: 2, sort_name: "kor_name_rev", sort_content: "이름 역순 정렬"},
                {key: 3, sort_name: "eng_name",     sort_content: "영어 이름순 정렬"},
                {key: 4, sort_name: "eng_name_rev", sort_content: "영어 이름 역순 정렬"},
            ],
            selectedItem: null,
        }
    },

    //mounted
    mounted() {
        this.selectedItem = this.sort_list.find(item => item.sort_name === 'kor_name')
    },

    //methos
    methods: {
        testconsole(param) {
            console.log(param)
        }
    }
}
</script>
<style>
</style>