import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import DataTable from '../../components/common/DataTable';
import ConfirmDeleteModal from '../../components/common/ConfirmDeleteModal';
import { useEmployees, useDeleteEmployee } from '../../hooks/useEmployees';
import type { Employee } from '../../types/employee';

export default function EmployeeListPage() {
  const navigate = useNavigate();
  const [page, setPage] = useState(0);
  const [search, setSearch] = useState('');
  const [deleteTarget, setDeleteTarget] = useState<Employee | null>(null);

  const { data, isLoading } = useEmployees({ page, size: 20, search });
  const deleteMutation = useDeleteEmployee();

  const columns = [
    { header: 'ID', accessor: 'id' as keyof Employee },
    { header: 'Name', accessor: 'name' as keyof Employee },
    { header: 'Department', accessor: 'department' as keyof Employee },
    { header: 'Salary ($)', accessor: (row: Employee) => `$${row.salary?.toFixed(2)}` },
  ];

  return (
    <div className="container mt-4">
      <h2 className="mb-4">👷 Employees</h2>
      <DataTable
        columns={columns}
        data={data?.data?.content ?? []}
        isLoading={isLoading}
        totalPages={data?.data?.totalPages ?? 0}
        currentPage={page}
        onPageChange={setPage}
        onSearch={setSearch}
        searchPlaceholder="Search by name or department..."
        onEdit={(id) => navigate(`/employees/${id}/edit`)}
        onDelete={(id) => {
          const emp = data?.data?.content.find(e => e.id === id);
          if (emp) setDeleteTarget(emp);
        }}
        addButton={
          <button className="btn btn-success" onClick={() => navigate('/employees/new')}>
            + Add Employee
          </button>
        }
      />
      <ConfirmDeleteModal
        isOpen={!!deleteTarget}
        itemName={deleteTarget?.name ?? ''}
        isDeleting={deleteMutation.isPending}
        onConfirm={() => {
          if (deleteTarget) {
            deleteMutation.mutate(deleteTarget.id, { onSuccess: () => setDeleteTarget(null) });
          }
        }}
        onCancel={() => setDeleteTarget(null)}
      />
    </div>
  );
}
