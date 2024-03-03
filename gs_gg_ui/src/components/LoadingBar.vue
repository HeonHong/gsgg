<template>
<div class="loading-bar-container" v-show="on">
    <div class="loading-bar-shape"></div>
    <div class="loading-bar-text">loading...</div>
</div>
</template>
<script>
export default {
    name:"LoadingBar",
    data(){
        return{
            
        }
    },
    computed:{
        on(){
            console.log('로딩바',this.$store.getters.getSwitch);
            if(this.$store.getters.getSwitch==this.$options.name) return true
            else return false;
        }
    },
    created(){
    }
}

</script>
<style>
</style>