<template>
  <div>
    <Sidebar v-model="leftMargin" :top-items="topItems" :bottom-items="bottomItems" title="MagicTheBuilder" />
    <main :style="{left: leftMargin + 'px'}">
      <router-view></router-view>
    </main>
  </div>
</template>

<script setup lang="ts">
import Sidebar from '@/components/Sidebar.vue';
import { SidebarItem } from '@/models/sidebarItem';

import githubIcon from '@/assets/github.svg?raw';
import cogIcon from '@/assets/cog.svg?raw';
import logoutIcon from '@/assets/logout.svg?raw';
import usersIcon from '@/assets/users.svg?raw';
import { ref } from 'vue';

const leftMargin = ref();

const topItems: SidebarItem[] = [
  {
    title: 'Cards',
    svg: usersIcon,
    items: [
      {
        title: 'Collection',
        svg: usersIcon,
        routerLink: '/cards/collection'
      },
      {
        title: 'Browse',
        svg: usersIcon,
        routerLink: '/cards/browse'
      }
    ]
  },
  {
    title: 'Decks',
    svg: usersIcon,
    items: [
      {
        title: 'My decks',
        svg: usersIcon,
        routerLink: '/decks/collection'
      },
      {
        title: 'Community',
        svg: usersIcon,
        routerLink: '/decks/browse'
      }
    ]
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
    routerLink: 'https://github.com/JakubK/MagicTheBuilder',
    isOuterLink: true,
    svg: githubIcon
  },
  {
    title: 'Settings',
    routerLink: '',
    svg: cogIcon
  },
  {
    title: 'Log out',
    routerLink: 'logout',
    svg: logoutIcon
  }
]
</script>

<style>

main {
  position: absolute;
  left: 200px;
  transition: all 0.5s;
}
</style>
