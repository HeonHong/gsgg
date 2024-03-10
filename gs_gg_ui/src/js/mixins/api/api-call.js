import axios from "axios";
import { commSwitch } from "@/js/comm-switch";


function response(data,sucFunc){
  sucFunc(data);
  commSwitch.off('LoadingBar');
}

function onFail(error,failFunc){
  failFunc(error);
  commSwitch.off('LoadingBar');
}

// function queryString(url,params){
//   let query=null;
//   let result = url;
//   if(params){
//     let keys=Object.keys(params);
//     keys.map(key=>{
//       if(!query) query = `${key}=${params[key]}`
//       else query = query+`${key}=${params[key]}`
//     })
//     result=url+'?'+query;
//   }

//   return result;
// }


export default{
  get:function getApi(url,params,success,fail){
    let param=null;
    if(!params)param={params};
    
    commSwitch.on('LoadingBar'); 
    axios.get(url,param)
    .then((data)=>response(data,success))
    .catch((error)=>onFail(error,fail));
    
    // let queryStringUrl=null;
    // queryStringUrl=queryString(url,params);   
    // axios.get(queryStringUrl)
    //   .then((data)=>response(data,success))
    //   .catch((error)=>onFail(error,fail));
  },
  post:function postApi(url,param,success,fail){
    commSwitch.on('LoadingBar');
    axios.post(url,param)
      .then((data)=>response(data,success))
      .catch((error)=>onFail(error,fail));
  },
  put:function putApi(url,param,success,fail){
    commSwitch.on('LoadingBar');
    axios.put(url,param)
      .then((data)=>response(data,success))
      .catch((error)=>onFail(error,fail));
  },
  delete:function deleteApi(url,params,success,fail){
    let param={params}; 

    commSwitch.on('LoadingBar');
    axios.delete(url,param)
      .then((data)=>response(data,success))
      .catch((error)=>onFail(error,fail));
  },
}

