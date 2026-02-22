import { useMutation, useQuery } from '@tanstack/react-query';
import { toast } from 'react-toastify';
import * as feedbackApi from '../api/feedback';
import type { FeedbackRequest } from '../types/feedback';
import { queryClient } from './queryClient';

export const useFeedback = (params: { page?: number; size?: number } = {}) =>
  useQuery({
    queryKey: ['feedback', params],
    queryFn: () => feedbackApi.getFeedback(params),
  });

export const useSubmitFeedback = () =>
  useMutation({
    mutationFn: (data: FeedbackRequest) => feedbackApi.submitFeedback(data),
    onError: (err: any) => toast.error(err.response?.data?.message || 'Failed to submit feedback'),
  });

export const useDeleteFeedback = () =>
  useMutation({
    mutationFn: (id: number) => feedbackApi.deleteFeedback(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['feedback'] });
      toast.success('Feedback deleted');
    },
    onError: (err: any) => toast.error(err.response?.data?.message || 'Failed to delete feedback'),
  });
