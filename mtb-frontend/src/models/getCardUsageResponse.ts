export interface GetCardUsageResponse {
    totalOwnedAmount: number;
    usageInDecks: Usage[];
}

interface Usage {
    name: string;
    usedAmount: number;
    id: string;
}