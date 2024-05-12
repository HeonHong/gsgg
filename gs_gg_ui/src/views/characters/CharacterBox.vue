<template lang="">
  <div class="window-div">
    <div class="outline-div">
      <div class="portrait-div">
        <img class="crt-img" :src="require(`@/assets/champion_img/${crtInfos.FILE_NAME}`)">
      </div>
      <div id="crt-desc-div">
        <div id="crt-name" @click="logCrtInfos">{{crtInfos.KOR_NAME}}</div>
        <div id="crt-main-line">미드, 정글</div>
      </div>
    </div>
  </div>
</template>
<script>
import '@/css/charactersStyle/CharacterBox.css';           //css 파일
export default {
  props: {
      crtInfos: Object
  },

  methods: {
    logCrtInfos() {
      console.log(this.crtInfos)
    }
  }
};
</script>
<style>

</style>
