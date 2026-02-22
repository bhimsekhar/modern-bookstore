import { useNavigate } from 'react-router-dom';
import EmployeeForm from '../../components/employees/EmployeeForm';
import { useCreateEmployee } from '../../hooks/useEmployees';
import type { EmployeeFormData } from '../../schemas/employeeSchema';

export default function EmployeeFormPage() {
  const navigate = useNavigate();
  const createEmployee = useCreateEmployee();

  const onSubmit = (data: EmployeeFormData) => {
    createEmployee.mutate(data, { onSuccess: () => navigate('/employees') });
  };

  return (
    <div className="container mt-4">
      <EmployeeForm title="Add New Employee" onSubmit={onSubmit} isSubmitting={createEmployee.isPending} />
    </div>
  );
}
