<template>
  <div class="error-label">
    {{ message }}
  </div>
</template>

<script lang="ts" setup>
  defineProps({
    message: String
  });
</script>

<style lang="scss" scoped src="./ErrorLabel.scss"/>
