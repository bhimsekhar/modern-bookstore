import api from './axiosInstance';
import type { ApiResponse, PagedResponse } from '../types/auth';
import type { Purchase, PurchaseRequest } from '../types/purchase';

interface PurchaseParams {
  page?: number;
  size?: number;
  sortField?: string;
  sortDir?: string;
  search?: string;
}

export const getPurchases = (params: PurchaseParams = {}) =>
  api.get<ApiResponse<PagedResponse<Purchase>>>('/purchases', { params }).then(r => r.data);

export const getPurchaseById = (id: number) =>
  api.get<ApiResponse<Purchase>>(`/purchases/${id}`).then(r => r.data);

export const createPurchase = (data: PurchaseRequest) =>
  api.post<ApiResponse<Purchase>>('/purchases', data).then(r => r.data);

export const updatePurchase = (id: number, data: PurchaseRequest) =>
  api.put<ApiResponse<Purchase>>(`/purchases/${id}`, data).then(r => r.data);

export const deletePurchase = (id: number) =>
  api.delete(`/purchases/${id}`);
