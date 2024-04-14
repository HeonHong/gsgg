<template lang="">
  <div class="window-div">
    <div class="outline-div">
      <div class="portrait-div">
        <img class="crt-img" :src="require(`@/assets/champion_img/${crtInfos.image.full}`)">
      </div>
      <div id="crt-desc-div">
        <div id="crt-name" @click="logCrtInfos">{{crtInfos.name}}</div>
        <div id="crt-main-line">미드, 정글</div>
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
    width: 100%;
    height: 100%;
}
.outline-div {
  width: 13rem;
  height: 19rem;
  align-content: center;
  border: 0.2rem solid var(--color1);
}
.portrait-div {
  height: 65%;
}
#crt-desc-div {
  height: 35%;
  font: var(--main-font);
  font-size: 1.8rem;
}
#crt-name {
  display: flex;
  height: 50%;
  align-items: center;
  justify-content: center;
}
#crt-main-line {
  display: flex;
  height: 50%;
  align-items: center;
  justify-content: center;
}
.crt-img {
  width: 100%;
  height: 100%;
}
</style>
