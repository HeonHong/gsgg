<template lang="">
    <div class="out-div">
        캐릭터 소개 페이지
        <div>
            <CharacterBox/>
        </div>
    </div>
</template>
<script>
import CharacterBox from './CharacterBox.vue';
export default {
    components: {
        CharacterBox: CharacterBox,
    }
}
</script>
<style>
.out-div{
    padding: 20px;
}
    
</style>