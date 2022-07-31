<template>
  <button class="button" :class="{'button--ghost' : type === 'ghost'}">
    <slot></slot>
  </button>
</template>

<script lang="ts" setup>
import { PropType } from 'vue';

type ButtonType = 'default' | 'ghost';

defineProps({
  type: {
    type: Object as PropType<ButtonType>,
    required: false,
    default: 'default'
  },
})

</script>

<style lang="scss" scoped src="./Button.scss"/>
