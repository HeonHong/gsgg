<template lang="">
    <div>
        <button @click="btnClick" class="common-btn">{{this.label}}</button>
    </div>
</template>
<script>
import commonUtils from "../js/common-utils"
export default {
    props: ['width', 'height', 'color', 'label'],
    data(){
        return{
            stWidth:null,
            stHeight:null,
        }
    },
    methods:{
        btnClick(){
            this.$emit('btnClick');
        }
    },
    mounted() {
        //width
        if(!commonUtils.isNull(this.width)) {
            this.stWidth = this.width + 'px';
            this.$el.style.setProperty('--dynamic-width', this.stWidth);
        }

        //height
        if(!commonUtils.isNull(this.height)) {
            this.stHeight = this.height + 'px';
            this.$el.style.setProperty('--dynamic-height', this.stHeight);
        }

        //color
        if(!commonUtils.isNull(this.color)) {
            this.$el.style.setProperty('--dynamic-color', this.color);
        }
    }
}
</script>
<style>
    .common-btn{
        width: var(--dynamic-width);
        height: var(--dynamic-height);
        background: var(--dynamic-color);
        font-family: var(--main-font);
    }
</style>