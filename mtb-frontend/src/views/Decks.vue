<template>
  <div class="decks-view">
    <BaseHeader>{{ title }}</BaseHeader>
    <div class="decks-view__decks">
    </div>
  </div>
</template>

<script setup lang="ts">
import BaseHeader from '@/components/typography/BaseHeader.vue';
import CardItem from '@/components/CardItem.vue';
import { onMounted } from 'vue';

defineProps({
  title: {
    type: String,
    required: true
  }
})

onMounted(async() => {
  //Fetch decks here
})
</script>

<style lang="scss" scoped src="./Decks.scss"/>
