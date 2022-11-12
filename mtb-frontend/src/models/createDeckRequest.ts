import { AccessLevel } from "./accessLevel";

export interface CreateDeckRequest {
    name: string;
    accessLevel: AccessLevel;
    gameMode: string;
}
