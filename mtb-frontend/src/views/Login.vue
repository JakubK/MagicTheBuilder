<template>
  <div class="login">
    <h2>Logging into existing account</h2>
    <div class="login__form">
      <TextInput v-model="form.email" placeholder="E-mail address"></TextInput>
      <br />
      <TextInput v-model="form.password" type="password" placeholder="Password"></TextInput>
      <br />
      <div class="login__remember">
        <CheckBox v-model="form.rememberMe" label="Remember me"/>
        <span>Forgot your password?</span>
      </div>
      <br />
      <button @click="submitLogin">Sign in</button>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { reactive } from 'vue';
import useVuelidate from '@vuelidate/core';
import { required, email } from '@vuelidate/validators'

import TextInput from '@/components/TextInput.vue';
import CheckBox from '@/components/CheckBox.vue';
import { SignIn } from '@/models/signIn';

const form = reactive<SignIn>({
  email: '',
  password: '',
  rememberMe: false
});

const rules = {
  email: {
    required, email
  },
  password: required,
}

const v$ = useVuelidate(rules, form);

const submitLogin = async() => {
  const isValid = await v$.value.$validate();
  if(isValid) {
    //  Send the actual form
  }
}

</script>

<style lang="scss" scoped src="./Login.scss"/>
