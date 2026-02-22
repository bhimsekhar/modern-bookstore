import { z } from 'zod';

export const bookSchema = z.object({
  title:       z.string().min(1, 'Title is required').max(255),
  author:      z.string().min(1, 'Author is required').max(255),
  genre:       z.string().min(1, 'Genre is required').max(100),
  description: z.string().max(2000).optional().or(z.literal('')),
  copy:        z.coerce.number().int('Must be a whole number').min(0, 'Copies cannot be negative'),
  price:       z.coerce.number().min(0.01, 'Price must be greater than 0'),
});

export type BookFormData = z.infer<typeof bookSchema>;
