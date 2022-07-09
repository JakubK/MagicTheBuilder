<template>
  <div>
    <Sidebar title="MagicTheBuilder"/>
    <main style="position: absolute; left: 200px;">
      <router-view></router-view>
    </main>
  </div>
</template>

<script setup lang="ts">
  import Sidebar from './components/Sidebar.vue';

</script>

<style>
body {
  margin: 0;
}

*,
*::before,
*::after {
  box-sizing: border-box;
}
</style>
