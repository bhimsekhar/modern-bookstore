import api from './axiosInstance';
import type { ApiResponse, JwtResponse } from '../types/auth';
import type { RegisterFormData } from '../schemas/authSchema';

export const login = (data: { username: string; password: string }) =>
  api.post<ApiResponse<JwtResponse>>('/auth/login', data).then(r => r.data);

export const register = (data: RegisterFormData) =>
  api.post<ApiResponse<{ username: string }>>('/auth/register', data).then(r => r.data);
