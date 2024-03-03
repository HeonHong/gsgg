export default {
  checkLog(word) {
    console.log(word);
  },
  
  /**
   * null 체크
   */
  isNull(target) {
    if(target == null) {
      return true;
    }

    if(target == '') {
      return true;
    }

    return false;
  },

  /**
   * target이 null일 경우 nullParam 반환
   */
  nvl(target, nullParam) {
    if(this.isNull(target)) {
      return nullParam;
    }

    return target;
  }
};
