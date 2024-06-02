import { createStore } from "vuex";
import profileIcon from '@/assets/profileIcon.jpg';

export const store = createStore({
    state: {
        //로그인용 JWT토큰
        userToken: null,
        //ID
        username: null,
        // profileImg : '@/assets/profileIcon.jpg' 사용 불가
        // @은 webpack의 alias임
        // vuex store는 단순 자바스크립트 객체 webpack alias 인식 불가
        //src/assets/profileIcon.jpg 로 해도 단순 문자열로 인식
        profileImg : profileIcon,

    },
    mutations: {
        setUserToken(state, token) {
            state.userToken = token;
        },
        removeUserToken(state) {
            state.userToken = null;
        },
        setUsername(state, username) {
            state.username = username;
        }
    },
    getters: {

    }
});

