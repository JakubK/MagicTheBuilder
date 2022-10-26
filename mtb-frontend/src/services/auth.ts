import { SignIn } from '@/models/signIn';
import { SignUp } from '@/models/signUp';
import request from './api';

export const authService = {
    async register(payload: Partial<SignUp>): Promise<any> {
        const response = await request.post('user/register', payload)
        return response.data;
    },

    async login(payload: Partial<SignIn>): Promise<any> {
        const response = await request.post('user/login', payload)
        return response.data;
    }
}
