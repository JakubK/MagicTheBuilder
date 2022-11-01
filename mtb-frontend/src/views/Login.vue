<template>
  <div class="login">
    <h2>Logging into existing account</h2>
    <div class="login__form">
      <TextInput :errors="mapErrors(v$.email.$errors)" v-model="form.email" placeholder="E-mail address"></TextInput>
      <br />
      <TextInput :errors="mapErrors(v$.password.$errors)" v-model="form.password" type="password" placeholder="Password"></TextInput>
      <br />
      <div class="login__remember">
        <CheckBox v-model="form.rememberMe" label="Remember me"/>
        <span class="login__forgot">Forgot your password?</span>
      </div>
      <br />
      <Button @click="submitLogin">Sign in</Button>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { reactive } from 'vue';
import useVuelidate from '@vuelidate/core';
import { required, email, minLength } from '@vuelidate/validators'

import TextInput from '@/components/TextInput.vue';
import CheckBox from '@/components/CheckBox.vue';

import { SignIn } from '@/models/signIn';
import { mapErrors} from '@/utils/errors';
import Button from '@/components/Button.vue';
import { authService } from '@/services/auth';
import router from '@/router';

const form = reactive<SignIn>({
  email: '',
  password: '',
  rememberMe: false
});

const rules = {
  email: {
    required, email
  },
  password: {
    required, minLength: minLength(8)
  },
}

const v$ = useVuelidate(rules, form);

const submitLogin = async() => {
  const isValid = await v$.value.$validate();
  if(isValid) {
    //  Send the actual form
    const jwt = await authService.login(form);
    localStorage.setItem('jwt', jwt);
    router.push('/');
  }
}

</script>

<style lang="scss" scoped src="./Login.scss"/>
