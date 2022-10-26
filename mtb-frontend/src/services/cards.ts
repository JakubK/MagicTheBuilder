import request from './api';
import { GetCardsResponse } from "@/models/getCardsResponse";
import { GetCardsRequest } from '@/models/getCardsRequest';

export const cardsService = {
    async getCards(payload: Partial<GetCardsRequest>): Promise<GetCardsResponse> {
        const response = await request.get('cards', {
            params: {
                ...payload
            }
        })
        return response.data;
    }
}
