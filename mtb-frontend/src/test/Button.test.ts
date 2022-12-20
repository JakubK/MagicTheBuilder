import { mount } from '@vue/test-utils'
import { test, expect} from 'vitest';
import Button from '../components/Button.vue';

test('Button default does not have ghost styling', async () => {
  const wrapper = mount(Button, {
    props: {
      type: 'default'
    }
  });

  expect(wrapper.classes()).not.toContain('button--ghost');
});


test('Button with ghost option does have ghost styling', async () => {
  const wrapper = mount(Button, {
    props: {
      type: 'ghost'
    }
  });

  expect(wrapper.classes()).toContain('button--ghost');
});


test('Button does render slots', async () => {
  const wrapper = mount(Button, {
    slots: {
      default: 'Submit'
    }
  });

  expect(wrapper.html()).toContain('Submit');
});