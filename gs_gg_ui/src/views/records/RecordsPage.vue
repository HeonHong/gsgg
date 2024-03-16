<template>
  <div id="records-page">
    <div id="search-box">
      <div id="search-section">
        <input type="text" id="summoner-name"  v-model="summonerName" placeholder="소환사 이름을 입력하세요">
        <button id="search-button" @click="getSummonerByName">검색</button>
      </div>
    </div>
    <div id="search-result">
      <div id="summoner-info-box">
        <div id="summoner-info-section">
          <div id="summoner-info" v-if="summonerInfo">
            <!-- api 연동 전 ui 확인용-->
            <p><strong>{{ summonerInfo.name }}</strong></p>
            <p><strong>{{ summonerInfo.summonerLevel }}</strong></p>
          </div>
        </div>
      </div>
      <div id="match-info-box">
        <div id="match_info_section">
          <div id="match_info">
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import apiMix from "@/js/mixins/api/api-mix.js"
export default {
  mixins: [apiMix],
  data() {
    return {
      summonerName: '',
      summonerInfo: null,
    };
  },
  methods: {
    getSummonerByName() {
      if (!this.summonerName.trim()) return;
      
      // 소환사 정보 조회 API 호출
      const url = `/api/lol/summoner`;
      let param = { summonerName: this.summonerName };
      
      this.getApi(url, param, this.success, this.fail);
    },
    success(response) {
      console.log('API 호출 성공:', response);
      this.summonerInfo = response;
    },
    fail(error) {
      console.error('API 호출 실패:', error.message);
      alert('API 호출에 실패했습니다.');
    },
  }
}
</script>
<style>
#records-page {
  width: 100%;
  margin: 0;
  padding: 0;
  background-color: var(--color4);
}

#search-box {
  max-width: 100%;
  margin-top: 20px;
  margin-bottom: 40px;
  padding: 20px;
  background-color: #ffffff;
}

#search-section {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  margin-bottom: 20px;
}

#search-section input[type="text"] {
  width: 80%;
  padding: 10px;
  margin-right: 5px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 16px;
}

#search-section button {
  width: 5%;
  background-color: var(--color3);
  color: white;
  border: none;
  padding: 10px;
  cursor: pointer;
  border-radius: 4px;
  font-size: 16px;
}

#search-section button:hover {
  background-color: #8246e5;
}

#search-result {
  display: flex;
  margin-left: 10%;
  margin-right: 10%;
}

#summoner-info-box {
  width: 20%;
  height: 10%;
  margin-right: 10px;
  margin-bottom: 10px;
  padding: 20px;
  background: #ffffff;
  border-radius: 4px;
}

#summoner-info-section {
  color: #333;
}

#summoner-info {
  margin: 10px 0;
  border: 5px;
}

#match-info-box {
  width: 80%;
  height: 80vh;
  padding: 20px;
  background-color: #ffffff;
  border-radius: 4px;
}

#match-info-section {
  color: #333;
}

#match-info {
  margin: 10px 0;
  border: 5px;
}
</style>