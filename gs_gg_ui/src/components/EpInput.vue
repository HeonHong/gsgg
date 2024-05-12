<template>
    <div class="input-form-container" :class="sizeAdjustment">
        <div class="input-form-label" v-if="isLabel">
            <span>{{label}}</span>
        </div>
        <div class="input-form">
            <input class="og-input-form" :value="modelValue" @input="update" type="text" :placeholder="placeholder" :readonly="readonly">
        </div>   
    </div>
</template>
<script>
export default {
    //공통컴포넌트만 리스트형으로 받기
    props:['label','size','modelValue','placeholder','readonly'],
    emits:['update:modelValue'],
    data(){
        return{

        }
    },
    computed:{
        isLabel(){
            return (this.label == null || this.label == '') ? false:true;
        },
        sizeAdjustment(){
            if(!this.isLabel) return (this.size == null || this.size == '') ? "size-1":"size-"+(this.size);
            else return (this.size == null || this.size == '') ? "size-2":"size-"+(this.size);
        }

    },
    methods:{
        update(e){
            this.$emit('update:modelValue',e.target.value);
        }
    }
}
</script>
<style>
</style>