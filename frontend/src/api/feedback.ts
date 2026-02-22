import api from './axiosInstance';
import type { ApiResponse, PagedResponse } from '../types/auth';
import type { Feedback, FeedbackRequest } from '../types/feedback';

export const getFeedback = (params: { page?: number; size?: number } = {}) =>
  api.get<ApiResponse<PagedResponse<Feedback>>>('/feedback', { params }).then(r => r.data);

export const submitFeedback = (data: FeedbackRequest) =>
  api.post<ApiResponse<Feedback>>('/feedback', data).then(r => r.data);

export const deleteFeedback = (id: number) =>
  api.delete(`/feedback/${id}`);
