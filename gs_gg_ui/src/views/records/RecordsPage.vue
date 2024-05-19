<template>
  <div id="records-page">
    <div id="search-box">
      <div id="search-section">
        <input type="text" id="summoner-name" v-model="summonerName" placeholder="소환사 이름을 입력하세요">
        <button id="search-button" @click="getSummonerByName">검색</button>
      </div>
    </div>
    <div id="search-result">
      <div id="summoner-info-box">
        <div id="summoner-info-section">
          <div id="summoner-info" v-if="summonerInfo">
            <div id="summoner-profile">
              <img :src="`https://ddragon.leagueoflegends.com/cdn/14.8.1/img/profileicon/${summonerInfo.profileIconId}.png`" alt="프로필 아이콘" class="profile-icon">
              <div class="level-badge"><h1>{{ summonerInfo.summonerLevel }}</h1></div>
              <div class="profile-details">
                <h1>{{ summonerID.gameName }} <span class="tag-line">#{{ summonerID.tagLine }}</span></h1>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div id="match-info-box">
        <div id="match_info_section" v-for="match in paginatedMatches" :key="match.gameId" class="match-card">
          <div class="match-summary">
            <div class="game-result" :class="{'win': match.win, 'loss': !match.win}">
              <span>{{ match.win ? 'Victory' : 'Defeat' }}</span>
            </div>
            <div class="game-details">
              <span>K/D/A: {{ match.kills }}/{{ match.deaths }}/{{ match.assists }}</span>
            </div>
          </div>
          <div class="champion-info">
            <img :src="`https://ddragon.leagueoflegends.com/cdn/14.9.1/img/champion/${match.championName}.png`" alt="champion">
            <span>{{ match.championName }}</span>
          </div>
        </div>
        <div class="pagination-controls" v-if="totalPages > 1">
          <button @click="prevPage" :disabled="currentPage === 1">이전</button>
          <span>{{ currentPage }} / {{ totalPages }}</span>
          <button @click="nextPage" :disabled="currentPage === totalPages">다음</button>
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
      summonerID: null,
      summonerInfo: null,
      matchInfo: [],
      matchDetail: [],
      currentPage: 1,
      itemsPerPage: 9
    };
  },
  computed: {
    paginatedMatches() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.matchDetail.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.matchDetail.length / this.itemsPerPage);
    }
  },
  methods: {
    getSummonerByName() {
      if (!this.summonerName.trim()) return;

      const [name, tagLine] = this.summonerName.split('#'); // 라이엇 계정 방식 변경으로 인해 태그라인도 입력 받아야함
  
      let param = { summonerName: name };

      if (tagLine) {
        param.tagLine = tagLine;
      }
      
      this.getApi('/summonerId', param, this.successPuuid, this.fail);
    },
    successPuuid(response) {
      console.log('소환사 ID 조회 성공:', response.data);
      this.summonerID = response.data;
      this.getSummonerByPuuid(this.summonerID.puuid);
    },

    getSummonerByPuuid(puuid) {
      let param = {puuid: puuid};
      this.getApi('/summonerInfo', param, this.successSummoner, this.fail);
    },
    successSummoner(response) {
      console.log('소환사 정보 조회 성공:', response.data);
      this.summonerInfo = response.data;
      this.getMatchesByPuuid(this.summonerInfo.puuid);
    },

    getMatchesByPuuid(puuid) {
      let param = { puuid: puuid };
      this.getApi('/matches', param, this.successMatches, this.fail);
    },
    successMatches(response) {
      console.log('매치 ID 목록 조회 성공:', response.data);
      this.getMatchDetails(response.data);
    },

    getMatchDetails(matchIds) {
      const queryString = matchIds.map(id => `matchIds=${id}`).join('&');
      this.getApi(`/matches/details?${queryString}`, {}, this.successMatchDetails, this.fail);
    },
    successMatchDetails(response) {
      console.log('매치 상세 정보 조회 성공:', response.data);
      this.matchInfo = response.data; // 전체 데이터를 배열에 저장
      
      this.matchDetail = response.data.flatMap(match => 
        match.info.participants.filter(participant =>
          participant.summonerName.toLowerCase() === this.summonerID.gameName.toLowerCase()
        ).map(participant => ({
          gameId: match.info.gameId,
          win: participant.win,
          kills: participant.kills,
          deaths: participant.deaths,
          assists: participant.assists,
          championName: participant.championName
        }))
      );
    },
    fail(error) {
      console.error('API 호출 실패:', error.message);
      alert('API 호출에 실패했습니다.');
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
      }
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    }
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

#summoner-profile {
  display: flex;
  align-items: center;
  background-color: #fff;
  padding: 10px;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-bottom: 10px;
}

.profile-icon {
  width: 80px; 
  height: 80px;
  border-radius: 15%; 
  margin-right: 20px;
  position: relative;
  overflow: hidden; 
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); 
}

.level-badge {
  position: relative;
  top: 30px;
  right: 90px; 
  transform: translate(50%, 50%);
  background-color: #4e4d4d;
  border-radius: 15%;
  width: 30px;
  height: 15px;
  display: flex; 
  align-items: center; 
  justify-content: center;
  border: 2px solid #4e4d4d;
  box-shadow: 0 2px 4px rgba(51, 37, 37, 0.25); 
  font-size: 5px;
  color: #fff;
}

.profile-details {
  display: flex;
  flex-direction: column;
}

.profile-details h2 {
  font-size: 1.2rem;
  margin: 0;
}

.tag-line {
  font-size: 0.9rem;
  color: #777;
}

#match-info-section {
  color: #333;
}

#match-info {
  margin: 10px 0;
  border: 5px;
}

#match-info-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.match-card {
  width: 90%;
  background: #fff;
  border-radius: 5px;
  margin-bottom: 10px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  display: flex;
  flex-direction: column;
  padding: 10px;
}

.match-summary {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
}

.game-result {
  padding: 5px 10px;
  color: #fff;
  border-radius: 4px;
}

.win {
  background-color: #a3cfec;
}

.loss {
  background-color: #e2b6b3;
}

.game-details {
  font-size: 12px;
}

.champion-info {
  display: flex;
  align-items: center;
  padding-top: 10px;
}

.champion-info img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}

.champion-info span {
  font-size: 14px;
  font-weight: bold;
}

pagination-controls {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
}

.pagination-controls button {
  background-color: var(--color3);
  color: white;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 4px;
  font-size: 16px;
}

.pagination-controls button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.pagination-controls span {
  margin: 0 10px;
  font-size: 16px;
}
</style>