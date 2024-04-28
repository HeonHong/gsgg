<template>
    <div>
        <EpInput :model-value="username" @update:model-value="newVal => username = newVal"></EpInput>
        <EpInput :model-value="password" @update:model-value="newVal => password = newVal"></EpInput>
        <EpButton :width="'22.2rem'" :height="'5rem'" :color="'var(--color1)'" :label="'로그인'" @click="login" />
    </div>
    <div>
        <a id="kakao-login-btn" @click="kakaoAutorize()">
            <img src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg" width="222"
                alt="카카오 로그인 버튼" />
        </a>
    </div>
    <div>{{ response }}</div>
</template>
<script>



export default {
    data() {
        return {
            username: "",
            password: "",
            response: ""
        }
    },
    methods: {
        kakaoAutorize() {
            window.Kakao.Auth.authorize({
                redirectUri: 'http://localhost:3000/loginpage',
                // redirectUri: 'http://localhost:8080/login/kakao/auth',
            });
        },
        getKakaoToken(code) {
            this.postApi('/login/kakao/auth', code, this.kakaoLoginSuccess, this.fail);
        },
        kakaoLoginSuccess(res) {
            //localStorage 저장 시, 문자열들로 저장됨.
            //res.data 그대로 저장하면 [object Object]로 저장되니까 조심할 것!
            localStorage.setItem('kakaoInfo', JSON.stringify(res.data));
            this.$router.push({ path: '/' });
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
            console.log(res);
            this.$store.commit('setUserToken', res.data);
            console.log(this.$store.state.userToken);
            // this.getApi('/admin', null, this.success, this.fail);
            this.$router.push({path:'/'});
        },
        success(res) {
            console.log("admin 다녀옴 ", res.data);
        },
        fail(err) {
            console.log(err.message);
        },
    },
    mounted() {
        // if (window.location.search.includes('code')) {
        //     const urlParams = new URLSearchParams(window.location.search);
        //     const code = urlParams.get('code');
        //     this.getKakaoToken(code);
        // }
        // localStorage.removeItem('kakaoInfo');

    }
}
</script>
<style></style>