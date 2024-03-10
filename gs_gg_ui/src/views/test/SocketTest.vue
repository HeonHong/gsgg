<template>
    <div id="app">
        <div>{{liveNum}}</div>
        유저이름:
        <input v-model="userName" type="text">
        내용: <input v-model="message" type="text" @keyup="sendMessage">
        <div v-for="(item, idx) in recvList" :key="idx">
            <h3>유저이름: {{ item.userName }}</h3>
            <h3>내용: {{ item.content }}</h3>
        </div>
    </div>
</template>

<script>
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
    name: 'App',
    data() {
        return {
            userName: "",
            message: "",
            recvList: [],
            liveNum:1,
        }
    },
    created() {
        this.connect()
    },
    methods: {
        connect() {
            const serverURL = process.env.VUE_APP_API_BASE_URL;
            let socket = new SockJS(serverURL);
            this.stompClient = Stomp.over(socket);
            // console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
            this.stompClient.connect(
                {},
                frame => {
                    this.connected = true;
                    console.log('소켓 연결 성공', frame);
                    this.stompClient.subscribe("/send", res => {
                        console.log('구독으로 받은 메시지 입니다.', res.body);
                        this.recvList.push(JSON.parse(res.body))
                    });
                    this.stompClient.subscribe("/liveCheck", res => {
                        console.log('구독으로 받은 메시지 입니다.', res.body);
                        this.liveNum=res.body;
                    });
                },
                error => {
                    console.log('소켓 연결 실패', error);
                    this.connected = false;
                }
            );
        },
        sendMessage(e) {
            if (e.keyCode === 13 && this.userName !== '' && this.message !== '') {
                this.send()
                this.message = ''
            }
        },
        send() {
            console.log("Send message:" + this.message);
            if (this.stompClient && this.stompClient.connected) {
                const msg = {
                    userName: this.userName,
                    content: this.message
                };
                this.stompClient.send("/receive", JSON.stringify(msg), {});
            }
        },
        liveCheck(liveNum){
          if (this.stompClient && this.stompClient.connected) {
                
                this.stompClient.send("/sendLiveCheck", liveNum, {});
            }
        }
    },
    mounted(){
      this.liveCheck(++this.liveNum);
    }
}
</script>