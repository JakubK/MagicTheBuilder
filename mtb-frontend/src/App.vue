<template>
  <router-view :key="$route.path"></router-view>
</template>

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
