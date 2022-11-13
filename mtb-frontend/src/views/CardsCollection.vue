<template>
    <div class="card-collection">
			<BaseHeader>My Cards</BaseHeader>
			<div class="card-collection__cards" v-if="cards.length > 0">
					<CardItem 
						class="card-collection__card"
						v-for="card in cards" :card="card" 
						:key="card.id" 
						@flipped-back="handleFlipBack($event)"
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
    </div>
</template>

<script lang="ts" setup>
import Button from '@/components/Button.vue';
import CardItem from '@/components/CardItem.vue';
import BaseHeader from '@/components/typography/BaseHeader.vue';
import { Card } from '@/models/card';
import { cardsService } from '@/services/cards';
import { onMounted, Ref, ref } from 'vue';

import { collectionService } from '../services/collection';

const cards: Ref<Card[]> = ref([]);

onMounted(async() => {
    const response = await collectionService.getCollection();
    const cardsIds = response.cards.map(x => x.cardId);
    if(cardsIds.length === 0)
        return;
    const cardResponse = await cardsService.getCards({
        ids: cardsIds.join(',')
    })
    cards.value = cardResponse.content;
})


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

</script>

<style lang="scss" scoped src="./CardsCollection.scss"/>
