import { AccessLevel } from "./accessLevel";

export interface Deck {
    id: string;
    accessLevel: AccessLevel;
    creationDate: Date;
    deckSize: number;
    gameMode: string;
    ownerId: number;
    sideboardSize: number;
    name: string;
    cards: DeckCard[];
    sideboard: DeckCard[];
    valid: boolean;
}

interface DeckCard {
    amount: number;
    cardId: string;
}
