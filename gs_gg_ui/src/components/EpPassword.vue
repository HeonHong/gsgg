<template>
    <div class="input-form-container" :class="sizeAdjustment">
        <div class="input-form-label" v-if="hasLabel">
            <span>{{label}}</span>
        </div>
        <div class="input-form">
            <input v-if="isPwd" class="og-input-form" :value="modelValue" @input="updVal" type="password" :placeholder="placeholder">
            <input v-else class="og-input-form" :value="modelValue" @input="updVal" type="text" :placeholder="placeholder">
        </div>   
        <img class="og-pwd-img" src="@/assets/eye.png" @click="showPwd"/>
    </div>
</template>
<script>
export default {
    //공통컴포넌트만 리스트형으로 받기
    props:['label','size','modelValue','placeholder'],
    emits:['update:modelValue'],
    data(){
        return{
            isPwd:true,
        }
    },
    computed:{
        hasLabel(){
            return (this.label == null || this.label == '') ? false:true;
        },
        sizeAdjustment(){
            if(!this.hasLabel) return (this.size == null || this.size == '') ? "size-1":"size-"+(this.size);
            else return (this.size == null || this.size == '') ? "size-2":"size-"+(this.size);
        }

    },
    methods:{
        updVal(e){
            this.$emit('update:modelValue',e.target.value);
        },
        showPwd(){
            this.isPwd=!this.isPwd;
        }
    }
}
</script>
<style>
</style>