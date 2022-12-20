import request from './api';
import { GetCardsResponse } from "@/models/getCardsResponse";
import { GetCardsRequest } from '@/models/getCardsRequest';
import { Card } from '@/models/card';

export const cardsService = {
    async getCards(payload: Partial<GetCardsRequest>): Promise<GetCardsResponse> {
        const response = await request.get('cards', {
            params: {
                ...payload
            }
        })
        return response.data;
    },
    async getCard(id: string): Promise<Card> {
        const response = await request.get(`cards/${id}`);
        return response.data;
    }
}
