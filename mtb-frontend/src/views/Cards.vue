<template>
  <div class="cards-view">
    <BaseHeader>{{ title }}</BaseHeader>
    <div class="cards-view__query">
      <div v-click-outside="resetQuery" class="cards-view__input">
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
          <template v-slot:right>
            <Icon v-if="!areFiltersShown" @click="areFiltersShown = true" class="cards-view__icon">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M22 3H2L10 12.46V19L14 21V12.46L22 3Z" stroke="black" stroke-width="2" stroke-linecap="round"
                  stroke-linejoin="round" />
              </svg>
            </Icon>
            <Icon v-else class="cards-view__icon" @click="applyFilters">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path
                  d="M22 11.0801V12.0001C21.9988 14.1565 21.3005 16.2548 20.0093 17.9819C18.7182 19.7091 16.9033 20.9726 14.8354 21.584C12.7674 22.1954 10.5573 22.122 8.53447 21.3747C6.51168 20.6274 4.78465 19.2462 3.61096 17.4372C2.43727 15.6281 1.87979 13.4882 2.02168 11.3364C2.16356 9.18467 2.99721 7.13643 4.39828 5.49718C5.79935 3.85793 7.69279 2.71549 9.79619 2.24025C11.8996 1.76502 14.1003 1.98245 16.07 2.86011"
                  stroke="#2D7DD2" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                <path d="M22 4L12 14.01L9 11.01" stroke="#2D7DD2" stroke-width="2" stroke-linecap="round"
                  stroke-linejoin="round" />
              </svg>
            </Icon>
          </template>
        </TextInput>
        <div class="cards-view__filters filters" v-if="areFiltersShown">
          <div class="filters__row">
            <Select v-model="cardFormatOption" placeholder="Card Format"/>
            <Select v-model="cardTypeOption" placeholder="Card Type" />
            <Select v-model="cardSetOption" placeholder="Card Set" />
          </div>
          <br />
          <div class="filters__row">
            Colors:
            <CheckBox :label="color.label" v-model="color.checked" v-for="color in colors" :key="color.label"/>
          </div>
        </div>
      </div>
    </div>
    <div class="cards-view__order">
      <BaseHeader>Search results</BaseHeader>
      <Select placeholder="Order by"/>
    </div>
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
import CheckBox from '@/components/CheckBox.vue';
import Select from '@/components/Select.vue';

import { onMounted, ref, watch } from 'vue';
import debounce from 'lodash.debounce';
import ClickOutside from 'click-outside-vue3';

const vClickOutside = ClickOutside.directive;

const search = ref('');

defineProps({
  title: {
    type: String,
    required: true
  }
})

onMounted(async() => {
  //Fetch cards here
})

watch(search, debounce(async (newSearchValue: string, previousValue: string) => {
  if (newSearchValue.length > 0 && newSearchValue != previousValue) {
    //  Send Query 
  }
}, 500));

const areFiltersShown = ref(false);

const applyFilters = () => {
  //  Send Query
  areFiltersShown.value = false;
}

const cardFormatOption = ref({});
const cardTypeOption = ref({});
const cardSetOption = ref({});
const colors = ref([
  {
    label: 'White',
    checked: false,
  },
  {
    label: 'Blue',
    checked: false,
  },
  {
    label: 'Green',
    checked: false,
  },
  {
    label: 'Black',
    checked: false,
  }
])

const resetQuery = () => {
  areFiltersShown.value = false;
}

</script>

<style lang="scss" scoped src="./Cards.scss">
</style>
