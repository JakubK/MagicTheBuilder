<template>
    <div class="modal">
        <div class="modal__frame">
            <div class="modal__head">
                <h2>Deck validation errors</h2>
                <Icon @click="$emit('close')">
					<svg width="25" height="25" viewBox="0 0 25 25" fill="none" xmlns="http://www.w3.org/2000/svg">
						<path d="M18.4976 6.49158L6.49756 18.4916" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
						<path d="M6.49756 6.49158L18.4976 18.4916" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
					</svg>
				</Icon>
            </div>
            <div>
                <div v-for="(error, index) in errors" :key="index">
                    {{ error }}
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { PropType } from 'vue';

defineProps({
    errors: {
        type: Array as PropType<Array<string>>,
        required: false,
        default: []
    }
})
defineEmits(['close']);

</script>

<style lang="scss" scoped src="./Modal.scss"/>
