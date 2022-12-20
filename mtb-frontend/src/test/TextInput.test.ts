import { mount } from '@vue/test-utils'
import { test, expect } from 'vitest';
import TextInput from '../components/TextInput.vue';

test('TextInput default does not render errors', async () => {
    const wrapper = mount(TextInput);
    expect(wrapper.findAll('.error-label').length).toBe(0);
});

test('TextInput with errors does render them', async () => {
    const errors = [
        {
            message: 'Error'
        },
        {
            message: 'Error'
        },
        {
            message: 'Error'
        }
    ]
    const wrapper = mount(TextInput, {
        props: {
            errors 
        }
    });
    expect(wrapper.findAll('.error-label').length).toBe(errors.length);
});

test('TextInput does react on error changes', async () => {
    const errors = [
        {
            message: 'Error'
        },
        {
            message: 'Error'
        },
        {
            message: 'Error'
        }
    ]
    const wrapper = mount(TextInput, {
        props: {
            errors 
        }
    });
    await wrapper.setProps({
        errors: []
    });
    expect(wrapper.findAll('.error-label').length).toBe(0);
});

test('TestInput does render placeholder', async() => {
    const placeholder = 'This is the placeholder for input';
    const wrapper = mount(TextInput, {
        props: {
            placeholder 
        }
    });

    expect(wrapper.find('.textinput__input').attributes('placeholder')).toBe(placeholder);
});

test('TestInput does apply passed type', async() => {
    const type = 'text';
    const wrapper = mount(TextInput, {
        props: {
            type 
        }
    });

    expect(wrapper.find('.textinput__input').attributes('type')).toBe(type);
})

test('TestInput produces new modelValue on typing text', async() => {
    const wrapper = mount(TextInput, {
        props: {
            modelValue: 'old'
        }
    });

    wrapper.find('.textinput__input').setValue('new');
    expect(wrapper.emitted()['update:modelValue']).toMatchObject([['new']]);
})
