<template>
  <component @click="clicked = !clicked" class="sidebar-item" :class="{ 'sidebar-item--rolled': !active }"
    :is="resolveComponent()" :to="item!.routerLink" :href="item!.routerLink" :target="resolveTarget()">
    <Icon>
      <div v-html="item!.svg" />
    </Icon>
    <div class="sidebar-item__heading">
      <span v-if="active">{{ item!.title }}</span>
      <Icon v-if="item?.items && active">
        <div class="sidebar-item__icon" :class="{ 'sidebar-item__icon--rolled': !clicked }" v-html="arrowIcon">
        </div>
      </Icon>
    </div>
  </component>
  <Transition>
    <div v-if="clicked">
      <SidebarItemVue v-for="(subItem, index) in item?.items" :item="subItem" :active="active" :key="index" />
    </div>
  </Transition>
</template>

<script lang="ts" setup>
import { PropType, ref } from 'vue';
import SidebarItemVue from './SidebarItem.vue';
import Icon from './Icon.vue';
import { SidebarItem } from '@/models/sidebarItem';
import arrowIcon from '@/assets/chevron-down.svg?raw';

const props = defineProps({
  item: Object as PropType<SidebarItem>,
  active: Boolean
})

const clicked = ref(false);

const resolveComponent = () => {
  if (props.item?.isOuterLink)
    return 'a';
  return props.item!.routerLink ? 'router-link' : 'div';
}

const resolveTarget = () => {
  if (props.item?.isOuterLink)
    return '_blank';
  return '_self';
}

</script>


<style lang="scss" scoped src="./SidebarItem.scss"/>
