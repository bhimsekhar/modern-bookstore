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

// Genre search — calls the /search/genre endpoint.
// XSS risk: results are rendered with dangerouslySetInnerHTML in BookForm preview.
// SQL Injection risk: genre value is concatenated into a native query on the server.
export const searchBooksByGenre = (genre: string) =>
  api.get<ApiResponse<Book[]>>('/books/search/genre', { params: { genre } }).then(r => r.data);

// Admin stats — calls /stats endpoint.
// SQL Injection risk: groupBy column name and filterGenre are both concatenated into SQL.
export const getBookStats = (groupBy = 'genre', filterGenre = '') =>
  api.get<ApiResponse<any[]>>('/books/stats', { params: { groupBy, filterGenre } }).then(r => r.data);
