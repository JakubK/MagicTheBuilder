import HelloWorld from "@/components/HelloWorld.vue";
import Cards from "@/views/Cards.vue";
import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: HelloWorld
  },
  {
    path: '/cards',
    component: Cards
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
