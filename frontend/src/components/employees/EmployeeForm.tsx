import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { useNavigate } from 'react-router-dom';
import FormField from '../common/FormField';
import { employeeSchema, type EmployeeFormData } from '../../schemas/employeeSchema';

interface EmployeeFormProps {
  defaultValues?: Partial<EmployeeFormData>;
  onSubmit: (data: EmployeeFormData) => void;
  isSubmitting: boolean;
  title: string;
}

export default function EmployeeForm({ defaultValues, onSubmit, isSubmitting, title }: EmployeeFormProps) {
  const navigate = useNavigate();
  const { register, handleSubmit, reset, formState: { errors } } = useForm<EmployeeFormData>({
    resolver: zodResolver(employeeSchema),
    defaultValues,
  });

  useEffect(() => {
    if (defaultValues) reset(defaultValues);
  }, [defaultValues, reset]);

  return (
    <div className="card shadow-sm">
      <div className="card-header bg-success text-white">
        <h5 className="mb-0">{title}</h5>
      </div>
      <div className="card-body">
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="row">
            <div className="col-md-6">
              <FormField label="Full Name" name="name" register={register} error={errors.name} required placeholder="Enter employee name" />
            </div>
            <div className="col-md-3">
              <FormField label="Salary ($)" name="salary" type="number" register={register} error={errors.salary} required placeholder="0.00" />
            </div>
            <div className="col-md-3">
              <FormField label="Department" name="department" register={register} error={errors.department} required placeholder="e.g. Sales, IT" />
            </div>
          </div>
          <div className="d-flex gap-2">
            <button type="submit" className="btn btn-success" disabled={isSubmitting}>
              {isSubmitting ? <><span className="spinner-border spinner-border-sm me-2" />Saving...</> : 'Save Employee'}
            </button>
            <button type="button" className="btn btn-secondary" onClick={() => navigate('/employees')}>
              Cancel
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
