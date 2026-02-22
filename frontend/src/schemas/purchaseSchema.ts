import { z } from 'zod';

export const purchaseSchema = z.object({
  name:       z.string().min(1, 'Buyer name is required').max(255),
  phone:      z.string().regex(/^[+\d\s\-]{7,20}$/, 'Invalid phone number'),
  books:      z.string().min(1, 'Books field is required').max(500),
  quantity:   z.coerce.number().int('Must be a whole number').min(1, 'Quantity must be at least 1'),
  totalPrice: z.coerce.number().min(0.01, 'Total price must be positive'),
});

export type PurchaseFormData = z.infer<typeof purchaseSchema>;
