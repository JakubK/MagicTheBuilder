import { mount } from '@vue/test-utils'
import { test, expect} from 'vitest';
import CheckBox from '../components/CheckBox.vue';

test('CheckBox default does render given label prop', async () => {
    const labelText = 'Option 1'; 
    const wrapper = mount(CheckBox, {
      props: {
        label: labelText
      }
    });

    expect(wrapper.text()).toContain(labelText);
});

test('CheckBox default does not render errors', async () => {
    const wrapper = mount(CheckBox);
    expect(wrapper.findAll('.error-label').length).toBe(0);
});

test('CheckBox with errors does render them', async () => {
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
    const wrapper = mount(CheckBox, {
        props: {
            errors 
        }
    });
    expect(wrapper.findAll('.error-label').length).toBe(errors.length);
});

test('CheckBox does react on error changes', async () => {
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
    const wrapper = mount(CheckBox, {
        props: {
            errors 
        }
    });
    await wrapper.setProps({
        errors: []
    });
    expect(wrapper.findAll('.error-label').length).toBe(0);
});

test('Checkbox modelValue renders check', async() => {
    const wrapper = mount(CheckBox, {
        props: {
            modelValue: true 
        }
    });

    expect(wrapper.find('.checkbox__check').exists()).toBe(true);

    await wrapper.setProps({
        modelValue: false
    });

    expect(wrapper.find('.checkbox__check').exists()).toBe(false);
})


test('Checkbox click emits negation of old modelValue', async() => {
    const modelValue = true;
    const wrapper = mount(CheckBox, {
        props: {
            modelValue 
        }
    });

    await wrapper.find('.checkbox__circle').trigger('click');

    expect(wrapper.emitted()['update:modelValue']).toEqual([[!modelValue]]);
})