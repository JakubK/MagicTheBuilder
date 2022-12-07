import Select from '@/components/Select.vue';
import { mount } from '@vue/test-utils'
import { test, expect } from 'vitest';

test('Select is not expanded by default', async () => {
    const wrapper = mount(Select);
    expect(wrapper.findAll('.select__options').length).toBe(0);
});

test('Select selection does emit single array when not multiple', async() => {
    const wrapper = mount(Select, {
        props: {
            multiple: false,
            modelValue: [],
            options: [
                {
                    label: 'item1',
                },
                {
                    label: 'item2',
                }
            ]
        }
    });
    
    const activator = wrapper.find('.select__front');
    await activator.trigger('click');

    const options = wrapper.findAll('.select__option');
    await options[0].trigger('click');

    expect(wrapper.findAll('svg').length).toBe(1);
    
});
