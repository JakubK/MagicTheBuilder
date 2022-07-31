<template>
  <div>
    <div class="textinput" :class="{ textinput__focused: focused }">
      <div class="textinput__slot">
        <slot name="left"></slot>
      </div>
      <input :type="type" :value="modelValue" @input="handleInput($event)" @focus="focused = true" @blur="focused = false"
        class="textinput__input" :placeholder="placeholder" />
      <div>
        <slot name="right"></slot>
      </div>
    </div>
    <ErrorLabel :message="error.message" v-for="(error, index) in errors" :key="index"/>
  </div>
</template>

<script setup lang="ts">
import { PropType, ref } from 'vue';
import ErrorLabel from './ErrorLabel.vue';

defineProps({
  placeholder: String,
  modelValue: String,
  type: {
    type: String,
    required: false,
    default: 'text'
  },
  errors: {
    type: Array as PropType<Array<{message: string}>>
  }
});

const emit = defineEmits([
  'update:modelValue'
]);

const focused = ref<boolean>(false);

const handleInput = (e: any) => {
  emit('update:modelValue', e.target.value)
}
</script>

<style lang="scss" scoped src="./TextInput.scss"/>
