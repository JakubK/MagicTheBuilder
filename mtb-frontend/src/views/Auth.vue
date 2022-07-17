<template>
  <div class="auth">
    <div class="auth__wrapper">
      <Login />
      <Register />
      <div class="auth__card" :class="{ 'auth__card--right': right }">
        <Transition>
          <div v-if="right" class="card__sign-in">
            <h2>Got an account already?</h2>
            <p>Sign in here!</p>
            <p>Sign in to browse and manage your cards, organize them into decks and so much more!</p>
            <button @click="right = !right">Switch</button>
          </div>
        </Transition>
        <Transition>
          <div v-if="!right" class="card__sign-up">
            <h2>No account yet?</h2>
            <p>Make one in matter of seconds!</p>
            <p>Sign up to browse and manage your cards, organize them into decks and so much more!</p>
            <button @click="right = !right">Switch</button>
          </div>
        </Transition>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { computed, ref } from 'vue';
import Login from "./Login.vue";
import Register from "./Register.vue";

const right = ref(false);

</script>


<style lang="scss" scoped src="./Auth.scss"/>
