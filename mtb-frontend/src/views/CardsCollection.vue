<template>
    <div class="card-collection">
			<BaseHeader>My Cards</BaseHeader>
      <Select placeholder="" v-model="accessLevelString" :multiple="false" @input="handleChangeVisibility" :options="availableAccessLevels"></Select>
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
    </div>
</template>

<script lang="ts" setup>
import Button from '@/components/Button.vue';
import CardItem from '@/components/CardItem.vue';
import Select from '@/components/Select.vue';
import BaseHeader from '@/components/typography/BaseHeader.vue';
import { AccessLevel } from '@/models/accessLevel';
import { AmountChangedEvent } from '@/models/amountChangedEvent';
import { Card } from '@/models/card';
import { cardsService } from '@/services/cards';
import { onMounted, Ref, ref } from 'vue';

import { collectionService } from '../services/collection';

const cards: Ref<Card[]> = ref([]);
const accessLevelString: Ref<any[]> = ref([]);
const availableAccessLevels: Ref<string[]> = ref([AccessLevel.notPublic, AccessLevel.private, AccessLevel.public]);

onMounted(async() => {
    //  Fetch cards
    const response = await collectionService.getCollection();
    const cardsIds = response.cards.map(x => x.cardId);
    if(cardsIds.length === 0)
        return;
    const cardResponse = await cardsService.getCards({
        ids: cardsIds.join(',')
    })
    cards.value = cardResponse.content;

    accessLevelString.value = [];
    accessLevelString.value.push({
      label: response.accessLevel
    });
});

const handleChangeVisibility = (x : any) => {
  collectionService.setAccessLevel(x);
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
