<template>
    <div>
        <div class="input-form-container" :class="sizeAdjustment">
            <div class="input-form-label" v-if="hasLabel">
                <span>{{ label }}</span>
            </div>
            <div class="input-form">
                <select>
                    <option v-for="(item, index) in options" :value="item.option" :key="index">{{ item.option }}
                    </option>
                </select>
            </div>
        </div>

    </div>
</template>
<script>
export default {
    props: ['options', 'label'],
    computed: {
        hasLabel() {
            return (this.label == null || this.label == '') ? false : true;
        },
        sizeAdjustment() {
            if (!this.hasLabel) return (this.size == null || this.size == '') ? "size-1" : "size-" + (this.size);
            else return (this.size == null || this.size == '') ? "size-2" : "size-" + (this.size);
        }

    },
}
</script>
<style lang="">

</style>