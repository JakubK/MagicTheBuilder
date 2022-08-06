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
            component: Cards,
            props: {
              title: 'My cards collection'
            }
          },
          {
            path: 'browse',
            component: Cards,
            props: {
              title: 'All cards'
            }
          }
        ]
      },
      {
        path: 'decks',
        redirect: 'decks/collection',
        children: [
          {
            path: 'collection',
            component: Cards,
            props: {
              title: 'My decks'
            }
          },
          {
            path: 'browse',
            component: Cards,
            props: {
              title: 'Community decks'
            }
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
