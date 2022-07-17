<template>
  <div class="cards-view">
    <BaseHeader>Cards Query Tool</BaseHeader>
    <div class="cards-view__query">
      <TextInput class="cards-view__input" v-model="search" placeholder="Type the name of card you're looking for...">
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
      <Icon class="cards-view__icon">
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path d="M22 3H2L10 12.46V19L14 21V12.46L22 3Z" stroke="black" stroke-width="2" stroke-linecap="round"
            stroke-linejoin="round" />
        </svg>
      </Icon>
      <Icon class="cards-view__icon">
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path d="M12 5V19" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
          <path d="M19 12L12 19L5 12" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
        </svg>
      </Icon>
    </div>
    <BaseHeader>Search results</BaseHeader>
    <div class="cards-view__cards">
      <CardItem class="cards-view__card" v-for="index in 10" :key="index" />
    </div>
  </div>
</template>

<script setup lang="ts">
import TextInput from '@/components/TextInput.vue';
import Icon from '@/components/Icon.vue';
import BaseHeader from '@/components/typography/BaseHeader.vue';
import CardItem from '@/components/CardItem.vue';

import { ref, watch } from 'vue';
import debounce from 'lodash.debounce';

const search = ref('');

watch(search, debounce(async(newSearchValue: string, previousValue: string) => {
  if(newSearchValue.length > 0 && newSearchValue != previousValue) {
    //  Send Query 
  }
}, 500));

</script>

<style lang="scss" scoped src="./Cards.scss">
</style>
