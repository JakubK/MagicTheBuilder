<template>
  <div class="register">
    <h2>Account creation</h2>
    <TextInput placeholder="E-mail address"></TextInput>
    <br />
    <TextInput type="password" placeholder="Password"></TextInput>
    <br />
    <TextInput type="password" placeholder="Repeat password"></TextInput>
    <br />
    <div class="register__agreements">
      <CheckBox label="I want to get email notifications about changes in the service" />
      <br/>
      <CheckBox label="I accept that my data will be stored and processed by this service" />
    </div>
    <br />
    <button>Sign up</button>
  </div>
</template>

<script lang="ts" setup>
import TextInput from '@/components/TextInput.vue';
import CheckBox from '@/components/CheckBox.vue';


</script>

<style lang="scss" scoped src="./Register.scss"/>
