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
        redirect: 'cards/collection',
        children: [
          {
            path: 'collection',
            component: Cards
          },
          {
            path: 'browse',
            component: Cards
          }
        ]
      },
      {
        path: 'decks',
        redirect: 'decks/collection',
        children: [
          {
            path: 'collection',
            component: Cards
          },
          {
            path: 'browse',
            component: Cards
          }
        ]
      }
    ]
  },
  {
    path: '/logout',
    redirect: '/auth',
    beforeEnter: () => localStorage.clear()
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
