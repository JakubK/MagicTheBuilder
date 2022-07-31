<template>
  <div class="register">
    <h2>Account creation</h2>
    <TextInput v-model="form.email" placeholder="E-mail address"/>
    <br />
    <TextInput v-model="form.password" type="password" placeholder="Password"/>
    <br />
    <TextInput v-model="form.passwordRepeat" type="password" placeholder="Repeat password"/>
    <br />
    <div class="register__agreements">
      <CheckBox v-model="form.allowNotifications" label="I want to get email notifications about changes in the service" />
      <br/>
      <CheckBox v-model="form.allowDataProcessing" label="I accept that my data will be stored and processed by this service" />
    </div>
    <br />
    <button @click="submitRegister">Sign up</button>
  </div>
</template>

<script lang="ts" setup>
import { reactive } from 'vue';
import useVuelidate from '@vuelidate/core';
import { required, email } from '@vuelidate/validators';

import TextInput from '@/components/TextInput.vue';
import CheckBox from '@/components/CheckBox.vue';
import { SignUp } from '@/models/signUp';
import { areSame, mustBeTrue } from '@/utils/valitators';

const form = reactive<SignUp>({
  email: '',
  password: '',
  passwordRepeat: '',
  allowDataProcessing: false,
  allowNotifications: false
});


const rules = {
  email: {
    required, email
  },
  password: required,
  passwordRepeat: { 
    required,
    sameAs: areSame(form.password, form.passwordRepeat)
  },
  allowDataProcessing: {
    mustBeTrue,
  }
}

const v$ = useVuelidate(rules, form);

const submitRegister = async() => {
  const isValid = await v$.value.$validate();
  if(isValid) {
    //  Send the actual form
  } 
}

</script>

<style lang="scss" scoped src="./Register.scss"/>
