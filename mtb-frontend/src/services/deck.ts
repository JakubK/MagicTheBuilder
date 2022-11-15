import { AmountChangedEvent } from "@/models/amountChangedEvent";
import { CreateDeckRequest } from "@/models/createDeckRequest";
import { Deck } from "@/models/deck";
import request from './api';

export const decksService = {
    async createDeck(payload: Partial<CreateDeckRequest>): Promise<Deck> {
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
    },


    async getOwnerDeck(deckId: string): Promise<Deck> {
        const response = await request.get(`auth/decks/${deckId}`);
        return response.data;
    },
    async removeFromDeck(deckId: string, cardId: string): Promise<number> {
        const response = await request.post(`auth/decks/${deckId}/remove/deck/${cardId}`);
        return response.data;
    },
    async addToDeck(deckId: string, cardId: string): Promise<number> {
        const response = await request.post(`auth/decks/${deckId}/add/deck/${cardId}`);
        return response.data;
    },

    async setInDeck(deckId: string, payload: AmountChangedEvent): Promise<number> {
        const response = await request.put(`/auth/decks/${deckId}/deck`, payload);
        return response.data;
    },
    async setInSide(deckId: string, payload: AmountChangedEvent): Promise<number> {
        const response = await request.put(`/auth/decks/${deckId}/sideboard`, payload);
        return response.data;
    },

    async removeFromSideboard(deckId: string, cardId: string): Promise<number> {
        const response = await request.post(`auth/decks/${deckId}/remove/sideboard/${cardId}`);
        return response.data;
    },
    async addToSideboard(deckId: string, cardId: string): Promise<number> {
        const response = await request.post(`auth/decks/${deckId}/add/sideboard/${cardId}`);
        return response.data;
    },


    async getCardAmountInDeck(deckId: string, cardId: string): Promise<number> {
        const response = await request.get(`/decks/${deckId}/${cardId}/amount/deck`);
        return response.data;
    },
    async getCardAmountInSide(deckId: string, cardId: string): Promise<number> {
        const response = await request.get(`/decks/${deckId}/${cardId}/amount/sideboard`);
        return response.data;
    },

    async getCardUsageInDecks(cardId: string): Promise<any> {
        const response = await request.get('auth/decks/withCard/' + cardId);
        return response.data;
    }
}
