<template>
  <div class="select">
    <div class="select__wrapper">
      <div v-click-outside="() => active = false" @click="active = !active" class="select__front">
        <span v-if="modelValue?.label">{{ modelValue.label }}</span>
        <span v-else>{{ placeholder }}</span>
        <svg width="14" height="9" viewBox="0 0 14 9" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path d="M1 1.81104L7 7.81104L13 1.81104" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
      </div>
      <div v-if="active" class="select__options">
        <div @click="selectOption(option)" class="select__option" v-for="(option) in options" :key="option.label">
          {{ option.label}}
        </div>
      </div>
    </div>
  </div>

</template>

<script lang="ts" setup>
import { PropType, ref } from 'vue';
import ClickOutside from 'click-outside-vue3';

const vClickOutside = ClickOutside.directive;

const emit = defineEmits(['update:modelValue']);
defineProps({
  placeholder: {
    type: String,
    required: true,
  },
  options: {
    type: Array as PropType<Array<{label: string}>>,
    required: false,
    default: [{ label: 'Option 1'}, { label: 'Option 2'}, { label: 'Option 3'}]
  },
  modelValue: {
    type: Object,
    required: false,
  }
});

const active = ref(false);
const selectOption = (option: {label: string}) => {
  emit('update:modelValue', option);
  active.value = false;
}

</script>

<style lang="scss" scoped src="./Select.scss"/>
