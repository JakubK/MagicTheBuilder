<template>
  <div class="textinput" :class="{ textinput__focused: focused }">
    <div class="textinput__slot">
      <slot name="left"></slot>
    </div>
    <input @focus="focused = true" @blur="focused = false" class="textinput__input" :placeholder="placeholder"/>
    <div>
      <slot name="right"></slot>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { ref } from 'vue';

  defineProps({
    placeholder: String,
  });

  const focused = ref<boolean>(false);
</script>

<style lang="scss" scoped src="./TextInput.scss"/>
