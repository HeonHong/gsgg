// comm-switch.js
import {reactive} from 'vue';
let caller=null;
const swithBtn=new Map();
export const commSwitch=reactive({
    setSwitch(name,callee){
        swithBtn.set(name,callee);
    },
    on(name,callerr){
        let compo=swithBtn.get(name);
        compo.on=true;
        caller=callerr;
    },
    off(name){
        let compo=swithBtn.get(name);
        compo.on=false;
    },
    getCaller(){
        return caller;
    }

});