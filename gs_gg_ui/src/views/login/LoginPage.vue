<template>
    <div>
        <div>
            <EpInput :model-value="username" label="아이디" @update:model-value="newVal => username = newVal"></EpInput>
            <EpPassword :model-value="password" label="비밀번호" @update:model-value="newVal => password = newVal" />
            <div>{{ validMsg }}</div>
            <input type="button" value="아이디 찾기">
            <input type="button" value="비밀번호 찾기">
            <EpButton :width="'22.2rem'" :height="'5rem'" :color="'var(--color1)'" label="로그인" @click="login" />
        </div>
        <div>
            <a id="kakao-login-btn" @click="kakaoAutorize()">
                <img src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg" width="222"
                    alt="카카오 로그인 버튼" />
            </a>
        </div>
        <AlertMdl :isOn="isOn" :message="message" @confirm="confirm" @cancel="cancel"></AlertMdl>


    </div>
</template>
<script>
import AlertMdl from '@/components/AlertMdl.vue';

export default {
    name: 'LoginPage',
    components: {
        AlertMdl
    },
    data() {
        return {
            username: "",
            password: "",
            response: "",
            isOn: false,
            message: "",
            validMsg: ""
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
            this.postApi('/kakao/auth', code, this.kakaoLoginSuccess, this.fail);
        },
        kakaoLoginSuccess(res) {
            //localStorage 저장 시, 문자열들로 저장됨.
            //res.data 그대로 저장하면 [object Object]로 저장되니까 조심할 것!
            // localStorage.setItem('kakaoInfo', JSON.stringify(res.data));
            this.loginSuccess(res);
            
        },
        confirm() {
            this.isOn = false;
            this.$router.push('/join');
        },
        cancel() {
            this.isOn = false;
            this.$router.push('/');
        },
        login() {
            //임시아이디 honey1234
            //임시비밀번호 1234

            //로그인할 경우 무조건 formData형식으로 보내야 함.
            let formData = new FormData();
            if (this.username == '' || this.password == '') {
                this.validMsg = "아이디, 비밀번호는 필수값입니다."
                return
            }
            formData.append('username', this.username);
            formData.append('password', this.password);
            //formData형식 헤더 axios사용
            this.postFApi('/login', formData, this.loginSuccess, this.fail);
        },
        loginSuccess(res) {
            let token = res.data;
            this.$store.commit('setUserToken', token);

            let payload = this.parseJWT(token);
            this.$store.commit('setUsername', payload.username);
            //this.$router.push({ path: '/' });
        },
        parseJWT(token) {
            //https://stackoverflow.com/questions/38552003/how-to-decode-jwt-token-in-javascript-without-using-a-library

            let base64Url = token.split('.')[1];
            let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            //무슨 차이인지 확인하기
            // JSON.parse(decodeURIComponent(window.atob(token)))         
            let jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function (c) {
                return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
            }).join(''));
            return JSON.parse(jsonPayload);
        },
        fail(err) {
            if(err?.code=="ERR_BAD_REQUEST"){
                this.isOn = true;
                this.message = err.response.data.message;
            }else{
                this.validMsg = err.response.data.message;
            }
        },
    },
    mounted() {
        if (window.location.search.includes('code')) {
            const urlParams = new URLSearchParams(window.location.search);
            const code = urlParams.get('code');
            this.getKakaoToken(code);
        }
        // localStorage.removeItem('kakaoInfo');

    }
}
</script>
<style></style>