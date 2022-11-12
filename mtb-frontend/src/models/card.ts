export interface Card {
    id: string;
    name: string;
    imageUrl: string;
    text: string;
    type: string;
    colors: string[];
    power: string;
    originalText: string;
    toughness: string;
    set: string;
    manaCost: string;
    amount: number;
}
