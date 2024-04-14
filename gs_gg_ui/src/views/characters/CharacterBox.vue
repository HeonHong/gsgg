<template lang="">
  <div class="window-div">
    <div class="outline-div">
      <div class="portrait-div">
        <img class="crt-img" :src="require(`@/assets/champion_img/${crtInfos.image.full}`)">
      </div>
      <div id="crt-desc-div">
        <div id="crt-name" @click="logCrtInfos">{{crtInfos.name}}</div>
        <!-- <div id="crt-main-line">main line: top, mid. bottom</div> -->
      </div>
    </div>
  </div>
</template>
<script>
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
.window-div{
    display: grid;
    width: 100%;
    height: 100%;
}
.outline-div {
  display: grid;
  width: 14.5vw;
  height: 16.5vw;
  align-content: center;
  border: 1px solid black;
}
.portrait-div {
  height: 13vw;
}
#crt-desc-div {
  height: 5vw;
}
#crt-name {
  display: flex;
  font: var(--main-font);
  font-size: 18px;
  align-items: center;
  justify-content: center;
  height: 100%;
}
.crt-img {
  width: 100%;
  height: 100%;
}
</style>
