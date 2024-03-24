<template lang="">
    <div class="out-div">
        캐릭터 소개 페이지
        <div>
            <CharacterBox/>


        </div>
    </div>
</template>
<script>
import CharacterBox from './CharacterBox.vue';
// import apiCall from '@/js/mixins/api/api-call';
export default {
    //mounted
    mounted() {
        // let url = "/api/test";  //연결 url
        // let param = "key"       //api 사용 param

        // //api 성공시 함수
        // let success = () => {
        //     console.log('success');
        // };

        // //api 실패시 함수
        // let fail = () => {
        //     console.log('fail')
        // }

        // apiCall.get(url, param, success, fail);
    },

    //components
    components: {
        CharacterBox: CharacterBox,
    },
}
</script>
<style>
.out-div{
    padding: 20px;
}

</style>