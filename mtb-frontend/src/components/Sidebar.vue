<template>
  <nav class="sidebar" :class="{ 'sidebar--active': active }">
    <header class="sidebar__header">
      <Transition>
        <BaseHeader v-if="active">{{ title }}</BaseHeader>
      </Transition>
      <Icon @click="active = !active">
        <svg class="sidebar__arrow" :class="{ 'sidebar__arrow--active': active }" width="24" height="24"
          viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path d="M19 12L5 12" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
          <path d="M12 19L5 12L12 5" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
        </svg>
      </Icon>
    </header>
    <div class="sidebar__content">
      <div class="sidebar__upper-content">
        <div v-for="(item, index) in topItems" :key="index" 
          class="sidebar__item"
          :class="{ 'sidebar__item--rolled': !active }">
          <Icon>
            <span v-html="item.svg"></span>
          </Icon>
          <span v-if="active">{{ item.title }}</span>
        </div>
      </div>
      <div class="sidebar__lower-content">
        <div v-for="(item, index) in bottomItems" :key="index" 
          class="sidebar__item"
          :class="{ 'sidebar__item--rolled': !active }">
          <Icon>
            <span v-html="item.svg"></span>
          </Icon>
          <span v-if="active">{{ item.title }}</span>
        </div>
      </div>
    </div>
  </nav>
</template>

<script lang="ts" setup>
import BaseHeader from './typography/BaseHeader.vue';
import Icon from './Icon.vue';
import { ref, PropType } from 'vue';

import { SidebarItem } from '@/models/sidebarItem';

const active = ref(true);
defineProps({
  title: String,
  topItems: Array as PropType<SidebarItem[]>,
  bottomItems: Array as PropType<SidebarItem[]>
})


</script>



<style lang="scss" scoped src="./Sidebar.scss"/>
