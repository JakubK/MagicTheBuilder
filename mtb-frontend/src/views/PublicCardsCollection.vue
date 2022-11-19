<template>
    <div class="card-collection">
		<BaseHeader>Public Cards Collection contents</BaseHeader>
        <div class="card-collection__cards" v-if="cards.length > 0">
            <CardItem
                class="card-collection__card"
                v-for="card in cards" :card="card" 
                :key="card.id"
                :read-only="true"/>
        </div>
        <Button v-if="showMoreBtn" @click="loadMore">Load more</Button>
    </div>
</template>

<script lang="ts" setup>
import Button from '@/components/Button.vue';
import CardItem from '@/components/CardItem.vue';
import BaseHeader from '@/components/typography/BaseHeader.vue';
import { Card } from '@/models/card';
import { cardsService } from '@/services/cards';
import { collectionService } from '@/services/collection';
import { onMounted, Ref, ref } from 'vue';

const props = defineProps({
    id: {
        type: String,
        required: true
    }
});

const page = ref(0);
const handleGetRequest = async() => {
  showMoreBtn.value = true;
  const response = await collectionService.getUserCollection(props.id, page.value, 20);
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

onMounted(async() => {
    await handleGetRequest();
});

const cards: Ref<Card[]> = ref([]);
const showMoreBtn = ref(false);
const loadMore = async() => {
    page.value++;
    await handleGetRequest();
}
</script>

<style lang="scss" scoped src="./CardsCollection.scss"/>
