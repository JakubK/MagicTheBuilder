<template>
    <div class="card-collection">
			<BaseHeader>My Cards</BaseHeader>
      <Button @click="toggleVisibility">
         {{ accessButtonText  }}
      </Button>
			<div class="card-collection__cards" v-if="cards.length > 0">
					<CardItem 
						class="card-collection__card"
						v-for="card in cards" :card="card" 
						:key="card.id" 
						@flipped-back="handleFlipBack($event)"
            @amount-changed="handleAmountChange($event)"
						@increment="handleIncrement($event)"
						@decrement="handleDecrement($event)" />
			</div>
      <div v-else>
        <h3>Collection is empty</h3>
        <p>Go to Cards Browser to make it from scratch</p>
        <router-link to="/cards/browse">
          <Button>Cards browser</Button>
        </router-link>
      </div>
      <Button v-if="showMoreBtn" @click="loadMore">Load more</Button>
    </div>
</template>

<script lang="ts" setup>
import Button from '@/components/Button.vue';
import CardItem from '@/components/CardItem.vue';
import BaseHeader from '@/components/typography/BaseHeader.vue';
import { AccessLevel } from '@/models/accessLevel';
import { AmountChangedEvent } from '@/models/amountChangedEvent';
import { Card } from '@/models/card';
import { cardsService } from '@/services/cards';
import { computed, onMounted, Ref, ref } from 'vue';

import { collectionService } from '../services/collection';

const cards: Ref<Card[]> = ref([]);
const accessLevel: Ref<AccessLevel> = ref(AccessLevel.public);

onMounted(async() => {
    await handleGetRequest();
});

const accessButtonText = computed(() => {
  return accessLevel.value === AccessLevel.private ? 'Make public' : 'Make private';
})

const handleGetRequest = async() => {
  //  Fetch cards
  showMoreBtn.value = true;
  const response = await collectionService.getCollection(page.value, 20);
  userId.value = response.userId;
  if (response.cards.length === 0)
    showMoreBtn.value = false;
  const cardsIds = response.cards.map(x => x.cardId);
  if(cardsIds.length === 0)
      return;
  const cardResponse = await cardsService.getCards({
      ids: cardsIds.join(',')
  })
  cards.value = [...cards.value, ...cardResponse.content];
}

const page = ref(0);
const userId = ref('');
const showMoreBtn = ref(false);
const loadMore = async() => {
  page.value++;
  await handleGetRequest();
}

const toggleVisibility = async() => {
  accessLevel.value = accessLevel.value === AccessLevel.private ? AccessLevel.public : AccessLevel.private;
  await collectionService.setAccessLevel(accessLevel.value);
  if(accessLevel.value === AccessLevel.public) { // Save link to collection in clipboard
    navigator.clipboard.writeText(`${import.meta.env.VITE_APP_URL}/cards/collection/${userId.value}`);
  }
}

const handleIncrement = async(cardId: string) => {
  const cardToUpdate = cards.value.find(x => x.id === cardId);
  if(cardToUpdate)
    cardToUpdate.amount = await collectionService.incrementCardAmountInCollection(cardId);
}

const handleDecrement = async(cardId: string) => {
  const cardToUpdate = cards.value.find(x => x.id === cardId);
  if(cardToUpdate)
    cardToUpdate.amount = await collectionService.decrementCardAmountInCollection(cardId);
}

const handleFlipBack = async(cardId: string) => {
  const cardToUpdate = cards.value.find(x => x.id === cardId);
  if(cardToUpdate)
    cardToUpdate.amount = await collectionService.getCardAmountInCollection(cardId);
}

const handleAmountChange = async(payload: AmountChangedEvent) => {
  const cardToUpdate = cards.value.find(x => x.id === payload.cardId);
  if(cardToUpdate)
    cardToUpdate.amount = await collectionService.setInCollection(payload);
}

</script>

<style lang="scss" scoped src="./CardsCollection.scss"/>
