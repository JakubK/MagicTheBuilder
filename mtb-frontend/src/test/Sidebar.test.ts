import { mount } from '@vue/test-utils'
import { test, expect } from 'vitest';
import Sidebar from '../components/Sidebar.vue';

test('Sidebar is expanded by default', async () => {
    const wrapper = mount(Sidebar);
    expect(wrapper.findAll('.sidebar--active').length).toBe(1)
});

test('Clicking on arrow hides the Sidebar', async () => {
    const wrapper = mount(Sidebar);
    await wrapper.find('.sidebar__arrow').trigger('click');
    expect(wrapper.findAll('.sidebar--active').length).toBe(0)
})

test('Hidden Sidebar does not render title', async () => {
    const title = 'Sidebar title content'
    const wrapper = mount(Sidebar, {
        props: {
            title
        }
    });
    await wrapper.find('.sidebar__arrow').trigger('click');
    expect(wrapper.find('.sidebar__header').text()).not.toContain(title);
});

test('Sidebar does render both top and bottom items', async () => {
    const wrapper = mount(Sidebar, {
        props: {
            topItems: [
                {
                    title: 'item1',
                },
                {
                    title: 'item2',
                }
            ],
            bottomItems: [
                {
                    title: 'item3',
                },
                {
                    title: 'item4',
                },
                {
                    title: 'item5'
                }
            ]
        }
    });

    expect(wrapper.findAll('.sidebar__upper-content .sidebar-item').length).toBe(2);
    expect(wrapper.findAll('.sidebar__lower-content .sidebar-item').length).toBe(3);
});
