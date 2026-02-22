import { useState } from 'react';
import DataTable from '../../components/common/DataTable';
import ConfirmDeleteModal from '../../components/common/ConfirmDeleteModal';
import { useFeedback, useDeleteFeedback } from '../../hooks/useFeedback';
import type { Feedback } from '../../types/feedback';

export default function FeedbackListPage() {
  const [page, setPage] = useState(0);
  const [deleteTarget, setDeleteTarget] = useState<Feedback | null>(null);

  const { data, isLoading } = useFeedback({ page, size: 20 });
  const deleteMutation = useDeleteFeedback();

  const columns = [
    { header: 'ID', accessor: 'id' as keyof Feedback },
    { header: 'Name', accessor: 'name' as keyof Feedback },
    { header: 'Phone', accessor: (row: Feedback) => row.phone || '—' },
    { header: 'Email', accessor: (row: Feedback) => row.email || '—' },
    { header: 'Feedback', accessor: (row: Feedback) => (
      <span title={row.feedback}>{row.feedback?.length > 60 ? row.feedback.substring(0, 60) + '...' : row.feedback}</span>
    )},
    { header: 'Date', accessor: (row: Feedback) => row.dateCreated ? new Date(row.dateCreated).toLocaleString() : '—' },
  ];

  return (
    <div className="container mt-4">
      <h2 className="mb-4">💬 Customer Feedback</h2>
      <p className="text-muted">Total: {data?.data?.totalElements ?? 0} feedback entries</p>
      <DataTable
        columns={columns}
        data={data?.data?.content ?? []}
        isLoading={isLoading}
        totalPages={data?.data?.totalPages ?? 0}
        currentPage={page}
        onPageChange={setPage}
        onDelete={(id) => {
          const fb = data?.data?.content.find(f => f.id === id);
          if (fb) setDeleteTarget(fb);
        }}
      />
      <ConfirmDeleteModal
        isOpen={!!deleteTarget}
        itemName={`feedback from ${deleteTarget?.name ?? ''}`}
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
