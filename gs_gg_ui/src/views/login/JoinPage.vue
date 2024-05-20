<!-- JoinPage -->
<template>
    <div>
        <div>
            <div v-if="level == 0">
                <EpInput v-model="joinData.name" label="이름"></EpInput>
                <EpInput v-model="joinData.mobileNum" label="휴대폰번호"></EpInput>
                <EpInput v-model="joinData.birthday" label="생년월일" placeholder="생년월일 8자리"></EpInput>
                {{ validMsg }}
                <EpButton :width="'22.2rem'" :height="'5rem'" :color="'var(--color1)'" label="본인인증"
                    @click="identityCheck" />
            </div>
            <div v-if="level == 1">
                <div class="form-container">
                    <EpInput v-model="joinData.name" label="이름" readonly="true"></EpInput>
                    <EpInput v-model="joinData.mobileNum" label="휴대폰번호" readonly="true"></EpInput>
                    <EpInput v-model="joinData.birthday" label="생년월일" readonly="true"></EpInput>
                    <div class="form-line">
                        <!-- 중복확인 후 아이디 변경 시, 다시 중복확인 필요 -->
                        <EpInput v-model="joinData.username" label="아이디"></EpInput>
                        <EpButton label="중복확인" width="6rem" height="3rem" @click="userNameCheck" />
                        &nbsp; &nbsp; &nbsp;
                    </div>
                    <!-- 비밀번호 정규식 확인 필요 -->
                    <EpPassword v-model="joinData.password" label="비밀번호"></EpPassword>
                    <EpPassword v-model="pwdCheck" label="중복확인"></EpPassword>
                    <div>{{ validMsg }}</div>
                    <!-- 비활성화 필요 -->
                    <EpButton :width="'22.2rem'" :height="'5rem'" :color="'var(--color1)'" label="회원가입" @click="join" />
                </div>
            </div>
            <AlertMdl type ="normal" :isOn="isOn" :message="message" @cancel="cancel" @confirm="confirm"></AlertMdl>
        </div>
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
            userCnt: -1,//response값에 따라 재랜더링이 필요하기 때문에 -1로 설정
            pwdCheck: "",
            validMsg: "",
            isJoined:false,
        }
    },
    watch: {
        userCnt(newVal) {
            if (newVal == 0 || newVal == "0") {
                this.validMsg = "사용가능한 아이디입니다."
            } else if (newVal > 0) {
                this.validMsg = "사용 불가능한 아이디입니다.";
            } else if (newVal == -1) {
                this.validMsg = ""
            }
        },
        'joinData.username'() {
            this.userCnt = -1;
        }
    },
    computed: {

    },
    methods: {
        identityCheck() {
            let mbRegex = /^[0-9]{11}$/g
            let birthRegex = /^[0-9]{8}$/g
            
            if (this.joinData.name == "") {
                this.validMsg = "이름은 필수값입니다."
                return;
            } else if (this.joinData.mobileNum == "") {
                this.validMsg = "휴대폰번호는 필수값입니다."
                return;
            } else if (this.joinData.birthday == ""){
                this.validMsg = "생년월일은 필수 값입니다."
                return;
            }
            
            if (!this.joinData.mobileNum.match(mbRegex)) {
                this.validMsg = "휴대폰번호는 11자리입니다."
                return
            } else if (!this.joinData.birthday.match(birthRegex)) {
                this.validMsg = "생년월일은 YYYYMMDD형식입니다."
                return
            }
            
            this.next();
        },
        join() {
            if (this.userCnt != 0) {
                this.validMsg = "중복확인을 해주세요"
                return
            }
            if (!this.pwdVal(this.joinData.password, this.pwdCheck)) return;
            this.postApi('/join', this.joinData, this.success, this.fail)
        },
        userNameVal(username) {
            let regex = /^(?=.*[a-z])(?=.*[0-9])\w{8,16}$/g;
            let regCheck = username.match(regex);
            if (regCheck) return true;

            this.validMsg = "아이디는 소문자,숫자를 포함한 8글자 이상 16글자 이하로 가능합니다."
            return false;
        },
        pwdVal(ogPwd, pwdCheck) {
            let regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()])[A-Za-z\d@$!%*#?&()]{8,16}$/g;
            let regCheck = ogPwd.match(regex);
            if (regCheck && pwdCheck == ogPwd) {
                this.validMsg = ""
                return true;
            }
            if (ogPwd == "") this.validMsg = "비밀번호를 입력해주세요.";
            else if (!regCheck && ogPwd != '') this.validMsg = "비밀번호는 영문 대소문자, 숫자를 포함한 8자리 이상 16자 이하입니다.";
            else if (pwdCheck != ogPwd) this.validMsg = "비밀번호가 다릅니다.";
            return false;
        },
        userNameCheck() {
            if (this.userNameVal(this.joinData.username)) {
                let param = { username: this.joinData.username };
                this.getApi('/check-id', param, this.checkSuccess, this.checkFail);
            }
        },
        next() {
            this.isOn = true;
            this.level++;
        },
        success(res) {
            this.isOn =true;
            this.message=res.data;
            this.isJoined=true;
        },
        fail(err) {
            console.log(err);
        },
        confirm() {
            this.isOn = false;
            if(this.isJoined)this.$router.push("/");
        },
        cancel() {
            this.isOn = false;
            this.$router.push('/');
        },

        checkSuccess(res) {
            this.userCnt = res.data;
        },
        checkFail() {
            this.validMsg = '확인 중 오류가 발생하였습니다';
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