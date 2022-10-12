<template>
  <div v-click-outside="() => active = false" class="select">
    <div class="select__wrapper">
      <div @click="active = true" class="select__front">
        <span>{{ placeholder }}</span>
        <svg width="14" height="9" viewBox="0 0 14 9" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path d="M1 1.81104L7 7.81104L13 1.81104" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
      </div>
      <div v-if="active" class="select__options">
        <div @click="selectOption(option)" class="select__option" v-for="(option) in options" :key="option.label">
          <div>
          {{ option.label}}
          </div>
          <svg v-if="isChecked(option.label)" width="25" height="25" viewBox="0 0 25 25" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M20.2522 6.65719L9.2522 17.6572L4.2522 12.6572" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </div>
      </div>
    </div>
  </div>

</template>

<script lang="ts" setup>
import { PropType, ref } from 'vue';
import ClickOutside from 'click-outside-vue3';

const vClickOutside = ClickOutside.directive;

const emit = defineEmits(['update:modelValue', 'input']);
const props = defineProps({
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
    type: Array as PropType<Array<{label: string, checked: boolean}>>,
    required: false,
    default: []
  }
});

const isChecked = (label: string): boolean => {
  console.log(props.modelValue);
  if(props.modelValue!.filter(x => x.label === label).length === 0)
    return false;
  return props.modelValue!.filter(x => x.label === label)[0].checked;
}

const active = ref(false);
const selectOption = (option: {label: string}) => {
  if(props.modelValue!.filter(x => x.label === option.label).length === 0) {
      const updatedArray = props.modelValue!
        .filter(x => x.label !== option.label)
        .map(x => x);
      //  Add item as checked
      updatedArray.push({
      ...option,
      checked: true
    });
    emit('update:modelValue', updatedArray);
  }
  else {
    //  Toggle state 
    const updatedArray = props.modelValue!
        .map(x => {
          if(x.label === option.label)
            return {
              ...x,
              checked: !x.checked
            }
          return x;
        });
    emit('update:modelValue', updatedArray);
  }
  emit('input');
}

</script>

<style lang="scss" scoped src="./Select.scss"/>
