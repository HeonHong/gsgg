<template>
    <div class="test">
        test component
    </div>
</template>
<script>
import commonUtils from "@/js/common-utils.js"
export default {
    mounted(){
        
        //공통함수js 예시
        commonUtils.checkLog("페이지 시작 테스트");

        //mixin 예시
        let param={test:'테스트'};
        this.getApi('/api',param,this.success,this.fail);
        this.postApi('/post',param,this.success,this.fail);
        this.putApi('/putTest',param,this.success,this.fail);
        this.deleteApi('/delTest',param,this.success,this.fail);
        
    },
    data(){
        return{
        }
    },
    methods:{
        success(data){
            console.log(data);
        },
        fail(err){
            console.log(err.message);
        }
    },
    watch:{
    }

}
</script>
<style>
</style>