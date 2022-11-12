<template>
	<div class="modal">
		<div class="modal__frame">
			<div class="modal__head">
				<h2>Deck creation</h2>
				<Icon @click="$emit('close')">
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
</template>

<script lang="ts" setup>
import Button from '@/components/Button.vue';
import ErrorLabel from '@/components/ErrorLabel.vue';
import Icon from '@/components/Icon.vue';
import Select from '@/components/Select.vue';
import TextInput from '@/components/TextInput.vue';
import { AccessLevel } from '@/models/accessLevel';
import { CreateDeckRequest } from '@/models/createDeckRequest';
import { Deck } from '@/models/deck';
import { decksService } from '@/services/deck';
import { metaDataService } from '@/services/metaData';
import { mapErrors } from '@/utils/errors';
import useVuelidate from '@vuelidate/core';
import { required } from '@vuelidate/validators';
import { Ref, ref, reactive, onMounted, PropType } from 'vue';

const props = defineProps({
	method: {
		type: String,
		default: 'POST'
	},
	deck: {
		type: Object as PropType<Deck>
	}
});

const emit = defineEmits(['close']);
const accessLevel: Ref<any[]> = ref([]);
const gameMode: Ref<any[]> = ref([]);

onMounted(async() => {
	if(props.method === 'PUT') {
		//	fill form fields
		form.name = props.deck!.name;
		accessLevel.value = [
			{
				checked: true,
				label: props.deck?.accessLevel
			}
		];
		gameMode.value = [
			{
				checked: true,
				label: props.deck?.gameMode
			}
		];

	}
  availableGameModes.value = await metaDataService.getFormats();
});

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
const availableGameModes: Ref<string[]> = ref([]);
const availableAccessLevels: Ref<string[]> = ref([AccessLevel.notPublic, AccessLevel.private, AccessLevel.public]);

const submitDeckCreation = async() => {
  //  Rewrite fields
  form.accessLevel = accessLevel.value[0]?.label;
  form.gameMode = gameMode.value[0]?.label;
  //  Check front validation
  const isValid = await v$.value.$validate();
  if(isValid) {
		if(props.method === 'POST')
    	await decksService.createDeck(form);
		else
			await decksService.updateDeck(props.deck?.id!,form);
		emit('close');
  }
}

</script>

<style lang="scss" scoped src="./DeckModal.scss"/>
