import { z } from 'zod';

export const employeeSchema = z.object({
  name:       z.string().min(1, 'Employee name is required').max(255),
  salary:     z.coerce.number().min(0.01, 'Salary must be positive'),
  department: z.string().min(1, 'Department is required').max(100),
});

export type EmployeeFormData = z.infer<typeof employeeSchema>;
