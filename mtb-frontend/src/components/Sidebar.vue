<template>
  <nav class="sidebar" :class="{ 'sidebar--active': active }">
    <header class="sidebar__header">
      <Transition>
        <BaseHeader v-if="active">{{ title }}</BaseHeader>
      </Transition>
      <Icon @click="handleArrowClick">
        <div v-html="arrowIcon" class="sidebar__arrow" :class="{ 'sidebar__arrow--active': active }">
        </div>
      </Icon>
    </header>
    <div class="sidebar__content">
      <div class="sidebar__upper-content">
        <component v-for="(item, index) in topItems" :key="index"
          class="sidebar__item"
          :class="{ 'sidebar__item--rolled': !active }"
          :is="item.routerLink ? 'router-link' : 'div'"
          :to="item.routerLink">
            <Icon>
              <span v-html="item.svg"></span>
            </Icon>
            <span v-if="active">{{ item.title }}</span>
        </component>
      </div>
      <div class="sidebar__lower-content">
        <component v-for="(item, index) in bottomItems" :key="index"
          class="sidebar__item"
          :class="{ 'sidebar__item--rolled': !active }"
          :is="item.routerLink ? 'router-link' : 'div'">
            <Icon>
              <span v-html="item.svg"></span>
            </Icon>
            <span v-if="active">{{ item.title }}</span>
        </component>
      </div>
    </div>
  </nav>
</template>

<script lang="ts" setup>
import BaseHeader from './typography/BaseHeader.vue';
import Icon from './Icon.vue';
import { ref, PropType } from 'vue';

import arrowIcon from '@/assets/arrow.svg?raw';

import { SidebarItem } from '@/models/sidebarItem';

const active = ref(true);
defineProps({
  title: String,
  topItems: Array as PropType<SidebarItem[]>,
  bottomItems: Array as PropType<SidebarItem[]>,
  modelValue: Number,
})

const emit = defineEmits([
  'resized',
  'update:modelValue'
]);

const handleArrowClick = () => {
  active.value = !active.value;
  emit('update:modelValue', active.value ? 200 : 50);
};

</script>


<style lang="scss" scoped src="./Sidebar.scss"/>
