import axios from "axios";

export default{
  get:function getApi(url,params,success,fail){ 
    let param={params}     
    axios.get(url,param)
      .then(success)
      .catch(fail);
  },
  post:function postApi(url,param,success,fail){
    axios.post(url,param)
      .then(success)
      .catch(fail);
  },
  put:function putApi(url,param,success,fail){
    axios.put(url,param)
      .then(success)
      .catch(fail);
  },
  delete:function deleteApi(url,params,success,fail){
    let param={params}     
    axios.delete(url,param)
      .then(success)
      .catch(fail);
  },
}

