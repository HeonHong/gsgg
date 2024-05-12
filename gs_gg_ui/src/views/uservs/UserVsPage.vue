<template lang="">
  <div id="main">
    <div class="topUserVs">
        <div class="btn-group">
          <!-- radio type으로 수정 할 예정 -->
          <label class="btn-light">
            <input value="랭크전"/>
          </label>
          <label>
            <input value="일반전"/>
          </label>
        </div>
        <div class="input-group">
          <input placeholder="나" v-model="mySummonerName"/>
          <input placeholder="상대" v-model="yourSummonerName" />
          <div class="input-group-append">
            <button class="search-btn" type="button" @click="getUserPuuid">검색</button>
          </div>
        </div>
      </div>
    </div>
    <div>
      <div id="con1" class="con">
        <h3>같은팀으로 만나서</h3>
    </div>
    <div>
      <div id="con2" class="con">
         <h3>적팀으로 만나서</h3>
      </div>
    </div>
  </div>
</template>

<script>
import apiMix from '@/js/mixins/api/api-mix';
export default {
  mixins: [apiMix],
  data() {
    return {
      mySummonerName: '',
      yourSummonerName: '',
      summonerInfo: '', 
      param: {}
    }
  }, 
  methods: {
    getUserPuuid() { 
      this.param = { mySummonerName: this.mySummonerName }
      this.param.tagLine = 'KR1';
      this.getApi('/getUserPuuid', this.param, this.getUserPuuidCallback, this.fail );
    },
    getUserPuuidCallback(res) {
      this.summonerInfo = res.data;
      this.getMatchId(this.summonerInfo.puuid);
    },

    // getUserId(puuid) {
    //   this.param = {puuid: puuid};
    //   this.getApi("/getUserId", this.param, this.getUserIdCallback, this.fail );
    // },
    // getUserIdCallback(res){
    //   this.summonerInfo = res.data; 
    //   console.log("afsds", this.summonerInfo);
    //   //this.getSummonerInfo(this.summonerInfo.id);
    // },
    
    getMatchId(puuid) {
      this.param = {puuid: puuid};
      this.getApi("/getMatchId", this.param, this.getMatchIdCallback, this.fail );
    },
    getMatchIdCallback(res){
      this.summonerInfo = res.data; 
      //this.getSummonerInfo(this.summonerInfo.id);
    },
    
    // getSummonerInfo(id) {
    //   this.param = { id: id}
    //   this.getApi("/getSummonerInfo", this.param, this.getSummonerInfoCallback, this.fail)
    // },
    // getSummonerInfoCallback(res) {
    //   this.summonerInfo = res.data;
    //   console.log(" summonerInfo 성공 ", this.summonerInfo);
    // },

    fail(err) {
      console.error(err);
    }
  }
}
</script>

<style lang="">

</style>

