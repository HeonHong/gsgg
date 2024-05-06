<!-- JoinPage -->
<template>
    <div>
        <div>
            <EpButton :width="'22.2rem'" :height="'5rem'" :color="'var(--color1)'" label="본인인증" @click="next" />
            <!-- <div v-if="level == 1"> -->
            <div class="form-container">
                <div class="form-line">
                    <!-- 중복확인 후 아이디 변경 시, 다시 중복확인 필요 -->
                    <EpInput v-model="joinData.username" placeholder="아이디를 입력하세요"></EpInput>
                    <EpButton label="중복확인" width="6rem" height="3rem" @click="checkId" />&nbsp; &nbsp; &nbsp;
                </div>
                {{ idMessage }}
                <!-- 비밀번호 정규식 확인 필요 -->
                <EpPassword v-model="joinData.password" placeholder="비밀번호를 입력하세요"></EpPassword>
                <EpPassword v-model="pwdCheck" placeholder="비밀번호를 입력하세요"></EpPassword>
                <div>{{ pwdMsg }}</div>
                <EpInput v-model="joinData.name" placeholder="이름을 입력하세요"></EpInput>
                <EpInput v-model="joinData.mobileNum" placeholder="휴대전화번호를 입력하세요"></EpInput>
                <EpInput v-model="joinData.birthday" placeholder="생년월일을 입력하세요"></EpInput>
                <!-- 비활성화 필요 -->
                <EpButton :width="'22.2rem'" :height="'5rem'" :color="'var(--color1)'" label="회원가입" @click="join" />
            </div>
        </div>
        <AlertMdl :isOn="isOn" :message="message" @cancel="cancel" @confirm="confirm"></AlertMdl>
        <!-- </div> -->
    </div>
</template>
<script>
import AlertMdl from '@/components/AlertMdl.vue';
export default {
    components: {
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
            level: 0,
            isOn: false,
            message: "본인인증 구현 불가(nice api비용처리 불가) 바로 회원가입 하시겠습니까?",
            isAvailable: 1,
            idMessage: "",
            pwdCheck: "",
        }
    },
    watch: {
        isAvailable(newVal) {
            if (newVal == 0 || newVal == "0") this.idMessage = "사용가능한 아이디입니다."
            else this.idMessage = "사용 불가능한 아이디입니다."
        }
    },
    computed: {
        pwdMsg() {
            if(this.joinData.password==""){ 
                return ""
            }else{
                if (this.pwdCheck == this.joinData.password) return "비밀번호가 일치합니다"
                else return "비밀번호가 일치하지 않습니다."
            }
        }
    },
    methods: {
        join(){
            this.postApi('/join', this.joinData, this.success, this.fail)
        },
        next() {
            this.isOn = true;
            this.level++;
        },
        success(res) {
            // alert처리 필요
            console.log(res.data);
            this.$router.push("/");
        },
        fail(err) {
            console.log(err);
        },
        confirm() {
            this.isOn = false;
        },
        cancel() {
            this.isOn = false;
            this.$router.push('/');
        },
        checkId() {
            let param = { username: this.joinData.username };
            this.getApi('/check-id', param, this.checkSuccess, this.checkFail);
        },
        checkSuccess(res) {
            this.isAvailable = res.data;
        },
        checkFail() {
            this.idMessage = '확인 중 오류가 발생하였습니다';
        },

    }
}
</script>
<style>
.form-container {
    /* display:flex; */
}

.form-line {
    display: flex;
    align-items: center;
}
</style>