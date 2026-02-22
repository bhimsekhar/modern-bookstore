import { z } from 'zod';

export const loginSchema = z.object({
  username: z.string().min(1, 'Username is required'),
  password: z.string().min(1, 'Password is required'),
});

export const registerSchema = z.object({
  username:  z.string().min(3, 'Min 3 characters').max(50).regex(/^[a-zA-Z0-9_]+$/, 'Alphanumeric and underscore only'),
  password:  z.string().min(8, 'Min 8 characters').regex(/^(?=.*[A-Za-z])(?=.*\d).{8,}$/, 'Must include a letter and a digit'),
  firstname: z.string().min(1, 'First name is required').max(100),
  lastname:  z.string().min(1, 'Last name is required').max(100),
  email:     z.string().email('Valid email is required'),
  address:   z.string().max(500).optional().or(z.literal('')),
  phone:     z.string().regex(/^\d{0,20}$/, 'Digits only').optional().or(z.literal('')),
});

export type LoginFormData = z.infer<typeof loginSchema>;
export type RegisterFormData = z.infer<typeof registerSchema>;
