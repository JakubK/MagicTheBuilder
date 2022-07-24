<template>
  <div class="checkbox">
    <div @click="clicked" class="checkbox__circle">
      <div v-if="modelValue" class="checkbox__check"></div>
    </div>
    <span class="checkbox__label">{{ label }}</span>
  </div>
</template>

<style lang="scss" scoped src="./CheckBox.scss"/>

<script lang="ts" setup>
const props = defineProps({
  modelValue: {
    type: Boolean,
    required: false,
    default: false
  },
  label: {
    type: String
  }
});

const emit = defineEmits(['update:modelValue']);

const clicked = () => {
  emit('update:modelValue', !props.modelValue)
};

</script>
