export default{
    methods:{
        getApi(url,param,success,fail){
            this.$store.commit('setSwitch','LoadingBar');
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
    }
}