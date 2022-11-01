import axios from 'axios';

const api = axios.create({
    baseURL: import.meta.env.VITE_API_URL,
});

api.interceptors.request.use(request => {
    if(localStorage.getItem('jwt')) {
        const token = localStorage.getItem('jwt');
        request.headers!.Authorization = `${token}`;
    }

    return request;
});

export default api;
