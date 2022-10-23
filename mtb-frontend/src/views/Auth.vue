<template>
  <div class="auth">
    <div class="auth__wrapper">
      <Login />
      <Register />
      <div class="auth__card" :class="{ 'auth__card--right': right }">
        <div v-if="right" class="card__text card__sign-in">
          <h2>Got an account already?</h2>
          <p>Sign in here!</p>
          <p>Sign in to browse and manage your cards, organize them into decks and so much more!</p>
          <Button type="ghost" @click="right = !right">Switch</Button>
        </div>
        <div v-if="!right" class="card__text card__sign-up">
          <h2>No account yet? <br/> Make one in matter of seconds!</h2>
          <p>Sign up to browse and manage your cards, organize them into decks and so much more!</p>
          <Button type="ghost" @click="right = !right">Switch</Button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import Login from "./Login.vue";
import Register from "./Register.vue";
import Button from '@/components/Button.vue';

const props = defineProps({
  startRight: {
    type: Boolean,
    required: false,
    default: false,
  }
})

const right = ref(props.startRight);

</script>

<style lang="scss" scoped src="./Auth.scss"/>
