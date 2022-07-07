<template>
  <div>Search</div>
  <TextInput v-model="search" placeholder="Type the name of card you're looking for...">
    <template v-slot:left>
      <svg style="margin-right: 20px" width="24" height="24" viewBox="0 0 24 24" fill="none"
        xmlns="http://www.w3.org/2000/svg">
        <path
          d="M11 19C15.4183 19 19 15.4183 19 11C19 6.58172 15.4183 3 11 3C6.58172 3 3 6.58172 3 11C3 15.4183 6.58172 19 11 19Z"
          stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
        <path d="M20.9999 21L16.6499 16.65" stroke="black" stroke-width="2" stroke-linecap="round"
          stroke-linejoin="round" />
      </svg>
    </template>
  </TextInput>
</template>

<script setup lang="ts">
import TextInput from '@/components/TextInput.vue';
import { ref } from 'vue';
const search = ref('');
</script>

<style lang="scss" scoped src="./Cards.scss">
</style>
