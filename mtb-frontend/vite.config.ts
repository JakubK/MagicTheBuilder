/// <reference types="vitest" />

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
const path = require('path')

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@' : path.resolve(__dirname, './src')
    },
  },
  server: {
    port: 3001
  },
  test: {
    globals: true,
    environment: 'happy-dom'
  }
})
