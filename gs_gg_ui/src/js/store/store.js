import { createStore } from "vuex";

export const store = createStore({
    state: {
        //로그인용 JWT토큰
        userToken: null,
    },
    mutations: {
        setUserToken(state, token) {
            state.userToken = token;
        },
        removeUserToken(state) {
            state.userToken = null;
        }
    },
    getters: {

    }
});

