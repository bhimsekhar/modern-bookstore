import { z } from 'zod';

export const feedbackSchema = z.object({
  name:     z.string().min(1, 'Name is required').max(255),
  phone:    z.string().regex(/^[+\d\s\-]{7,20}$/, 'Invalid phone number').optional().or(z.literal('')),
  email:    z.string().email('Invalid email address').optional().or(z.literal('')),
  feedback: z.string().min(10, 'Feedback must be at least 10 characters').max(2000, 'Feedback must not exceed 2000 characters'),
}).refine(data => (data.phone && data.phone.length > 0) || (data.email && data.email.length > 0), {
  message: 'At least one contact method (phone or email) is required',
  path: ['phone'],
});

export type FeedbackFormData = z.infer<typeof feedbackSchema>;
