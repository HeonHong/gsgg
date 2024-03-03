import { createStore } from "vuex";

export default createStore({
    state:{
        switchName:'',
    },
    mutations:{
        setSwitch(state,switchName){
            state.switchName=switchName;
        }
    },
    getters:{
        getSwitch(state){
            return state.switchName
        }
    }
});