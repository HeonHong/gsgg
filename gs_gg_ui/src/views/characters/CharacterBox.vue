<template lang="">
  <div class="window-div">
    <div class="outine-div">
      <div class="portrait-div">

      </div>
      <div id="crt-desc-div">
        <div id="crt-name">character name</div>
        <div id="crt-main-line">main line: top, mid. bottom</div>
      </div>
    </div>
  </div>
</template>
<script>
export default {};
</script>
<style>
.window-div{
    width: 100%;
    height: 100%;
}
.outine-div {
  width: 200px;
  height: 250px;
  border: 1px solid black;
}
.portrait-div {
  height: 70%;
}
#crt-desc-div {
  height: 30%;
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
