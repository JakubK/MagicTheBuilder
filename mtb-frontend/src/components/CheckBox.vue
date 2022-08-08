<template>
  <div>
    <div class="checkbox">
      <div @click="clicked" class="checkbox__circle">
        <div v-if="modelValue" class="checkbox__check"></div>
      </div>
      <span class="checkbox__label">{{ label }}</span>
    </div>
    <ErrorLabel :message="error.message" v-for="(error, index) in errors" :key="index" />
  </div>
</template>

<style lang="scss" scoped src="./CheckBox.scss"/>

<script lang="ts" setup>
import { PropType } from 'vue';
import ErrorLabel from './ErrorLabel.vue';
const props = defineProps({
  modelValue: {
    type: Boolean,
    required: false,
    default: false
  },
  label: {
    type: String
  },
  errors: {
    type: Array as PropType<Array<{message: string}>>
  }
});

const emit = defineEmits(['update:modelValue']);

const clicked = () => {
  emit('update:modelValue', !props.modelValue)
};

</script>
