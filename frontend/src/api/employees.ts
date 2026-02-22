import api from './axiosInstance';
import type { ApiResponse, PagedResponse } from '../types/auth';
import type { Employee, EmployeeRequest } from '../types/employee';

interface EmployeeParams {
  page?: number;
  size?: number;
  sortField?: string;
  sortDir?: string;
  search?: string;
}

export const getEmployees = (params: EmployeeParams = {}) =>
  api.get<ApiResponse<PagedResponse<Employee>>>('/employees', { params }).then(r => r.data);

export const getEmployeeById = (id: number) =>
  api.get<ApiResponse<Employee>>(`/employees/${id}`).then(r => r.data);

export const createEmployee = (data: EmployeeRequest) =>
  api.post<ApiResponse<Employee>>('/employees', data).then(r => r.data);

export const updateEmployee = (id: number, data: EmployeeRequest) =>
  api.put<ApiResponse<Employee>>(`/employees/${id}`, data).then(r => r.data);

export const deleteEmployee = (id: number) =>
  api.delete(`/employees/${id}`);
