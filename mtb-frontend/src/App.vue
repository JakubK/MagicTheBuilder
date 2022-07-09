<template>
  <div>
    <Sidebar :top-items="topItems" :bottom-items="bottomItems" title="MagicTheBuilder" />
    <main style="position: absolute; left: 200px;">
      <router-view></router-view>
    </main>
  </div>
</template>

<script setup lang="ts">
import Sidebar from './components/Sidebar.vue';
import { SidebarItem } from './models/sidebarItem';

import githubIcon from '@/assets/github.svg?raw';
import cogIcon from '@/assets/cog.svg?raw';
import logoutIcon from '@/assets/logout.svg?raw';
import usersIcon from '@/assets/users.svg?raw';

const topItems: SidebarItem[] = [
  {
    title: 'Cards',
    routerLink: '',
    svg: usersIcon
  },
  {
    title: 'Decks',
    routerLink: '',
    svg: usersIcon
  },
  {
    title: 'Friends',
    routerLink: '',
    svg: usersIcon
  }
]

const bottomItems: SidebarItem[] = [
  {
    title: 'Github',
    routerLink: '',
    svg: githubIcon
  },
  {
    title: 'Settings',
    routerLink: '',
    svg: cogIcon
  },
  {
    title: 'Log out',
    routerLink: '',
    svg: logoutIcon
  }
]
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
