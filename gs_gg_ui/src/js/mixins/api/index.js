import axios from "axios";
import { store } from "@/js/store/store";
//https://axios-http.com/kr/docs/interceptors

// 요청 인터셉터 추가하기
function setInterceptor(instance) {
    // 요청이 전달되기 전에 작업 수행
    instance.interceptors.request.use(function (config) {
        console.log("store.state.userToken", store.state.userToken);
        if (store.state.userToken != null)
            config.headers.Authorization = store.state.userToken;
        return config;
    });
    return instance;
}

function getAxiosInstance() {
    const instance = axios.create({
        baseURL: process.env.VUE_APP_API_BASE_URL,
        headers: {
            "Content-Type": "application/json;charset=utf-8",
        },
    });
    return setInterceptor(instance);
}
//formData용 인스턴스
function getAxiosFormInstance() {
    const instance = axios.create({
        baseURL: process.env.VUE_APP_API_BASE_URL,
        headers: {
            "Content-Type": "multipart/form-data",
        },
    });
    return setInterceptor(instance);
}
export const axiosInstance = getAxiosInstance();
export const axiosFormInstance = getAxiosFormInstance();

