<template lang="">
  <div id="main">
    <div class="topUserVs">
        <div class="btn-group">
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
            <button class="search-btn" type="button" @click="getSummonerInfo">검색</button>
          </div>
        </div>
    </div>
    <div>
      <div id="con1" class="con">
        <h3>같은팀으로 만나서</h3>
      </div>
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
    }
  }, 
  methods: {
    getSummonerInfo() { 
      console.log(' this ', this.mySummonerName);
      let param = { mySummonerName: this.mySummonerName }
      param.tagLine = '#KR1';
    
      // this.getApi('/summonerId', param, this.getSummonerInfoCallback, this.fail );
      this.getApi('/getUserInfo', param, this.getSummonerInfoCallback, this.fail );
    },
    getSummonerInfoCallback(res) {
      console.log("res ", res);
    },
    fail(err) {
      console.error(err);
    }
  }
}
</script>

<style lang="">

</style>

