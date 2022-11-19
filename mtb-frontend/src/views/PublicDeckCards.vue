<template>
    <div class="deck-cards">
        <div>
          <Button @click="handleClick">{{ buttonText }}</Button>
        </div>
        <div v-if="showCards">
          <BaseHeader>{{ deck?.name }} 
            Deck Cards 
            <router-link :to="'/decks/deck/'+ deck?.id">
              <Button>Add another card</Button>
            </router-link>
          </BaseHeader>
          <div  class="deck-cards__cards">
            <CardItem
              class="deck-cards__card"
              v-for="card in cards" :card="card" 
              :key="card.id" 
              :read-only="true"/>
          </div>
        </div>
        <div v-else>
        <BaseHeader>{{ deck?.name }} Sideboard Cards 
            <router-link :to="'/decks/side/'+ deck?.id">
              <Button>Add another card</Button>
            </router-link>
        </BaseHeader>
          <div class="deck-cards__cards">
            <CardItem
              class="deck-cards__card"
              v-for="card in sideboard" :card="card" 
              :key="card.id" 
              :read-only="true"/>
          </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import Button from '@/components/Button.vue';
import BaseHeader from '@/components/typography/BaseHeader.vue';
import CardItem from '@/components/CardItem.vue';

import { Card } from '@/models/card';
import { Deck } from '@/models/deck';
import { cardsService } from '@/services/cards';
import { decksService } from '@/services/deck';
import { computed, onMounted, ref, Ref } from 'vue';
import { RouterLink } from 'vue-router';


const props = defineProps({
  id: {
    type: String,
    required: true
  }
});

const showCards = ref(true);

const deck: Ref<Deck | null> = ref(null);
const cards: Ref<Card[]> = ref([]);
const sideboard: Ref<Card[]> = ref([]);

onMounted(async() => {
    deck.value = await decksService.getNonPublicDeck(props.id);

    //  Cards from deck
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
    //  Sideboard
    const sideboardIds = deck.value.sideboard.map(x => x.cardId);
    if(sideboardIds.length === 0)
      return;
    const sideboardCardsResponse = await cardsService.getCards({
      ids: sideboardIds.join(',')
    });

    sideboard.value = sideboardCardsResponse.content;
		sideboard.value = sideboard.value.map((x,i) => ({
			...x,
			amount: deck.value?.sideboard[i].amount!
		}));
});

const handleClick = () => {
  showCards.value = !showCards.value
}

const buttonText = computed(() => {
  return showCards.value ? 'Show sideboard' : 'Show cards';
})
</script>

<style lang="scss" scoped src="./DeckCards.scss"/>
