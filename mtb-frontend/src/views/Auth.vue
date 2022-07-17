<template>
  <div class="auth">
    <div class="auth__wrapper">
      <Login/>
      <Register/>
      <div class="auth__card" :class="{'auth__card--right': right}">
        <button @click="right = !right">Switch</button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import Login from "./Login.vue";
import Register from "./Register.vue";

const right = ref(false);

</script>

<style lang="scss" scoped src="./Auth.scss"/>
