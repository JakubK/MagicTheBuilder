<template>
  <div class="card-item" :class="{ 'card-item--flipped': flipped }">
    <div class="card-item__content">
      <div @click="flipped = !flipped" class="card-item__front">
        <img class="card-item__image" src="@/assets/cardMock.png"/>
      </div>
      <div class="card-item__back card-reverse">
        <img class="card-item__image" src="@/assets/cardMock.png"/>
        <div class="card-reverse__blur"></div>
        <header class="card-reverse__head">
          <div class="card-reverse__title">
            Sword of Light and Shadow
          </div>
        </header>
        <footer>
          <div class="card-reverse__actions">
            <div class="card-reverse__action">
              <router-link :to="`/cards/${id}`">
                <Icon>
                  <svg width="32" height="32" viewBox="0 0 32 32" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path
                      d="M16.0001 29.3333C23.3639 29.3333 29.3334 23.3638 29.3334 16C29.3334 8.63621 23.3639 2.66667 16.0001 2.66667C8.63628 2.66667 2.66675 8.63621 2.66675 16C2.66675 23.3638 8.63628 29.3333 16.0001 29.3333Z"
                      stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                    <path d="M16 21.3333V16" stroke="white" stroke-width="2" stroke-linecap="round"
                      stroke-linejoin="round" />
                    <path d="M16 10.6667H16.0133" stroke="white" stroke-width="2" stroke-linecap="round"
                      stroke-linejoin="round" />
                  </svg>
                </Icon>
              </router-link>
            </div>
            <Icon @click="flipped = !flipped" class="card-reverse__flip">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M17 1L21 5L17 9" stroke="white" stroke-width="2" stroke-linecap="round"
                  stroke-linejoin="round" />
                <path d="M3 11V9C3 7.93913 3.42143 6.92172 4.17157 6.17157C4.92172 5.42143 5.93913 5 7 5H21"
                  stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                <path d="M7 23L3 19L7 15" stroke="white" stroke-width="2" stroke-linecap="round"
                  stroke-linejoin="round" />
                <path d="M21 13V15C21 16.0609 20.5786 17.0783 19.8284 17.8284C19.0783 18.5786 18.0609 19 17 19H3"
                  stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
              </svg>
            </Icon>
            <div class="card-reverse__counter">
              <div class="card-reverse__action">
                <svg width="32" height="32" viewBox="0 0 32 32" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path
                    d="M16.0001 29.3333C23.3639 29.3333 29.3334 23.3638 29.3334 16C29.3334 8.63621 23.3639 2.66667 16.0001 2.66667C8.63628 2.66667 2.66675 8.63621 2.66675 16C2.66675 23.3638 8.63628 29.3333 16.0001 29.3333Z"
                    stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                  <path d="M16 10.6667V21.3333" stroke="white" stroke-width="2" stroke-linecap="round"
                    stroke-linejoin="round" />
                  <path d="M10.6667 16H21.3334" stroke="white" stroke-width="2" stroke-linecap="round"
                    stroke-linejoin="round" />
                </svg>
              </div>
              <div class="card-reverse__action card-reverse__count">
                5
              </div>
              <div class="card-reverse__action">
                <svg width="32" height="32" viewBox="0 0 32 32" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path
                    d="M16.0001 29.3333C23.3639 29.3333 29.3334 23.3638 29.3334 16C29.3334 8.63621 23.3639 2.66667 16.0001 2.66667C8.63628 2.66667 2.66675 8.63621 2.66675 16C2.66675 23.3638 8.63628 29.3333 16.0001 29.3333Z"
                    stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                  <path d="M10.6667 16H21.3334" stroke="white" stroke-width="2" stroke-linecap="round"
                    stroke-linejoin="round" />
                </svg>
              </div>
            </div>
          </div>
        </footer>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import Icon from './Icon.vue';

defineProps({
  id: {
    type: Number,
    required: false,
    default: 1
  }
});

const flipped = ref(false);
</script>

<style lang="scss" scoped src="./CardItem.scss"/>
