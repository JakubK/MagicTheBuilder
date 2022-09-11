<template>
  <div class="decks-view">
    <BaseHeader>{{ title }}</BaseHeader>
    <div class="decks-view__decks">
      <CardItem class="cards-view__card" v-for="index in 10" :key="index" />
    </div>
  </div>
</template>

<script setup lang="ts">
import BaseHeader from '@/components/typography/BaseHeader.vue';

defineProps({
  title: {
    type: String,
    required: true
  }
})
</script>