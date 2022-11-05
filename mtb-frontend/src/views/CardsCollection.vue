<template>
    <div class="card-collection">
        <BaseHeader>My Cards</BaseHeader>
        <div class="cards-collection__cards">
            <CardItem class="cards-collection__card" v-for="(card, index) in cards" :card="card" :key="index" />
        </div>
    </div>
</template>

<script lang="ts" setup>
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
    const cardResponse = await cardsService.getCards({
        ids: cardsIds.join(',')
    })
    cards.value = cardResponse.content;
})

</script>

<style lang="scss" scoped src="./CardsCollection.scss"/>
