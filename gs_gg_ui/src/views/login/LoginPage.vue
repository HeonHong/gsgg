<template>
    <div>
        <div>
            <EpInput :model-value="username" @update:model-value="newVal => username = newVal"></EpInput>
            <EpInput :model-value="password" @update:model-value="newVal => password = newVal"></EpInput>
            <EpButton :width="'22.2rem'" :height="'5rem'" :color="'var(--color1)'" label="로그인" @click="login" />
        </div>
        <div>
            <a id="kakao-login-btn" @click="kakaoAutorize()">
                <img src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg" width="222"
                    alt="카카오 로그인 버튼" />
            </a>
        </div>
        <AlertMdl :isOn="isOn" :message="message" @confirm = "confirm" @cancel="cancel"></AlertMdl>


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
            username: "",
            password: "",
            response: "",
            isOn: false,
            message: ""

        }
    },
    methods: {
        kakaoAutorize() {
            window.Kakao.Auth.authorize({
                redirectUri: 'http://localhost:3000/login',
                // redirectUri: 'http://localhost:8080/login/kakao/auth',
            });
        },
        getKakaoToken(code) {
            this.postApi('/kakao/auth', code, this.kakaoLoginSuccess, this.kakaoLoginFail);
        },
        kakaoLoginSuccess(res) {
            //localStorage 저장 시, 문자열들로 저장됨.
            //res.data 그대로 저장하면 [object Object]로 저장되니까 조심할 것!
            // localStorage.setItem('kakaoInfo', JSON.stringify(res.data));
            console.log("res.data", res);
            if (res.data == "") {
                this.isOn = true;
                this.message = "회원이 아닙니다. 회원가입 하시겠습니까?"
            } else {
                this.loginSuccess(res);
            }
        },
        kakaoLoginFail(err) {
            console.log("err" + err);
        },
        confirm() {
            console.log("여기 아닌가?");
            this.isOn=false;
            this.$router.push('/join');
        },
        cancel(){
            this.isOn=false;
            this.$router.push('/');
        },
        login() {
            //임시아이디 honey1234
            //임시비밀번호 1234

            //로그인할 경우 무조건 formData형식으로 보내야 함.
            let formData = new FormData();
            formData.append('username', this.username);
            formData.append('password', this.password);
            //formData형식 헤더 axios사용
            this.postFApi('/login', formData, this.loginSuccess, this.fail);
        },
        loginSuccess(res) {
            this.$store.commit('setUserToken', res.data);
            console.log(this.$store.state.userToken);
            // this.getApi('/admin', null, this.success, this.fail);
            this.$router.push({ path: '/' });
        },
        success(res) {
            console.log("admin 다녀옴 ", res.data);
        },
        fail(err) {
            console.log(err.message);
        },
    },
    mounted() {
        if (window.location.search.includes('code')) {
            console.log("들어왔냐?");
            const urlParams = new URLSearchParams(window.location.search);
            const code = urlParams.get('code');
            this.getKakaoToken(code);
        }
        // localStorage.removeItem('kakaoInfo');

    }
}
</script>
<style></style>