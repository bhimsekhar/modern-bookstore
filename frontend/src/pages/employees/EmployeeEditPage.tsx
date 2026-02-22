import { useNavigate, useParams } from 'react-router-dom';
import EmployeeForm from '../../components/employees/EmployeeForm';
import { useEmployee, useUpdateEmployee } from '../../hooks/useEmployees';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import type { EmployeeFormData } from '../../schemas/employeeSchema';

export default function EmployeeEditPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const empId = Number(id);
  const { data, isLoading } = useEmployee(empId);
  const updateEmployee = useUpdateEmployee(empId);

  if (isLoading) return <LoadingSpinner />;

  const emp = data?.data;
  const defaultValues: Partial<EmployeeFormData> = emp
    ? { name: emp.name, salary: emp.salary, department: emp.department }
    : {};

  const onSubmit = (formData: EmployeeFormData) => {
    updateEmployee.mutate(formData, { onSuccess: () => navigate('/employees') });
  };

  return (
    <div className="container mt-4">
      <EmployeeForm title="Edit Employee" defaultValues={defaultValues} onSubmit={onSubmit} isSubmitting={updateEmployee.isPending} />
    </div>
  );
}
