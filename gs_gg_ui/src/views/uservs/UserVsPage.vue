<template>
  <div id="main">
    <div class="topUserVs">
        <div class="btn-con">
        <div class="btn-group">
          <!-- radio type으로 수정 할 예정 -->
<!--          <label class="btn-light">-->
<!--            <input value="랭크전" readonly/>-->
<!--          </label>-->
<!--          <label>-->
<!--            <input value="일반전" readonly/>-->
<!--          </label>-->
        </div>
        <div class="input-group">
          <input placeholder="나 플레이어 이름 + #서버" v-model="mySummonerName"/>
          <input placeholder="상대 플레이어 이름 + #서버" v-model="yourSummonerName" />
          <div class="input-group-append">
            <button class="search-btn" type="button" @click="getUserPuuid">검색</button>
          </div>
        </div>
        </div>
    </div>
  </div>
  <div>
    <div id="con1" class="con">
        <h3 class="con-margin">같은팀으로 만나서</h3>
        <div id="con1_2" class="table-responsive con-margin">
          <h4>
            승리 :
            <span class="red">{{ getMyWinCount }}</span>
            &nbsp;&nbsp;
            패배 :
            <span class="blue">{{ participantInfo.length - getMyWinCount }}</span>
          </h4>
          <table class="vsTable table table-striped">
            <thead class="thead-dark">
              <tr>
                <th scope="col"> 날짜 </th>
                <th scope="col"> 결과 </th>
                <th scope="col"> 나 </th>
                <th scope="col"> KDA </th>
                <th scope="col"> 딜 </th>
                <th scope="col"> 너 </th>
                <th scope="col"> KDA </th>
                <th scope="col"> 딜 </th>
                <th scope="col">  </th>
              </tr>
            </thead>
            <tbody>
            <tr v-for="match in participantInfo" :key="match.date">
                <td> {{ match.date }} </td>
                <td>
                  <b>
                    <span class="">{{ match.myResult ? '승' : '패' }}</span> <!-- 승 / 패 -->
                  </b>
                </td>
                <td>
                  <img :src="match.myChampionImg" alt="My Champion">
                </td>
                <td>{{ match.myKda }}</td>
                <td>{{ match.myTotalDamageDealt }}</td>
                <td>
                  <img :src="match.yourChampionImg" alt="Your Champion">
                </td>
                <td>{{ match.yourKda }}</td>
                <td>{{ match.yourTotalDamageDealt }}</td>
                <td></td> <!-- 돋보기 버튼 -->
              </tr>
            </tbody>
          </table>
        </div>
    </div>

    <div>
      <div id="con2" class="con">
         <h3 class="con-margin">적팀으로 만나서</h3>
          <div id="con1_2" class="table-responsive">
            <h4 style="padding-left: 15px;">
              승리 :
              <span class="red">{{ getYourWinCount }}</span>
              &nbsp;&nbsp;
              패배 :
              <span class="blue">{{ participantInfo.length - getYourWinCount }}</span>
            </h4>
            <table class="vsTable table table-striped">
              <thead class="thead-dark">
              <tr>
                <th scope="col"> 날짜 </th>
                <th scope="col"> 결과 </th>
                <th scope="col"> 나 </th>
                <th scope="col"> KDA </th>
                <th scope="col"> 딜 </th>
                <th scope="col"> 너 </th>
                <th scope="col"> KDA </th>
                <th scope="col"> 딜 </th>
                <th scope="col"> </th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="match in participantInfo" :key="match.date">
                <td> {{ match.date }}</td>
                <td>
                  <b>
                    <span class="">{{ match.yourResult ? '승' : '패' }}</span> <!-- 승 / 패 -->
                  </b>
                </td>
                <td>
                  <img :src="match.yourChampionImg">
                </td>
                <td>{{ match.yourKda }}</td>
                <td>{{ match.yourTotalDamageDealt}}</td>
                <td>
                  <img :src="match.myChampionImg">
                </td>
                <td>{{ match.myKda }}</td>
                <td>{{ match.myTotalDamageDealt}}</td>
                <td></td> <!-- 돋보기 버튼 -->
              </tr>
              </tbody>
            </table>
          </div>
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
      puuidInfo:[],
      myPuuid:'',
      yourPuuid: '',
      myMatchInfo: [],
      yourMatchInfo: [],
      param: {}
    }
  },
  computed: {
    participantInfo() {
      return this.myMatchInfo.map((match, index) => {
        const myParticipant = match.info.participants.find(p => p.puuid === this.myPuuid);
        const yourMatch = this.yourMatchInfo[index];
        const yourParticipant = yourMatch ? yourMatch.info.participants.find(p => p.puuid === this.yourPuuid) : null;

        return {
          date: new Date(match.info.gameCreation).toLocaleDateString(),
          myResult: myParticipant ? myParticipant.win : false,
          myChampionName: myParticipant ? myParticipant.championName : '',
          myKda: myParticipant ? `${myParticipant.kills}/${myParticipant.deaths}/${myParticipant.assists}` : '',
          myChampionImg: myParticipant ? `https://opgg-static.akamaized.net/meta/images/lol/14.9.1/champion/${myParticipant.championName}.png` : '',
          myTotalDamageDealt: myParticipant ? myParticipant.totalDamageDealtToChampions : '',
          yourResult: yourParticipant ? yourParticipant.win : false,
          yourChampionName: yourParticipant ? yourParticipant.championName : '',
          yourKda: yourParticipant ? `${yourParticipant.kills}/${yourParticipant.deaths}/${yourParticipant.assists}` : '',
          yourChampionImg: yourParticipant ? `https://opgg-static.akamaized.net/meta/images/lol/14.9.1/champion/${yourParticipant.championName}.png` : '',
          yourTotalDamageDealt: yourParticipant ? yourParticipant.totalDamageDealtToChampions : ''
        };
      });
    },
    getYourWinCount() {
      return this.participantInfo.filter(match => match.yourResult).length;
    },
    getMyWinCount() {
      return this.participantInfo.filter(match => match.myResult).length;
    }

  },

  methods: {
    getUserPuuid() {
      const myParts = this.mySummonerName.split('#');
      const yourParts = this.yourSummonerName.split('#');
      this.param = {
        mySummonerName: myParts[0],
        yourSummonerName: yourParts[0],
        myTagLine: myParts[1],
        yourTagLine: yourParts[1]
      };

      this.getApi('/getUserPuuid', this.param, this.getUserPuuidCallback, this.fail );
    },
    getUserPuuidCallback(res) {
      this.puuidInfo = res.data;
      console.log("getUserPuuidCallback 성공 ", this.puuidInfo);
      this.myPuuid = this.puuidInfo.myPuuid;
      this.yourPuuid = this.puuidInfo.yourPuuid;
      this.getMatchId(this.myPuuid , this.yourPuuid);
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

    getMatchId(myPuuid,  yourPuuid) {
      this.param = {myPuuid: myPuuid, yourPuuid : yourPuuid};
      this.getApi("/getMatchId", this.param, this.getMatchIdCallback, this.fail );
    },
    getMatchIdCallback(res){
      console.log(" getMatchIdCallback / myMatches / yourMatches ", res);
      const param = res.data;
      const commonMatches = {
        myMatches: [],
        yourMatches: []
      };

      param.myMatches.forEach(myMatchId => {
        if (param.yourMatches.includes(myMatchId)) {
          commonMatches.myMatches.push(myMatchId);
        }
      });

      param.yourMatches.forEach(yourMatchId => {
        if (param.myMatches.includes(yourMatchId)) {
          commonMatches.yourMatches.push(yourMatchId);
        }
      });

      console.log("Common matches: ", commonMatches);
      this.postApi("/getMatchDetails", commonMatches, this.getMatchDetailsCallback, this.fail);
    },
    getMatchDetailsCallback(res){
      console.log("getMatchDetailsCallback data ", res.data);
      this.myMatchInfo = res.data.myMatches;
      this.yourMatchInfo = res.data.yourMatches;
      console.log(" this.myMatchInfo ", this.myMatchInfo);
      console.log(" this.yourMatchInfo ", this.yourMatchInfo);
      // [ {},  {} ]
      // res.data 배열의 각 요소는 info와 metadata를 포함하는 객체
      // info 객체 반복 iterate 하면서 접근
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


