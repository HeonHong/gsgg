<template lang="">
  <div class="window-div">
    <div class="outline-div">
      <div class="portrait-div">

      </div>
      <div id="crt-desc-div">
        <div id="crt-name" @click="logCrtInfos">character name</div>
        <div id="crt-main-line">main line: top, mid. bottom</div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: {
      crtInfos: Array
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
