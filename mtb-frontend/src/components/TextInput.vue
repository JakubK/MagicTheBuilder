<template>
  <div class="textinput" :class="{ textinput__focused: focused }">
    <div class="textinput__slot">
      <slot name="left"></slot>
    </div>
    <input :value="modelValue" @input="handleInput($event)" @focus="focused = true" @blur="focused = false"
      class="textinput__input" :placeholder="placeholder" />
    <div>
      <slot name="right"></slot>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

defineProps({
  placeholder: String,
  modelValue: String
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
