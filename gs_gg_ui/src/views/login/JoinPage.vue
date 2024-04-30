<template>
    <div>
        <div>
            <EpInput v-model="username" placeholder="아이디를 입력하세요" ></EpInput>
            <EpInput v-model="password" placeholder="비밀번호를 입력하세요" ></EpInput>
            <EpInput v-model="name" placeholder="이름을 입력하세요" ></EpInput>
            <EpSelectBox :options="mobileCompany"></EpSelectBox>
            <EpInput v-model="mobileNum" placeholder="휴대전화번호를 입력하세요" ></EpInput>
            <EpInput v-model="birthday" placeholder="생년월일을 입력하세요" ></EpInput>
            <EpButton :width="'22.2rem'" :height="'5rem'" :color="'var(--color1)'" :label="'본인인증'" @click="next"></EpButton>
        </div>
    </div>
</template>
<script>
export default {
    data(){
        return{
            name:'',
            username:'',
            birthday : '',
            mobileNum:'',
            mobileCompany:{
                option1:{option:"SKT", key:"SKT"},
                option2:{option:"LG", key:"LG"},
                option3:{option:"KT", key:"KT"},
            },
            password:'',
            // role:'client'

        }
    },
    methods:{
        next(){
            this.level++;
        },
    }
}
</script>
<style lang="">
    
</style>