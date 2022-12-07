import { mount } from '@vue/test-utils'
import { test, expect } from 'vitest';
import SidebarItem from '../components/SidebarItem.vue';

test('SidebarItem with outerlink has <a> in root', async () => {
    const wrapper = mount(SidebarItem, {
        props: {
            item: {
                isOuterLink: true
            }
        }
    });
    expect(wrapper.findAll('a').length).toBe(1);
});


test('SidebarItem without link has <div> in root', async() => {

});

test('SidebarItem with outer link redirects to new tab', async() => {
    const wrapper = mount(SidebarItem, {
        props: {
            item: {
                isOuterLink: true
            }
        }
    });
    const link = wrapper.find('a');
    expect(link.attributes('target')).toBe('_blank');
});

test('SidebarItem with children is not expanded by default', async() => {
    const wrapper = mount(SidebarItem, {
        props: {
            item: {
                isOuterLink: true,
                items: [
                    {
                        title: 'item1'
                    },
                    {
                        title: 'item2'
                    }
                ]
            }
        }
    });

    expect(wrapper.findAll('.sidebar-item').length).toBe(1);
});