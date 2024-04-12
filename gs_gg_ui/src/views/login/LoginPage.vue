<template>
    <div>
        <a id="kakao-login-btn" @click="kakaoAutorize()">
            <img src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg" width="222"
                alt="카카오 로그인 버튼" />
        </a>
    </div>
</template>
<script>


export default {
    methods: {
        kakaoAutorize() {
            window.Kakao.Auth.authorize({
                redirectUri: 'http://localhost:3000/loginpage',
                // redirectUri: 'http://localhost:8080/login/kakao/auth',
            });
        },
        getKakaoToken(code){
            this.postApi('/login/kakao/auth',code,this.succeedLogin,this.fail);
        },
        succeedLogin(res){
            console.log("data안옴?",res.data);
            this.$router.push({path:'/', params:res.data.id});
        },
        fail(err){
            console.log(err.message);
        },
    },
    mounted(){
        if(window.location.search.includes('code')){
            const urlParams = new URLSearchParams(window.location.search);
            const code = urlParams.get('code');
            console.log(code);

            this.getKakaoToken(code);
        }
    }
}
</script>
<style></style>