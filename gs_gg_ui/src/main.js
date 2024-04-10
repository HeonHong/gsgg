import { createApp, defineAsyncComponent } from 'vue'
import App from './App.vue'
import router from "@/js/router"
import axios from 'axios';
import apiCall from './js/mixins/api/api-call';
import './js/total-css';
import apiMix from "@/js/mixins/api/api-mix.js"
import store from './js/store/store';

const app = createApp(App);
const COMMON_COMPONENTS=['EpInput','EpButton'];

function loadComponents(){

    for (let component of COMMON_COMPONENTS){
        app.component(
          component,
          //동적import
          defineAsyncComponent(() => import(`@/components/${component}.vue`))
        );
    }
}

//라이브러리 전역 변수 등록
app.config.globalProperties.$axios=axios;
app.config.globalProperties.$apiCall=apiCall;
app.mixin(apiMix);

loadComponents();
app.use(store);
app.use(router);
app.mount('#app')

