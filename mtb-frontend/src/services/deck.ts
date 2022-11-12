import { CreateDeckRequest } from "@/models/createDeckRequest";
import { Deck } from "@/models/deck";
import request from './api';

export const decksService = {
    async createDeck(payload: Partial<CreateDeckRequest>): Promise<string> {
        const response = await request.post('auth/decks', payload);
        return response.data;
    },
    async updateDeck(id: string, payload: Partial<CreateDeckRequest>): Promise<string> {
        const response = await request.put(`auth/decks/${id}`, payload);
        return response.data;
    },
    async getMyDecks(): Promise<Deck[]> {
        const response = await request.get('auth/decks/myDecks');
        return response.data;
    },
    async deleteDeck(deckId: string): Promise<any> {
        await request.delete(`auth/decks/${deckId}`);
    }
}
