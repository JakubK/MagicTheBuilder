<template>
  <div>
    <Sidebar v-model="leftMargin" :top-items="topItems" :bottom-items="bottomItems" title="MagicTheBuilder" />
    <main :style="{left: leftMargin + 'px', width: 'calc(100% - ' + leftMargin+'px)'}">
      <router-view :key="$route.path"></router-view>
    </main>
  </div>
</template>

<script setup lang="ts">
import Sidebar from '@/components/Sidebar.vue';
import { SidebarItem } from '@/models/sidebarItem';

import githubIcon from '@/assets/github.svg?raw';
import logoutIcon from '@/assets/logout.svg?raw';
import usersIcon from '@/assets/users.svg?raw';
import { onMounted, ref } from 'vue';
import folderIcon from '@/assets/folder.svg?raw';
import fileIcon from '@/assets/file.svg?raw';
import { collectionService } from '@/services/collection';

const leftMargin = ref();

onMounted(async() => {
  //  verify JWT by calling empty authorized GET
  await collectionService.getCardAmountInCollection('-1');
});

const topItems: SidebarItem[] = [
  {
    title: 'Cards',
    svg: fileIcon,
    items: [
      {
        title: 'Collection',
        svg: folderIcon,
        routerLink: '/cards/collection'
      },
      {
        title: 'Browse',
        svg: fileIcon,
        routerLink: '/cards/browse'
      }
    ]
  },
  {
    title: 'Decks',
    svg: folderIcon,
    items: [
      {
        title: 'My decks',
        svg: folderIcon,
        routerLink: '/decks/collection'
      },
      {
        title: 'Community',
        svg: usersIcon,
        routerLink: '/decks/browse'
      }
    ]
  },
]

const bottomItems: SidebarItem[] = [
  {
    title: 'Github',
    routerLink: 'https://github.com/JakubK/MagicTheBuilder',
    isOuterLink: true,
    svg: githubIcon
  },
  {
    title: 'Log out',
    routerLink: '/logout',
    svg: logoutIcon
  }
]
</script>

<style>

main {
  position: absolute;
  left: 200px;
  width: calc(100% - 200px);
  transition: all 0.5s;
}
</style>
