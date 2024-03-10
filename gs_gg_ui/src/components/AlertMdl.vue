<template>
    <div class="alert-modal-container" v-show="on">
        <div class="alert-modal-window">
            <div class="alert-modal-message">메세지 들어갈 공간</div>
            <div class="alert-modal-btns">
                <input type="button" value="취소" @click="confirm()"/>
                <input type="button" value="확인" @click="confirm()"/>
            </div>
        </div>
    </div>
    </template>
    <script>
    import { commSwitch } from '@/js/comm-switch';
    export default {
        name:"AlertMdl",
        data(){
            return{
                on:false,
            }
        },
        created(){
            commSwitch.setSwitch('AlertMdl',this)
        },
        methods:{
            confirm(){
                this.on=false
            }
        }
    }
    
    </script>
    <style>
.alert-modal-container{
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw; /* 현재 뷰포트의 너비 */
    height: 100vh; /* 현재 뷰포트의 높이 */
    background-color: rgba(0, 0, 0, 0.3);
    display: flex;
    justify-content: center;
    align-items: center;
   
}
.alert-modal-window{
    /* position: absolute;
    top: 40%;
    left: 50%; */
    width: 50rem;
    height: 30rem;
    background-color: white;
    display: grid;
    grid-template-columns: 1fr 1fr 1fr 1fr;
    grid-template-rows: 1fr 1fr 1fr 1fr;
    border-radius: 5%;
}
.alert-modal-message{
    grid-column-start: 2;
    grid-column-end: 4;
    grid-row-start: 2;
    grid-row-end: 3;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 2rem;
}
.alert-modal-btns{
    grid-column-start: 2;
    grid-column-end: 4;
    grid-row-start: 4;
    grid-row-end: 5;
    display: flex;
    align-items: center;
    justify-content: center;
}
    </style>