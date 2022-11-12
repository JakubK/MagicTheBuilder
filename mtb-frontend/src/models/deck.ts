import { AccessLevel } from "./accessLevel";

export interface Deck {
    accessLevel: AccessLevel;
    creationDate: Date;
    deckSize: number;
    gameMode: string;
    id: string;
    ownerId: number;
    sideboardSize: number;
}
