<template>
  <div id="main">
    <div class="topUserVs">
        <div class="btn-group">
          <!-- radio type으로 수정 할 예정 -->
          <label class="btn-light">
            <input value="랭크전" readonly/>
          </label>
<!--          <label>-->
<!--            <input value="일반전" readonly/>-->
<!--          </label>-->
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
        <h3 class="con-margin">같은팀으로 만나서</h3>
        <div id="con1_2" class="table-responsive con-margin">
          <h4>
            승리 :
            <span class="red"> ?? </span>
            &nbsp;&nbsp;
            패배 :
            <span class="blue"> ?? </span>
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
                <th scope="col"> More </th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="match in participantInfo" :key="match.info.gameId">
                <td> {{ match.date }}</td>
                <td>
                  <b>
                    <span class="">{{ match.result ? '승' : '패' }}</span> <!-- 승 / 패 -->
                  </b>
                </td>
                <td>
                  <img :src="match.myChampionImg">
                </td>
                <td>{{ match.myKda }}</td>
                <td>{{ match.myTotalDamageDealt}}</td>
                <td>
                  <img>
                </td>
                <td></td>
                <td></td>
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
            <h4>
              승리 :
              <span class="red"> ?? </span>
              &nbsp;&nbsp;
              패배 :
              <span class="blue"> ?? </span>
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
                <th scope="col"> More </th>
              </tr>
              </thead>
              <tbody>
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
      matchInfo: [],
      param: {}
    }
  },
  computed: {
    participantInfo() {
      return this.matchInfo.map( match => {
          const myParticipant = match.info.participants.find( p => p.puuid === this.myPuuid );
          return {
            ...match // 새로운 객체 생성
            ,date: new Date(match.info.gameCreation).toLocaleDateString() // ? gameCreation : 1714025227629
            , result: myParticipant ? myParticipant.win : false
            , myChampionName: myParticipant ? myParticipant.championName : ''
            , myKda : myParticipant ? `${myParticipant.kills}/${myParticipant.deaths}/${myParticipant.assists}` : ''
            , myChampionImg: myParticipant ? `https://opgg-static.akamaized.net/meta/images/lol/14.9.1/champion/${myParticipant.championName}.png` : ''
            , myTotalDamageDealt : myParticipant ? myParticipant.totalDamageDealtToChampions : ''
          };
      });
    }
  },

  methods: {
    getUserPuuid() {
      this.param = { mySummonerName: this.mySummonerName, yourSummonerName: this.yourSummonerName }
      this.param.tagLine = 'KR1';
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
      console.log(" res ", res);
      // this.param = res.data;
      // console.log("this. param ", this.param);
      // this.postApi("/getMatchDetails", this.param, this.getMatchDetailsCallback, this.fail);
    },
    getMatchDetailsCallback(res){
      console.log("getMatchDetailsCallback data ", res.data);
      this.matchInfo = res.data;
      // [ {},  {} ]
      // res.data 배열의 각 요소는 info와 metadata를 포함하는 객체
      // info 객체 반복 iterate 하면서 접근
      console.log(" match.info  ", this.matchInfo );
    },
    getResult(match) {
      const participant = match.info.participants.find( p => p.puuid === this.myPuuid );
      return participant ? participant.win : false;
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


