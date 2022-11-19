export interface GetCollectionResponse {
    accessLevel: string;
    cards: CollectionCard[];
    userId: string
}

interface CollectionCard {
    amount: number;
    cardId: string;
}