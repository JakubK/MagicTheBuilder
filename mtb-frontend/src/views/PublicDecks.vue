<template>
    <div class="decks-view">
        <BaseHeader class="decks-view__head">List of public decks</BaseHeader>
        <div v-if="decks.length > 0" class="decks-view__decks">
            <div class="deck" v-for="deck in decks" :key="deck.id">
                <div class="deck__features">
                    <span>{{ deck.gameMode }}</span>
                </div>
                <br/>
                <div class="deck__content">
                    <span>{{ deck.name }}</span>
                    <span class="deck__icons">
                        <router-link :to="'/decks/' + deck.id ">
                            <Icon>
                                <svg width="25" height="25" viewBox="0 0 25 25" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <g clip-path="url(#clip0_607_380)">
                                    <path d="M1.49756 12.192C1.49756 12.192 5.49756 4.19205 12.4976 4.19205C19.4976 4.19205 23.4976 12.192 23.4976 12.192C23.4976 12.192 19.4976 20.192 12.4976 20.192C5.49756 20.192 1.49756 12.192 1.49756 12.192Z" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    <path d="M12.4976 15.192C14.1544 15.192 15.4976 13.8489 15.4976 12.192C15.4976 10.5352 14.1544 9.19205 12.4976 9.19205C10.8407 9.19205 9.49756 10.5352 9.49756 12.192C9.49756 13.8489 10.8407 15.192 12.4976 15.192Z" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                </g>
                                <defs>
                                    <clipPath id="clip0_607_380">
                                    <rect width="24" height="24" fill="white" transform="translate(0.497559 0.192047)"/>
                                    </clipPath>
                                </defs>
                                </svg>
                            </Icon>
                        </router-link>
                    </span>
                </div>
            </div>
        </div>
        <div v-else>
            List is empty
        </div>
    </div>
</template>

<script lang="ts" setup>
import { onMounted, Ref, ref } from 'vue';
import { Deck } from '@/models/deck';
import { decksService } from '@/services/deck';
import BaseHeader from '@/components/typography/BaseHeader.vue';
import Icon from '@/components/Icon.vue';

const decks: Ref<Deck[]> = ref([]);

onMounted(async() => {
    decks.value = await decksService.getPublicDecks();
});

</script>

<style lang="scss" scoped src="./Decks.scss"/>
