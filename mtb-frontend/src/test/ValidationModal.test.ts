import { mount } from '@vue/test-utils'
import { test, expect } from 'vitest';
import ValidationModal from '../views/ValidationModal.vue';

test('ValidationModal displays error', async () => {
    const wrapper = mount(ValidationModal, {
    props: {
        errors: ['error1', 'error2', 'error3']
    }
    });
    expect(wrapper.findAll('.modal__error').length).toBe(3);
});

test('ValidationModal emits close after clicking on cross', async () => {
    const wrapper = mount(ValidationModal);
    await wrapper.find('.icon').trigger('click');
    expect(wrapper.emitted()['close']).toStrictEqual([[]]);
});