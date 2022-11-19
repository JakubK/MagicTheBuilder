<template>
  <div class="card-item" :class="{ 'card-item--flipped': flipped }">
    <div class="card-item__content">
      <div @click="flipped = !flipped" class="card-item__front">
        <img class="card-item__image" :src="card.imageUrl"/>
      </div>
      <div class="card-item__back card-reverse">
        <img class="card-item__image" :src="card.imageUrl"/>
        <div class="card-reverse__blur"></div>
        <header class="card-reverse__head">
          <div class="card-reverse__title">
            {{ card.name }}
          </div>
        </header>
        <footer>
          <div class="card-reverse__actions">
            <div class="card-reverse__action">
              <router-link :to="`/cards/${card.id}`">
                <Icon>
                  <svg width="32" height="32" viewBox="0 0 32 32" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path
                      d="M16.0001 29.3333C23.3639 29.3333 29.3334 23.3638 29.3334 16C29.3334 8.63621 23.3639 2.66667 16.0001 2.66667C8.63628 2.66667 2.66675 8.63621 2.66675 16C2.66675 23.3638 8.63628 29.3333 16.0001 29.3333Z"
                      stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                    <path d="M16 21.3333V16" stroke="white" stroke-width="2" stroke-linecap="round"
                      stroke-linejoin="round" />
                    <path d="M16 10.6667H16.0133" stroke="white" stroke-width="2" stroke-linecap="round"
                      stroke-linejoin="round" />
                  </svg>
                </Icon>
              </router-link>
            </div>
            <Icon @click="flipped = !flipped" class="card-reverse__flip">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M17 1L21 5L17 9" stroke="white" stroke-width="2" stroke-linecap="round"
                  stroke-linejoin="round" />
                <path d="M3 11V9C3 7.93913 3.42143 6.92172 4.17157 6.17157C4.92172 5.42143 5.93913 5 7 5H21"
                  stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                <path d="M7 23L3 19L7 15" stroke="white" stroke-width="2" stroke-linecap="round"
                  stroke-linejoin="round" />
                <path d="M21 13V15C21 16.0609 20.5786 17.0783 19.8284 17.8284C19.0783 18.5786 18.0609 19 17 19H3"
                  stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
              </svg>
            </Icon>
            <div v-if="readOnly" class="card-reverse__counter">
              {{ card.amount }}
            </div>
            <div v-else class="card-reverse__counter">
              <div class="card-reverse__action">
                <svg @click="incrementCollection" width="32" height="32" viewBox="0 0 32 32" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path
                    d="M16.0001 29.3333C23.3639 29.3333 29.3334 23.3638 29.3334 16C29.3334 8.63621 23.3639 2.66667 16.0001 2.66667C8.63628 2.66667 2.66675 8.63621 2.66675 16C2.66675 23.3638 8.63628 29.3333 16.0001 29.3333Z"
                    stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                  <path d="M16 10.6667V21.3333" stroke="white" stroke-width="2" stroke-linecap="round"
                    stroke-linejoin="round" />
                  <path d="M10.6667 16H21.3334" stroke="white" stroke-width="2" stroke-linecap="round"
                    stroke-linejoin="round" />
                </svg>
              </div>
              <div v-if="!editionVisible" @click="showEdition" class="card-reverse__action card-reverse__count">
                {{ card.amount }}
              </div>
              <div v-else v-click-outside="applyEdition" class="card-reverse__action card-reverse__count card-reverse__edit">
                <input v-model="editedAmount"/>
              </div>
              <div @click="decrementCollection" class="card-reverse__action">
                <svg width="32" height="32" viewBox="0 0 32 32" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path
                    d="M16.0001 29.3333C23.3639 29.3333 29.3334 23.3638 29.3334 16C29.3334 8.63621 23.3639 2.66667 16.0001 2.66667C8.63628 2.66667 2.66675 8.63621 2.66675 16C2.66675 23.3638 8.63628 29.3333 16.0001 29.3333Z"
                    stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                  <path d="M10.6667 16H21.3334" stroke="white" stroke-width="2" stroke-linecap="round"
                    stroke-linejoin="round" />
                </svg>
              </div>
            </div>
          </div>
        </footer>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { Card } from '@/models/card';
import { PropType, ref, watch } from 'vue';
import Icon from './Icon.vue';

import ClickOutside from 'click-outside-vue3';
const vClickOutside = ClickOutside.directive;

const emit = defineEmits(['increment', 'decrement', 'amountChanged', 'flippedBack']);
const props = defineProps({
  card: {
    type: Object as PropType<Card>,
    required: true,
  },
  readOnly: {
    type: Boolean,
    required: false,
    default: false
  }
});


const editionVisible = ref(false);
const editedAmount = ref(-1);

const showEdition = () => {
  editedAmount.value = props.card.amount;
  editionVisible.value = true;
}

const applyEdition = () => {
  emit('amountChanged', {
    cardId: props.card.id,
    amount: editedAmount.value
  });
  editionVisible.value = false;
}

const flipped = ref(false);

watch(flipped, async (_, newVal) => {
  if(newVal === false)
  {
    //  Fetch new count
    emit('flippedBack', props.card.id);
  }
});

const incrementCollection = () => {
  emit('increment', props.card.id);
}

const decrementCollection = () => {
  emit('decrement', props.card.id)
}

</script>

<style lang="scss" scoped src="./CardItem.scss"/>
