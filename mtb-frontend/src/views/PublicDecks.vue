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
                        <router-link :to="'/decks/public/' + deck.id">
                            <Icon>
                                <svg width="25" height="25" viewBox="0 0 25 25" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <g clip-path="url(#clip0_607_380)">
                                    <path d="M1.49756 12.192C1.49756 12.192 5.49756 4.19205 12.4976 4.19205C19.4976 4.19205 23.4976 12.192 23.4976 12.192C23.4976 12.192 19.4976 20.192 12.4976 20.192C5.49756 20.192 1.49756 12.192 1.49756 12.192Z" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    <path d="M12.4976 15.192C14.1544 15.192 15.4976 13.8489 15.4976 12.192C15.4976 10.5352 14.1544 9.19205 12.4976 9.19205C10.8407 9.19205 9.49756 10.5352 9.49756 12.192C9.49756 13.8489 10.8407 15.192 12.4976 15.192Z" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                </g>
                                <defs>
                                    <clipPath id="clip0_607_380">
                                    <rect width="24" height="24" fill="black" transform="translate(0.497559 0.192047)"/>
                                    </clipPath>
                                </defs>
                                </svg>
                            </Icon>
                        </router-link>
                        <router-link v-if="!id" :to="'/decks/user/' + deck.ownerId">
                            <Icon>
                                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <path d="M17 21V19C17 17.9391 16.5786 16.9217 15.8284 16.1716C15.0783 15.4214 14.0609 15 13 15H5C3.93913 15 2.92172 15.4214 2.17157 16.1716C1.42143 16.9217 1 17.9391 1 19V21" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                                    <path d="M9 11C11.2091 11 13 9.20914 13 7C13 4.79086 11.2091 3 9 3C6.79086 3 5 4.79086 5 7C5 9.20914 6.79086 11 9 11Z" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                                    <path d="M23 21V19C22.9993 18.1137 22.7044 17.2528 22.1614 16.5523C21.6184 15.8519 20.8581 15.3516 20 15.13" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                                    <path d="M16 3.13C16.8604 3.35031 17.623 3.85071 18.1676 4.55232C18.7122 5.25392 19.0078 6.11683 19.0078 7.005C19.0078 7.89318 18.7122 8.75608 18.1676 9.45769C17.623 10.1593 16.8604 10.6597 16 10.88" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
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

const props = defineProps({
    id: {
        type: String,
        required: false
    }
})

onMounted(async() => {
    if(!props.id)
        decks.value = await decksService.getPublicDecks();
    else
        decks.value = await decksService.getUserDecks(props.id!);
});

</script>

<style lang="scss" scoped src="./Decks.scss"/>
