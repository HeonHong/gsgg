<template>
    <div>
        <div>
            <EpInput v-model="joinData.username" placeholder="아이디를 입력하세요"></EpInput>
            <EpInput v-model="joinData.password" placeholder="비밀번호를 입력하세요"></EpInput>
            <EpInput v-model="joinData.name" placeholder="이름을 입력하세요"></EpInput>
            <!-- <EpSelectBox :options="joinData.mobileCompany"></EpSelectBox> -->
            <EpInput v-model="joinData.mobileNum" placeholder="휴대전화번호를 입력하세요"></EpInput>
            <EpInput v-model="joinData.birthday" placeholder="생년월일을 입력하세요"></EpInput>
            <EpButton :width="'22.2rem'" :height="'5rem'" :color="'var(--color1)'" :label="'본인인증'" @click="next">
            </EpButton>
            <AlertMdl :isOn="isOn" :message="message"></AlertMdl>
        </div>
    </div>
</template>
<script>
import AlertMdl from '@/components/AlertMdl.vue';
export default {
    component: {
        AlertMdl
    },
    data() {
        return {
            joinData: {
                name: '',
                username: '',
                birthday: '',
                mobileNum: '',
                // mobileCompany: {
                //     option1: { option: "SKT", key: "SKT" },
                //     option2: { option: "LG", key: "LG" },
                //     option3: { option: "KT", key: "KT" },
                // },
                password: '',
                // role:'client'
            },

            isOn: false,
            message: "본인인증 구현 안함 바로 회원가입 하시겠습니까?"

        }
    },
    methods: {
        next() {
            // this.level++;
            // this.isOn=true;
            this.postApi('/join',this.joinData,this.success,this.fail)
        },
        success(res){
            // alert처리 필요
            console.log(res.data);
            this.$router.push("/");
        },
        fail(err){
            console.log(err);
        }
    }
}
</script>
<style lang="">

</style>