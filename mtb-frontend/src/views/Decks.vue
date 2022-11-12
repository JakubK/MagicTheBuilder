<template>
  <div class="decks-view">
    <BaseHeader class="decks-view__head">
      {{ title }}
      <div></div>
      <Button @click="toggleModal(true)">New deck</Button>
    </BaseHeader>
    <div v-if="decks.length > 0" class="decks-view__decks">
    </div>
    <div v-else  class="decks-view__empty">
      <div>
        <h4>No decks found</h4>
        <p>Create your first deck</p>
        <Button @click="toggleModal(true)">New deck</Button>
      </div>
    </div>
    <Teleport to="body">
      <div v-if="isModalVisible" class="modal">
        <div class="modal__frame">
          <div class="modal__head">
            <h2>Deck creation</h2>
            <Icon @click="toggleModal(false)">
              <svg width="25" height="25" viewBox="0 0 25 25" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M18.4976 6.49158L6.49756 18.4916" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <path d="M6.49756 6.49158L18.4976 18.4916" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
            </Icon>
          </div>
          <div>
            Deck name
            <TextInput :errors="mapErrors(v$.name.$errors)" v-model="form.name"/>
          </div>
          <div>
            Deck access level
            <Select placeholder="" v-model="accessLevel" :multiple="false" :options="availableAccessLevels"/>
            <ErrorLabel :message="error.message" v-for="(error, index) in mapErrors(v$.accessLevel.$errors)" :key="index"/>
          </div>
          <div>
            Deck GameMode
            <Select :errors="mapErrors(v$.gameMode.$errors)" placeholder="" v-model="gameMode" :multiple="false" :options="availableGameModes"/>
            <ErrorLabel :message="error.message" v-for="(error, index) in mapErrors(v$.gameMode.$errors)" :key="index"/>
          </div>
          <br/>
          <Button @click="submitDeckCreation">Submit</Button>
        </div>
      </div>
    </Teleport>
  </div>
</template>

<script setup lang="ts">
import Button from '@/components/Button.vue';
import Icon from '@/components/Icon.vue';
import Select from '@/components/Select.vue';
import TextInput from '@/components/TextInput.vue';
import BaseHeader from '@/components/typography/BaseHeader.vue';
import { mapErrors} from '@/utils/errors';
import { CreateDeckRequest } from '@/models/createDeckRequest';
import { Deck } from '@/models/deck';
import { decksService } from '@/services/deck';
import { onMounted, reactive, Ref, ref } from 'vue';
import { required } from '@vuelidate/validators'
import { metaDataService } from '@/services/metaData';
import useVuelidate from '@vuelidate/core';
import { AccessLevel } from '@/models/accessLevel';
import ErrorLabel from '@/components/ErrorLabel.vue';

defineProps({
  title: {
    type: String,
    required: true
  }
})

const isModalVisible = ref(false);
const toggleModal = async (shouldBeVisible: boolean) => {
  isModalVisible.value = shouldBeVisible;
  if(shouldBeVisible && availableGameModes.value.length === 0) {
    availableGameModes.value = await metaDataService.getFormats();
  }
}


const accessLevel: Ref<any[]> = ref([]);
const gameMode: Ref<any[]> = ref([]);

const form = reactive<Partial<CreateDeckRequest>>({});
const rules = {
  name: {
    required
  },
  accessLevel: {
    required
  },
  gameMode: {
    required
  }
}
const v$ = useVuelidate(rules, form as any);

const decks: Ref<Deck[]> = ref([]);
const availableGameModes: Ref<string[]> = ref([]);
const availableAccessLevels: Ref<string[]> = ref([AccessLevel.notPublic, AccessLevel.private, AccessLevel.public]);


onMounted(async() => {
  //Fetch decks here
  decks.value = await decksService.getMyDecks();
})

const submitDeckCreation = async() => {
  //  Rewrite fields
  form.accessLevel = accessLevel.value[0]?.label;
  form.gameMode = gameMode.value[0]?.label;
  //  Check front validation
  const isValid = await v$.value.$validate();
  if(isValid) {
    await decksService.createDeck(form);
    isModalVisible.value = false;
  }
}

</script>

<style lang="scss" scoped src="./Decks.scss"/>
