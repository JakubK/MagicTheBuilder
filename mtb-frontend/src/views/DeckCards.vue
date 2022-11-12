<template>
    <div class="deck-cards">
        <BaseHeader>{{ deck?.name }} Deck Cards</BaseHeader>
        <div class="card-collection__cards">
					<CardItem
						class="card-collection__card"
						v-for="card in cards" :card="card" 
						:key="card.id" 
						@increment="handleIncrementDeck($event)"
						@decrement="handleDecrementDeck($event)" />
				</div>
				<BaseHeader>{{ deck?.name }} Sideboard Cards</BaseHeader>
				<div class="card-collection__cards">
					<CardItem
						class="card-collection__card"
						v-for="card in cards" :card="card" 
						:key="card.id" 
						@increment="handleIncrementSide($event)"
						@decrement="handleDecrementSide($event)" />
				</div>
    </div>
</template>

<script lang="ts" setup>
import CardItem from '@/components/CardItem.vue';
import BaseHeader from '@/components/typography/BaseHeader.vue';
import { Card } from '@/models/card';
import { Deck } from '@/models/deck';
import { cardsService } from '@/services/cards';
import { collectionService } from '@/services/collection';
import { onMounted, ref, Ref } from 'vue';
import { decksService } from '../services/deck';

const props = defineProps({
  id: {
    type: String,
    required: true
  }
});

const deck: Ref<Deck | null> = ref(null);
const cards: Ref<Card[]> = ref([]);

onMounted(async() => {
    deck.value = await decksService.getOwnerDeck(props.id);

		const cardsIds = deck.value.cards.map(x => x.cardId);
    if(cardsIds.length === 0)
        return;
    const cardResponse = await cardsService.getCards({
        ids: cardsIds.join(',')
    })

    cards.value = cardResponse.content;
		cards.value = cards.value.map((x,i) => ({
			...x,
			amount: deck.value?.cards[i].amount!
		}))
});

const handleIncrementDeck = async(cardId: string) => {
  const cardToUpdate = cards.value.find(x => x.id === cardId);
  if(cardToUpdate)
    cardToUpdate.amount = await decksService.addToDeck(props.id, cardId);
}

const handleDecrementDeck = async(cardId: string) => {
  const cardToUpdate = cards.value.find(x => x.id === cardId);
  if(cardToUpdate)
    cardToUpdate.amount = await decksService.removeFromDeck(props.id, cardId);
}

const handleIncrementSide = async(cardId: string) => {
  const cardToUpdate = cards.value.find(x => x.id === cardId);
  if(cardToUpdate)
    cardToUpdate.amount = await collectionService.incrementCardAmountInCollection(cardId);
}

const handleDecrementSide = async(cardId: string) => {
  const cardToUpdate = cards.value.find(x => x.id === cardId);
  if(cardToUpdate)
    cardToUpdate.amount = await collectionService.decrementCardAmountInCollection(cardId);
}

</script>

<style lang="scss" scoped src="./DeckCards.scss"/>
