//apicall을 굳이 만든 이유.
//여기서 함수,변수를 여러가지 만들어서 커스터마이징해도 되지만
//mixins특성상 vue파일에 import되었을 때 충돌이 나서 제대로 작동하지 않을 수 있다. 
export default{
    methods:{
        getApi(url,param,success,fail){
            this.$apiCall.get(url,param,success,fail);
        },
        postApi(url,param,success,fail){
            this.$apiCall.post(url,param,success,fail);
        },
        putApi(url,param,success,fail){
            this.$apiCall.put(url,param,success,fail);
        },
        deleteApi(url,param,success,fail){
            this.$apiCall.delete(url,param,success,fail);
        },
        postFApi(url,param,success,fail){
            this.$apiCall.postF(url,param,success,fail);
        },
    }
}
