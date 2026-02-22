import { useMutation, useQuery } from '@tanstack/react-query';
import { toast } from 'react-toastify';
import * as employeesApi from '../api/employees';
import type { EmployeeRequest } from '../types/employee';
import { queryClient } from './queryClient';

interface EmployeeParams {
  page?: number;
  size?: number;
  sortField?: string;
  sortDir?: string;
  search?: string;
}

export const useEmployees = (params: EmployeeParams = {}) =>
  useQuery({
    queryKey: ['employees', params],
    queryFn: () => employeesApi.getEmployees(params),
  });

export const useEmployee = (id: number) =>
  useQuery({
    queryKey: ['employees', id],
    queryFn: () => employeesApi.getEmployeeById(id),
    enabled: !!id,
  });

export const useCreateEmployee = () =>
  useMutation({
    mutationFn: (data: EmployeeRequest) => employeesApi.createEmployee(data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['employees'] });
      toast.success('Employee created successfully');
    },
    onError: (err: any) => toast.error(err.response?.data?.message || 'Failed to create employee'),
  });

export const useUpdateEmployee = (id: number) =>
  useMutation({
    mutationFn: (data: EmployeeRequest) => employeesApi.updateEmployee(id, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['employees'] });
      toast.success('Employee updated successfully');
    },
    onError: (err: any) => toast.error(err.response?.data?.message || 'Failed to update employee'),
  });

export const useDeleteEmployee = () =>
  useMutation({
    mutationFn: (id: number) => employeesApi.deleteEmployee(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['employees'] });
      toast.success('Employee deleted');
    },
    onError: (err: any) => toast.error(err.response?.data?.message || 'Failed to delete employee'),
  });
