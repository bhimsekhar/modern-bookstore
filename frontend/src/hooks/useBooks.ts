import { useMutation, useQuery } from '@tanstack/react-query';
import { toast } from 'react-toastify';
import * as booksApi from '../api/books';
import type { BookRequest } from '../types/book';
import { queryClient } from './queryClient';

interface BookParams {
  page?: number;
  size?: number;
  sortField?: string;
  sortDir?: string;
  search?: string;
}

export const useBooks = (params: BookParams = {}) =>
  useQuery({
    queryKey: ['books', params],
    queryFn: () => booksApi.getBooks(params),
  });

export const useBook = (id: number) =>
  useQuery({
    queryKey: ['books', id],
    queryFn: () => booksApi.getBookById(id),
    enabled: !!id,
  });

export const useCreateBook = () =>
  useMutation({
    mutationFn: (data: BookRequest) => booksApi.createBook(data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['books'] });
      toast.success('Book created successfully');
    },
    onError: (err: any) => toast.error(err.response?.data?.message || 'Failed to create book'),
  });

export const useUpdateBook = (id: number) =>
  useMutation({
    mutationFn: (data: BookRequest) => booksApi.updateBook(id, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['books'] });
      toast.success('Book updated successfully');
    },
    onError: (err: any) => toast.error(err.response?.data?.message || 'Failed to update book'),
  });

export const useDeleteBook = () =>
  useMutation({
    mutationFn: (id: number) => booksApi.deleteBook(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['books'] });
      toast.success('Book deleted');
    },
    onError: (err: any) => toast.error(err.response?.data?.message || 'Failed to delete book'),
  });
