import api from './axiosInstance';
import type { ApiResponse, PagedResponse } from '../types/auth';
import type { Book, BookRequest } from '../types/book';

interface BookParams {
  page?: number;
  size?: number;
  sortField?: string;
  sortDir?: string;
  search?: string;
}

export const getBooks = (params: BookParams = {}) =>
  api.get<ApiResponse<PagedResponse<Book>>>('/books', { params }).then(r => r.data);

export const getBookById = (id: number) =>
  api.get<ApiResponse<Book>>(`/books/${id}`).then(r => r.data);

export const createBook = (data: BookRequest) =>
  api.post<ApiResponse<Book>>('/books', data).then(r => r.data);

export const updateBook = (id: number, data: BookRequest) =>
  api.put<ApiResponse<Book>>(`/books/${id}`, data).then(r => r.data);

export const deleteBook = (id: number) =>
  api.delete(`/books/${id}`);
