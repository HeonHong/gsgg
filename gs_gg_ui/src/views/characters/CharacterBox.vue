<template lang="">
  <div class="window-div">
    <div class="outine-div">
      <div class="portrait-div">

      </div>
      <div id="crt-desc-div">
        <div id="crt-name">character name</div>
        <div id="crt-main-line">main line: top, mid. bottom</div>
      </div>
    </div>
  </div>
</template>
<script>
import apiCall from '@/js/mixins/api/api-call';
export default {
    //mounted
    mounted() {
      let url = "/api/getCrtInfos";   //연결 url
      let crtInfos = {};              //캐릭터 정보 배열
      let success = (result) => {
          crtInfos = result.data;     //결과값을 캐릭터 정보 배열에
          
          for(let info of crtInfos) {
              console.log(info.name);
          }
          
      };
      let fail = (data) => {
        console.log('fail', data);
      };
      apiCall.get(url, '', success, fail)
    }
};
</script>
<style>
.window-div{
    display: grid;
    width: 100%;
    height: 100%;
}
.outine-div {
  display: grid;
  width: 14.5vw;
  height: 19.5vw;
  border: 1px solid black;
}
.portrait-div {
  height: 13vw;
}
#crt-desc-div {
  height: 5vw;
}
#crt-name {
  display: block;
  text-align: center;
  align-content: center;
  height: 50%;
}
#crt-main-line {
  text-align: center;
  align-content: center;
  height: 50%;
}
</style>
