import request from './api';

export const metaDataService = {
    async getFormats(): Promise<string[]> {
        const response = await request.get('formats');
        return response.data;
    },

    async getSets(): Promise<string[]> {
        const response = await request.get('sets');
        return response.data.map(x => x.name);
    },

    async getTypes(): Promise<string[]> {
        const response = await request.get('types');
        return response.data;
    },
}
