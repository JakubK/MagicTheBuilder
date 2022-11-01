import request from './api';

export const collectionService = {
    async getCardAmountInCollection(cardId: string): Promise<number> {
        const response = await request.get('/auth/collections/' + cardId);
        return response.data;
    },

    async incrementCardAmountInCollection(cardId: string): Promise<number>
    {
        const response = await request.get('auth/collections/add/' + cardId);
        return response.data;
    },

    async decrementCardAmountInCollection(cardId: string): Promise<number>
    {
        const response = await request.get('auth/collections/remove/' + cardId);
        return response.data;
    }
}
