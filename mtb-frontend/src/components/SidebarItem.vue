<template>
  <component class="sidebar-item" :class="{ 'sidebar-item--rolled': !active }"
    :is="item!.routerLink ? 'router-link' : 'div'" :to="item!.routerLink">
    <Icon>
      <span v-html="item!.svg"></span>
    </Icon>
    <span v-if="active">{{ item!.title }}</span>
  </component>
</template>

<script lang="ts" setup>
import { SidebarItem } from '@/models/sidebarItem';
import { PropType } from 'vue';

defineProps({
  item: Object as PropType<SidebarItem>,
  active: Boolean
})

</script>

<style lang="scss" scoped src="./SidebarItem.scss"/>
