import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import DataTable from '../../components/common/DataTable';
import ConfirmDeleteModal from '../../components/common/ConfirmDeleteModal';
import { usePurchases, useDeletePurchase } from '../../hooks/usePurchases';
import { useAuth } from '../../context/AuthContext';
import type { Purchase } from '../../types/purchase';

export default function PurchaseListPage() {
  const navigate = useNavigate();
  const { isAdmin } = useAuth();
  const [page, setPage] = useState(0);
  const [search, setSearch] = useState('');
  const [deleteTarget, setDeleteTarget] = useState<Purchase | null>(null);

  const { data, isLoading } = usePurchases({ page, size: 20, sortDir: 'desc', search });
  const deleteMutation = useDeletePurchase();

  const columns = [
    { header: 'ID', accessor: 'id' as keyof Purchase },
    { header: 'Buyer Name', accessor: 'name' as keyof Purchase },
    { header: 'Phone', accessor: 'phone' as keyof Purchase },
    { header: 'Books', accessor: (row: Purchase) => (
      <span title={row.books}>{row.books?.length > 40 ? row.books.substring(0, 40) + '...' : row.books}</span>
    )},
    { header: 'Qty', accessor: 'quantity' as keyof Purchase },
    { header: 'Total ($)', accessor: (row: Purchase) => `$${row.totalPrice?.toFixed(2)}` },
    { header: 'Date', accessor: (row: Purchase) => row.datePurchased ? new Date(row.datePurchased).toLocaleDateString() : '—' },
  ];

  return (
    <div className="container mt-4">
      <h2 className="mb-4">🛒 Purchases</h2>
      <DataTable
        columns={columns}
        data={data?.data?.content ?? []}
        isLoading={isLoading}
        totalPages={data?.data?.totalPages ?? 0}
        currentPage={page}
        onPageChange={setPage}
        onSearch={setSearch}
        searchPlaceholder="Search by buyer name..."
        onEdit={(id) => navigate(`/purchases/${id}/edit`)}
        onDelete={isAdmin() ? (id) => {
          const p = data?.data?.content.find(x => x.id === id);
          if (p) setDeleteTarget(p);
        } : undefined}
        addButton={
          <button className="btn btn-warning text-dark" onClick={() => navigate('/purchases/new')}>
            + Record Purchase
          </button>
        }
      />
      <ConfirmDeleteModal
        isOpen={!!deleteTarget}
        itemName={`purchase by ${deleteTarget?.name ?? ''}`}
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
