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
    async getMyDecks(page:number, size:number): Promise<Deck[]> {
        const response = await request.get(`auth/decks?page=${page}&size=${size}`);
        return response.data;
    },
    async deleteDeck(deckId: string): Promise<any> {
        await request.delete(`auth/decks/${deckId}`);
    },
    async getUserDecks(userId: string): Promise<Deck[]> {
        const response = await request.get(`user/${userId}/decks`);
        return response.data;
    },
    async getOwnerDeck(deckId: string): Promise<Deck> {
        const response = await request.get(`auth/decks/${deckId}`);
        return response.data;
    },

    async getPublicDecks(): Promise<Deck[]> {
        const response = await request.get(`decks`);
        return response.data;
    },
    async getNonPublicDeck(deckId: string): Promise<Deck> {
        const response = await request.get(`decks/${deckId}`);
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
        const response = await request.put(`/auth/decks/${deckId}/cards/${payload.cardId}/${payload.amount}`);
        return response.data;
    },
    async setInSide(deckId: string, payload: AmountChangedEvent): Promise<number> {
        const response = await request.put(`/auth/decks/${deckId}/sideboard/${payload.cardId}/${payload.amount}`);
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
        const response = await request.get(`/decks/${deckId}/deck/${cardId}/amount`);
        return response.data;
    },
    async getCardAmountInSide(deckId: string, cardId: string): Promise<number> {
        const response = await request.get(`/decks/${deckId}/sideboard/${cardId}/amount`);
        return response.data;
    },

    async getCardUsageInDecks(cardId: string): Promise<any> {
        const response = await request.get('auth/decks/withCard/' + cardId);
        return response.data;
    },

    async validateDeck(deckId: string): Promise<string[]> {
        const response = await request.get(`auth/decks/${deckId}/valid`);
        return response.data;
    }
}
