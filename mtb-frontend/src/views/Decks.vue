<template>
  <div class="decks-view">
    <BaseHeader class="decks-view__head">
      {{ title }}
      <div></div>
      <Button @click="initAddDeck">New deck</Button>
    </BaseHeader>
    <div v-if="decks.length > 0" class="decks-view__decks">
      <div class="deck" v-for="deck in decks" :key="deck.id">
        <div class="deck__features">
          <span>{{ deck.accessLevel }}</span>
          <span>{{ deck.gameMode }}</span>
        </div>
        <br/>
        <div class="deck__content">
          <span>{{ deck.name }}</span>
          <span class="deck__icons">
            <Icon>
              <svg width="25" height="25" viewBox="0 0 25 25" fill="none" xmlns="http://www.w3.org/2000/svg">
                <g clip-path="url(#clip0_607_380)">
                  <path d="M1.49756 12.192C1.49756 12.192 5.49756 4.19205 12.4976 4.19205C19.4976 4.19205 23.4976 12.192 23.4976 12.192C23.4976 12.192 19.4976 20.192 12.4976 20.192C5.49756 20.192 1.49756 12.192 1.49756 12.192Z" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                  <path d="M12.4976 15.192C14.1544 15.192 15.4976 13.8489 15.4976 12.192C15.4976 10.5352 14.1544 9.19205 12.4976 9.19205C10.8407 9.19205 9.49756 10.5352 9.49756 12.192C9.49756 13.8489 10.8407 15.192 12.4976 15.192Z" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                </g>
                <defs>
                  <clipPath id="clip0_607_380">
                    <rect width="24" height="24" fill="white" transform="translate(0.497559 0.192047)"/>
                  </clipPath>
                </defs>
              </svg>
            </Icon>
            <Icon @click="initUpdateDeck(deck)">
              <svg width="25" height="25" viewBox="0 0 25 25" fill="none" xmlns="http://www.w3.org/2000/svg">
               <path d="M11.4976 4.19205H4.49756C3.96713 4.19205 3.45842 4.40276 3.08334 4.77783C2.70827 5.15291 2.49756 5.66161 2.49756 6.19205V20.192C2.49756 20.7225 2.70827 21.2312 3.08334 21.6063C3.45842 21.9813 3.96713 22.192 4.49756 22.192H18.4976C19.028 22.192 19.5367 21.9813 19.9118 21.6063C20.2868 21.2312 20.4976 20.7225 20.4976 20.192V13.192" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
               <path d="M18.9976 2.69206C19.3954 2.29424 19.9349 2.07074 20.4976 2.07074C21.0602 2.07074 21.5997 2.29424 21.9976 2.69206C22.3954 3.08988 22.6189 3.62945 22.6189 4.19206C22.6189 4.75467 22.3954 5.29424 21.9976 5.69206L12.4976 15.1921L8.49756 16.1921L9.49756 12.1921L18.9976 2.69206Z" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
            </Icon>
            <Icon @click="handleDelete(deck.id)">
              <svg width="25" height="25" viewBox="0 0 25 25" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M3.49756 6.19205H5.49756H21.4976" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <path d="M19.4976 6.19205V20.192C19.4976 20.7225 19.2868 21.2312 18.9118 21.6063C18.5367 21.9813 18.028 22.192 17.4976 22.192H7.49756C6.96713 22.192 6.45842 21.9813 6.08334 21.6063C5.70827 21.2312 5.49756 20.7225 5.49756 20.192V6.19205M8.49756 6.19205V4.19205C8.49756 3.66161 8.70827 3.15291 9.08335 2.77783C9.45842 2.40276 9.96713 2.19205 10.4976 2.19205H14.4976C15.028 2.19205 15.5367 2.40276 15.9118 2.77783C16.2868 3.15291 16.4976 3.66161 16.4976 4.19205V6.19205" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <path d="M10.4976 11.192V17.192" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <path d="M14.4976 11.192V17.192" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
            </Icon>
          </span>
        </div>
      </div>
    </div>
    <div v-else  class="decks-view__empty">
      <div>
        <h4>No decks found</h4>
        <p>Create your first deck</p>
        <Button @click="initAddDeck">New deck</Button>
      </div>
    </div>
    <Teleport to="body">
      <DeckModal :method="method" :deck="deck" @close="showModal = false" v-if="showModal"/>
    </Teleport>
  </div>
</template>

<script setup lang="ts">
import Button from '@/components/Button.vue';
import Icon from '@/components/Icon.vue';
import BaseHeader from '@/components/typography/BaseHeader.vue';
import DeckModal from './DeckModal.vue';

import { Deck } from '@/models/deck';
import { decksService } from '@/services/deck';
import { onMounted, Ref, ref } from 'vue';

defineProps({
  title: {
    type: String,
    required: true
  }
})

const method = ref('');
const deck = ref();

const showModal = ref(false);
const decks: Ref<Deck[]> = ref([]);

const initAddDeck = () => {
  method.value = 'POST';
  showModal.value = true;
}

const initUpdateDeck = (targetDeck: Deck) => {
  method.value = 'PUT';
  deck.value = targetDeck;
  showModal.value = true;
}

const handleDelete = async(deckId: string) => {
  await decksService.deleteDeck(deckId);
  const deckIndex = decks.value.findIndex(x => x.id === deckId);
  decks.value.splice(deckIndex, 1);
}

onMounted(async() => {
  //Fetch decks here
  decks.value = await decksService.getMyDecks();
})

</script>

<style lang="scss" scoped src="./Decks.scss"/>
