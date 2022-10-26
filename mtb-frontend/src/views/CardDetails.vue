<template>
  <div class="card-details">
    <nav @click="goBack()" class="card-details__back">
      <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M15 18L9 12L15 6" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
      </svg>
      Back
    </nav>
    <div class="card-details__grid">
        <img class="card-item__image" src="@/assets/cardMock.png"/>
        <table class="card-details__data">
          <tr><td>Title</td><td>Sword of Light and Shadow</td></tr>
          <tr><td>Mana</td><td>Lorem ipsum</td></tr>
          <tr><td>Type</td><td>Lorem ipsum</td></tr>
          <tr><td>Color</td><td>Lorem ipsum</td></tr>
          <tr><td>Expansion</td><td>Lorem ipsum</td></tr>
          <tr><td>Power & Toughness</td><td>Lorem ipsum</td></tr>
          <tr><td>Description</td><td>Lorem ipsum</td></tr>
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
import router from '@/router';
import { onMounted } from 'vue';

onMounted(async() => {
  //  TODO: Fetch data about card
})

defineProps({
  id: {
    type: Number,
    required: true
  }
});

const goBack = () => {
  router.go(-1);
}

</script>

<style lang="scss" scoped src="./CardDetails.scss"/>
