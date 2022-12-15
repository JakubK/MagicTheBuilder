import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import Auth from "@/views/Auth.vue";
import CardDetails from "@/views/CardDetails.vue";
import Cards from "@/views/Cards.vue";
import Main from "@/views/Main.vue";
import Decks from "@/views/Decks.vue";
import CardsCollection from "@/views/CardsCollection.vue";
import PublicCardsCollection from "@/views/PublicCardsCollection.vue";
import PublicDeckCards from "@/views/PublicDeckCards.vue";

import { CardSource } from "@/models/cardSource";
import DeckCards from "@/views/DeckCards.vue";
import PublicDecks from "@/views/PublicDecks.vue";

const routes: RouteRecordRaw[] = [
  {
    path: '/auth/login',
    component: Auth,
    props: {
      startRight: true
    }
  },
  {
    path: '/auth/register',
    component: Auth,
    props: {
      startRight: false
    }
  },
  {
    path: '/',
    component: Main,
    meta: {
      requiresAuth: true
    },
    children: [
      {
        path: 'cards',
        name: 'dashboard',
        redirect: 'cards/collection',
        children: [
          {
            path: 'collection',
            component: CardsCollection,
          },
          {
            path: 'collection/:id',
            component: PublicCardsCollection,
            props: true
          },
          {
            path: 'browse',
            component: Cards,
            props: {
              title: 'All cards',
              cardSource: CardSource.Collection
            }
          },
          {
            path: ':id',
            component: CardDetails,
            props: true
          }
        ]
      },
      {
        path: 'decks',
        redirect: 'decks/collection',
        children: [
          {
            path: ':id',
            component: DeckCards,
            props: true,
          },
          {
            path: 'deck/:id',
            component: Cards,
            props: {
              title: 'Query cards for your Deck',
              cardSource: CardSource.Deck
            }
          },
          {
            path: 'public/:id',
            component: PublicDeckCards,
            props: true
          },
          {
            path: 'user/:id',
            component: PublicDecks,
            props: true
          },
          {
            path: 'side/:id',
            component: Cards,
            props: {
              title: 'Query cards for your Sideboard',
              cardSource: CardSource.Sideboard
            }
          },
          {
            path: 'collection',
            component: Decks,
            props: {
              title: 'My decks'
            }
          },
          {
            path: 'browse',
            component: PublicDecks,
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
    redirect: '/auth/login'
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
});


router.beforeEach((to, _) => {
  if(to.meta.requiresAuth && !localStorage.getItem('jwt')) {
    return {
      path: '/auth/login',
      query: { redirect: to.fullPath }
    }
  }
});

export default router;
