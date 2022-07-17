import Auth from "@/views/Auth.vue";
import Cards from "@/views/Cards.vue";
import Main from "@/views/Main.vue";
import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";

const routes: RouteRecordRaw[] = [
  {
    path: '/auth',
    component: Auth
  },
  {
    path: '/',
    component: Main,
    children: [
      {
        path: 'cards',
        component: Cards
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
