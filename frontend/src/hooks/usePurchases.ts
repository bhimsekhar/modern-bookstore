import { useMutation, useQuery } from '@tanstack/react-query';
import { toast } from 'react-toastify';
import * as purchasesApi from '../api/purchases';
import type { PurchaseRequest } from '../types/purchase';
import { queryClient } from './queryClient';

interface PurchaseParams {
  page?: number;
  size?: number;
  sortField?: string;
  sortDir?: string;
  search?: string;
}

export const usePurchases = (params: PurchaseParams = {}) =>
  useQuery({
    queryKey: ['purchases', params],
    queryFn: () => purchasesApi.getPurchases(params),
  });

export const usePurchase = (id: number) =>
  useQuery({
    queryKey: ['purchases', id],
    queryFn: () => purchasesApi.getPurchaseById(id),
    enabled: !!id,
  });

export const useCreatePurchase = () =>
  useMutation({
    mutationFn: (data: PurchaseRequest) => purchasesApi.createPurchase(data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['purchases'] });
      toast.success('Purchase recorded successfully');
    },
    onError: (err: any) => toast.error(err.response?.data?.message || 'Failed to record purchase'),
  });

export const useUpdatePurchase = (id: number) =>
  useMutation({
    mutationFn: (data: PurchaseRequest) => purchasesApi.updatePurchase(id, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['purchases'] });
      toast.success('Purchase updated successfully');
    },
    onError: (err: any) => toast.error(err.response?.data?.message || 'Failed to update purchase'),
  });

export const useDeletePurchase = () =>
  useMutation({
    mutationFn: (id: number) => purchasesApi.deletePurchase(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['purchases'] });
      toast.success('Purchase deleted');
    },
    onError: (err: any) => toast.error(err.response?.data?.message || 'Failed to delete purchase'),
  });
