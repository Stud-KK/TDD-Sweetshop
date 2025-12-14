import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add token to requests
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Auth API
export const authAPI = {
  register: (name, email, password) =>
    api.post('/auth/register', { name, email, password }),
  
  login: (email, password) =>
    api.post('/auth/login', { email, password }),
};

// Sweets API
export const sweetsAPI = {
  getAll: () => api.get('/sweets'),
  
  getById: (id) => api.get(`/sweets/${id}`),
  
  create: (sweet) => api.post('/sweets', sweet),
  
  update: (id, sweet) => api.put(`/sweets/${id}`, sweet),
  
  delete: (id) => api.delete(`/sweets/${id}`),
  
  search: (params) => {
    const queryParams = new URLSearchParams();
    if (params.name) queryParams.append('name', params.name);
    if (params.category) queryParams.append('category', params.category);
    if (params.minPrice) queryParams.append('minPrice', params.minPrice);
    if (params.maxPrice) queryParams.append('maxPrice', params.maxPrice);
    return api.get(`/sweets/search?${queryParams.toString()}`);
  },
  
  purchase: (id, quantity) =>
    api.post(`/sweets/${id}/purchase?quantity=${quantity}`),
  
  restock: (id, quantity) =>
    api.post(`/sweets/${id}/restock?quantity=${quantity}`),
};

export default api;

