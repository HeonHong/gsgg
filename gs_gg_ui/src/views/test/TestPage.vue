<template>
    <div class="test">
        <div class="test-first-grid">
            Sample css<br>
            Grid        : 4분의 4 기준<br>
            Color       : --color2 사용<br>
            FontSize    : 2rem 적용<br>
            FontFamily  :--sub-font 사용
        </div>
        <div class="grid-default">
            로딩 기능 샘플
            <EpButton :width="'7rem'" :height="'4rem'" :color="'var(--color1)'" :label="'로딩 3초'" @click="loading"/>
        </div>
        <div class="grid-default">
            alert 모달 샘플(구현중)
            <EpButton :width="'7rem'" :height="'4rem'" :color="'var(--color2)'" :fontColor="'var(--color1)'" :label="'Alert 모달'" @btnClick="alert"/>
        </div>
        <div>

            <EpButton :width="'7rem'" :height="'4rem'" :color="'var(--color3)'" :label="'common button'" @btnClick="test"/>
        </div>
        <div>

            <EpButton :width="'7rem'" :height="'4rem'" :color="'var(--color4)'" :label="'common button'" @btnClick="test"/>
        </div>
        <div>

            <EpButton :width="'7rem'" :height="'4rem'" :color="'var(--color5)'" :label="'common button'" @btnClick="test"/>
        </div>
        <EpButton :width="'10rem'" :height="'10rem'" :color="'var(--color2)'" :label="'common button'" @btnClick="test"/>
        <EpButton :width="'10rem'" :height="'10rem'" :color="'var(--color3)'" :label="'common button'" @btnClick="test"/>
        <EpButton :width="'10rem'" :height="'10rem'"  :label="'common button'" @btnClick="test"/>

    </div>
</template>
<script>
import commonUtils from "@/js/common-utils.js"
import EpButton from '@/components/EpButton.vue';
import { commSwitch } from '@/js/comm-switch';


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
    components:{
        EpButton
    },
    methods:{
        success(data){
            console.log(data);
        },
        fail(err){
            console.log(err.message);
        },
        loading(){
            //on안에 철자 정확히 해야함
            commSwitch.on('LoadingBar');
            setTimeout(()=>commSwitch.off('LoadingBar'),3000)
        },
        alert(){
            commSwitch.on('AlertMdl');

        }
    },
    watch:{
    }

}
</script>
<style>

.test {
    /* background-color: var(--color1); */
    display: grid;
    grid-template-columns: 1fr 1fr 1fr 1fr;
    /* grid-template-columns: repeat(4,25%); */
    grid-auto-flow: row;
    row-gap:2rem
}
.test-first-grid{
    grid-column-start: 1;
	grid-column-end: 5;
    font-size:2rem;
    background-color: var(--color2);
    font-family: var(--sub-font);

}
.grid-default{
    font-size: 2rem;
}
</style>