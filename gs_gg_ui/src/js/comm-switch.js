import {reactive} from 'vue';
const swithBtn=new Map();
export const commSwitch=reactive({
    setSwitch(name,callee){
        swithBtn.set(name,callee);
    },
    on(name){
        let compo=swithBtn.get(name);
        compo.on=true;
    },
    off(name){
        let compo=swithBtn.get(name);
        compo.on=false;
    }

});