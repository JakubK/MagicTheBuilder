<template>
  <div class="register">
    <h2>Account creation</h2>
    <TextInput placeholder="E-mail address"></TextInput>
    <br/>
    <TextInput placeholder="Password"></TextInput>
    <br/>
    <TextInput placeholder="Repeat password"></TextInput>
    <br/>
    <div class="register__agreements">
      <div>
        <input type="checkbox" />
        <span>I want to get email notifications about changes in the service</span>
      </div>
      <div>
        <input type="checkbox" />
        <span>I accept that my data will be stored and processed by this service</span>
      </div>
    </div>
    <br/>
    <button>Sign up</button>
  </div>
</template>

<script lang="ts" setup>
import TextInput from '@/components/TextInput.vue';

</script>

<style lang="scss" scoped src="./Register.scss"/>
