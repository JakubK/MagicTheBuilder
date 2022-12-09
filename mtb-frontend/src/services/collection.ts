import { AccessLevel } from '@/models/accessLevel';
import { AmountChangedEvent } from '@/models/amountChangedEvent';
import { GetCollectionResponse } from '@/models/getCollectionResponse';
import request from './api';

export const collectionService = {
    async getCardAmountInCollection(cardId: string): Promise<number> {
        const response = await request.get('auth/collections/' + cardId);
        return response.data;
    },

    async setInCollection(payload: AmountChangedEvent): Promise<number> {
        const response = await request.put(`auth/collections/${payload.cardId}/${payload.amount}`);
        return response.data;
    },

    async incrementCardAmountInCollection(cardId: string): Promise<number> {
        const response = await request.post('auth/collections/add/' + cardId);
        return response.data;
    },

    async decrementCardAmountInCollection(cardId: string): Promise<number> {
        const response = await request.post('auth/collections/remove/' + cardId);
        return response.data;
    },

    async getCollection(page: number, size: number): Promise<GetCollectionResponse> {
        const response = await request.get(`auth/collections?page=${page}&size=${size}`);
        return response.data;
    },

    async getUserCollection(id:string, page: number, size: number): Promise<GetCollectionResponse> {
        const response = await request.get(`collections/${id}?page=${page}&size=${size}`);
        return response.data; 
    },

    async setAccessLevel(accessLevel: AccessLevel): Promise<any> {
        const response = await request.post(`auth/collections/${accessLevel}`);
        return response.data;
    }
}
