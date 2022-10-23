import { SignIn } from '@/models/signIn';
import request from './api';

export const authService = {
    async register(payload: Partial<SignIn>): Promise<any> {
        const response = await request.post('user/register', payload)
        return response.data;
    }
}
