<template>
  <div class="cards-view">
    
    <BaseHeader>{{ title }} <Button @click="handleFinish" v-if="cardSource > 0">Finish editing</Button></BaseHeader>
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
            <Select v-model="cardFormats" :options="cardFormatOptions" placeholder="Card Format"/>
            <Select v-model="cardTypes" :options="cardTypeOptions" placeholder="Card Type" />
            <Select v-model="cardSets" :options="cardSetOptions" placeholder="Card Set" />
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
      <div>
        <p>Sort by</p>
        <Select :options="sortingOptions" v-model="sortBy" @input="sortingChanged" @change="sortingChanged" placeholder="-"/>
      </div>
    </div>
    <div class="cards-view__cards">
      <CardItem class="cards-view__card"
        v-for="card in cards"
        :card="card"
        :key="card.id"
        @flipped-back="handleFlipBack($event)"
        @increment="handleIncrement($event)"
        @decrement="handleDecrement($event)" />
    </div>
    <Button class="cards-view__more" @click="loadMore">Load more results</Button>
  </div>
</template>

<script setup lang="ts">
import TextInput from '@/components/TextInput.vue';
import Icon from '@/components/Icon.vue';
import BaseHeader from '@/components/typography/BaseHeader.vue';
import CardItem from '@/components/CardItem.vue';
import CheckBox from '@/components/CheckBox.vue';
import Select from '@/components/Select.vue';
import Button from '@/components/Button.vue';

import { onMounted, PropType, Ref, ref, watch } from 'vue';
import debounce from 'lodash.debounce';
import ClickOutside from 'click-outside-vue3';
import { Card } from '@/models/card';
import { cardsService } from '@/services/cards';
import { metaDataService } from '@/services/metaData';
import { collectionService } from '@/services/collection';
import { CardSource } from '@/models/cardSource';
import { useRoute } from 'vue-router';
import { decksService } from '@/services/deck';
import router from '@/router';


const vClickOutside = ClickOutside.directive;

const search = ref('');

const sortBy: Ref<any[]> = ref([]);
const sortingOptions = [
  'toughness',
  'type'
]

const props = defineProps({
  title: {
    type: String,
    required: true
  },
  cardSource: {
    type: Number as PropType<CardSource>,
    required: true
  }
})


const handleFinish = () => {
  router.go(-1);
}

const route = useRoute();
const id = ref('');

const cards: Ref<Card[]> = ref([]);
const page = ref(0);

if (props.cardSource > 0) {
  const segments = route.fullPath.split('/');
  id.value = segments[segments.length - 1];
}

const loadMore = async () => {
  page.value++;
  await handleQueryRequest(true);
}

const cardFormats: Ref<any[]> = ref([]);
const cardFormatOptions: Ref<string[]> = ref([]);

const cardTypes: Ref<any[]> = ref([]);
const cardTypeOptions: Ref<string[]> = ref([]);

const cardSets: Ref<any[]> = ref([]);
const cardSetOptions: Ref<string[]> = ref([]);

onMounted(async() => {
  //  Fetch all cards here
  const cardsResponse = await cardsService.getCards({});
  cards.value = cardsResponse.content;

  //  Fetch metadata
  cardFormatOptions.value = await metaDataService.getFormats();
  cardTypeOptions.value = await metaDataService.getTypes();
  cardSetOptions.value = await metaDataService.getSets();
})

watch(search, debounce(async (newSearchValue: string, previousValue: string) => {
  if (newSearchValue.length > 0 && newSearchValue != previousValue) {
    //  Send Query 
    page.value = 0;
    await handleQueryRequest();
  }
}, 500));

const areFiltersShown = ref(false);


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

const applyFilters = async () => {
  //  Send Query
  areFiltersShown.value = false;
  page.value = 0;
  await handleQueryRequest();
}

const sortingChanged = async () => {
  page.value = 0;
  await handleQueryRequest();
}

const handleQueryRequest = async(more: boolean = false) => {
  const cardsResponse = await cardsService.getCards({
    phrase: search.value,
    page: page.value,
    size: 30,
    types: cardTypes.value.map(x => x.label).join(','),
    colors: colors.value.filter(x => x.checked).map(x => x.label).join(','),
    sortBy: sortBy.value.map(x => x.label).join(','),
    sets: cardSets.value.map(x => x.label).join(',')
  });
  if(more)
    cards.value = [...cards.value , ...cardsResponse.content];
  else
    cards.value = cardsResponse.content;
}

const handleIncrement = async(cardId: string) => {
  const cardToUpdate = cards.value.find(x => x.id === cardId);
  if(cardToUpdate) {
    if(props.cardSource === CardSource.Collection)
      cardToUpdate.amount = await collectionService.incrementCardAmountInCollection(cardId);
    else if(props.cardSource === CardSource.Deck)
      cardToUpdate.amount = await decksService.addToDeck(id.value, cardId);
    else // Sideboard
      cardToUpdate.amount = await decksService.addToSideboard(id.value, cardId);
  }
}

const handleDecrement = async(cardId: string) => {
  const cardToUpdate = cards.value.find(x => x.id === cardId);
  if(cardToUpdate) {
    if(props.cardSource === CardSource.Collection)
      cardToUpdate.amount = await collectionService.decrementCardAmountInCollection(cardId);
    else if(props.cardSource === CardSource.Deck)
      cardToUpdate.amount = await decksService.removeFromDeck(id.value, cardId);
    else // Sideboard
      cardToUpdate.amount = await decksService.removeFromSideboard(id.value, cardId);
  }
}

const handleFlipBack = async(cardId: string) => {
  const cardToUpdate = cards.value.find(x => x.id === cardId);
  if(cardToUpdate) {
    if(props.cardSource === CardSource.Collection)
      cardToUpdate.amount = await collectionService.getCardAmountInCollection(cardId);
    else if(props.cardSource === CardSource.Deck)
      cardToUpdate.amount = await decksService.getCardAmountInDeck(id.value, cardId);
    else // Sideboard
      cardToUpdate.amount = await decksService.getCardAmountInSide(id.value, cardId);
  }
}

</script>

<style lang="scss" scoped src="./Cards.scss">
</style>
