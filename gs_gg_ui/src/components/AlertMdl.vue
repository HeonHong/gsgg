<!-- AlertMdl.vue -->
<template>
    <div class="alert-modal-container" v-show="isOn" @click="cancel">
        <div class="alert-modal-window">
            <div class="alert-modal-message">{{ message }}</div>
            <div class="alert-modal-btns">
                <EpButton label="취소" width="5rem" height="3.5rem" @click="cancel" />&nbsp; &nbsp; &nbsp;
                <EpButton label="확인" width="5rem" height="3.5rem" @click="confirm" />
            </div>
        </div>
    </div>
</template>
<script>
// import { commSwitch } from '@/js/comm-switch';
export default {
    name: "AlertMdl",
    props:['message','isOn'],
    data() {
        return {
            
        }
    },
    created() {
        // commSwitch.setSwitch('AlertMdl', this)
    },
    methods: {
        confirm() {
            this.$emit('confirm');
        },
        cancel() {
            this.$emit('cancel');
        }
    }
}

</script>
<style>
.alert-modal-container {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    /* 현재 뷰포트의 너비 */
    height: 100vh;
    /* 현재 뷰포트의 높이 */
    background-color: rgba(0, 0, 0, 0.3);
    display: flex;
    justify-content: center;
    align-items: center;

}

.alert-modal-window {
    /* position: absolute;
    top: 40%;
    left: 50%; */
    width: 45rem;
    height: 25rem;
    background-color: var(--color6);
    display: grid;
    grid-template-rows: repeat(5,1fr);
    grid-template-columns: repeat(6,1fr);
    border-radius: 3rem;
   
}
.alert-modal-message {
    grid-column-start: 2;
    grid-column-end: 6;
    grid-row-start: 2;
    grid-row-end: 4;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 1.5rem;
    border-top: 0.1rem dotted var(--color7);
    border-bottom: 0.1rem dotted var(--color7);
}

.alert-modal-btns {
    grid-column-start: 3;
    grid-column-end: 5;
    grid-row-start: 5;
    grid-row-end: 6;
    display: flex;
    align-items: center;
    justify-content: center;
}
</style>