<template>
  <div class="auth">
    <div class="auth__wrapper">
      <Login />
      <Register />
      <div class="auth__card" :class="{ 'auth__card--right': startRight }">
        <div v-if="!startRight" class="card__text card__sign-in">
          <h2>Got an account already?</h2>
          <p>Sign in here!</p>
          <p>Sign in to browse and manage your cards, organize them into decks and so much more!</p>
          <Button type="ghost" @click="handleClick">Switch</Button>
        </div>
        <div v-else class="card__text card__sign-up">
          <h2>No account yet? <br/> Make one in matter of seconds!</h2>
          <p>Sign up to browse and manage your cards, organize them into decks and so much more!</p>
          <Button type="ghost" @click="handleClick">Switch</Button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { onMounted } from 'vue';
import Login from "./Login.vue";
import Register from "./Register.vue";
import Button from '@/components/Button.vue';

import router from '@/router';

const props = defineProps({
  startRight: {
    type: Boolean,
    required: false,
    default: false,
  }
})

onMounted(() => {
  localStorage.clear();
})

const handleClick = () => {
  if(!props.startRight) {
    router.push('/auth/login');
  }
  else {
    router.push('/auth/register');
  } 
}

</script>

<style lang="scss" scoped src="./Auth.scss"/>
