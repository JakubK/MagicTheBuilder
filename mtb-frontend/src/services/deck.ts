import { CreateDeckRequest } from "@/models/createDeckRequest";
import { Deck } from "@/models/deck";
import request from './api';

export const decksService = {
    async createDeck(payload: Partial<CreateDeckRequest>): Promise<string> {
        const response = await request.post('auth/decks', payload);
        return response.data;
    },
    async getMyDecks(): Promise<Deck[]> {
        const response = await request.get('auth/decks/myDecks');
        return response.data;
    }
}
