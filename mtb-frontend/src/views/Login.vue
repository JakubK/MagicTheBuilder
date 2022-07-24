<template>
  <div class="login">
    <h2>Logging into existing account</h2>
    <div class="login__form">
      <TextInput placeholder="E-mail address"></TextInput>
      <br />
      <TextInput type="password" placeholder="Password"></TextInput>
      <br />
      <div class="login__remember">
        <CheckBox label="Remember me"/>
        <span>Forgot your password?</span>
      </div>
      <br />
      <button>Sign in</button>
    </div>
  </div>
</template>

<script lang="ts" setup>
import TextInput from '@/components/TextInput.vue';
import CheckBox from '@/components/CheckBox.vue';

</script>

<style lang="scss" scoped src="./Login.scss"/>
