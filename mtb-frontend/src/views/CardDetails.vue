<template>
  <div class="card-details">
    <nav @click="goBack()" class="card-details__back">
      <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M15 18L9 12L15 6" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
      </svg>
      Back
    </nav>
    <div v-if="card" class="card-details__grid">
        <img class="card-item__image" :src="card.imageUrl"/>
        <table class="card-details__data">
          <tr><td>Title</td><td>{{ card.name }}</td></tr>
          <tr><td>Mana</td><td>{{ card.manaCost }}</td></tr>
          <tr><td>Type</td><td>{{ card.type }}</td></tr>
          <tr><td>Colors</td><td>{{ card.colors.join(', ')}}</td></tr>
          <tr><td>Set</td><td>{{ card.set }}</td></tr>
          <tr><td>Power & Toughness</td><td>{{ card.power }} / {{ card.toughness }}</td></tr>
          <tr><td><b>Description</b></td></tr>
          <tr><td>{{ card.originalText }}</td></tr>
        </table>
        <div class="card-details__usage">
          <span>Used x of owned y in z decks:</span>
          <br/>
          <br/>
          <div class="deck">
            <span class="deck__name">MyAwesomeDeck1</span>
            <div class="deck__controls">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M12 22C17.5228 22 22 17.5228 22 12C22 6.47715 17.5228 2 12 2C6.47715 2 2 6.47715 2 12C2 17.5228 6.47715 22 12 22Z" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <path d="M12 8V16" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <path d="M8 12H16" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
              <span>2</span>
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M12 22C17.5228 22 22 17.5228 22 12C22 6.47715 17.5228 2 12 2C6.47715 2 2 6.47715 2 12C2 17.5228 6.47715 22 12 22Z" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <path d="M8 12H16" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
            </div>
          </div>
        </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { Card } from '@/models/card';
import router from '@/router';
import { cardsService } from '@/services/cards';
import { onMounted, Ref, ref } from 'vue';

const props = defineProps({
  id: {
    type: String,
    required: true
  }
});

const card: Ref<Card | null> = ref(null);

onMounted(async() => {
  //  TODO: Fetch data about card
  card.value = await cardsService.getCard(props.id);
  console.log(card.value);
});



const goBack = () => {
  router.go(-1);
}

</script>

<style lang="scss" scoped src="./CardDetails.scss"/>
