import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import DataTable from '../../components/common/DataTable';
import ConfirmDeleteModal from '../../components/common/ConfirmDeleteModal';
import { useBooks, useDeleteBook } from '../../hooks/useBooks';
import { useAuth } from '../../context/AuthContext';
import type { Book } from '../../types/book';

export default function BookListPage() {
  const navigate = useNavigate();
  const { isAdmin } = useAuth();
  const [page, setPage] = useState(0);
  const [search, setSearch] = useState('');
  const [deleteTarget, setDeleteTarget] = useState<Book | null>(null);

  const { data, isLoading } = useBooks({ page, size: 20, search });
  const deleteMutation = useDeleteBook();

  const columns = [
    { header: 'ID', accessor: 'id' as keyof Book },
    { header: 'Title', accessor: 'title' as keyof Book },
    { header: 'Author', accessor: 'author' as keyof Book },
    { header: 'Genre', accessor: 'genre' as keyof Book },
    { header: 'Price ($)', accessor: (row: Book) => `$${row.price?.toFixed(2)}` },
    { header: 'Copies', accessor: 'copy' as keyof Book },
  ];

  return (
    <div className="container mt-4">
      <h2 className="mb-4">📚 Book Inventory</h2>
      <DataTable
        columns={columns}
        data={data?.data?.content ?? []}
        isLoading={isLoading}
        totalPages={data?.data?.totalPages ?? 0}
        currentPage={page}
        onPageChange={setPage}
        onSearch={setSearch}
        searchPlaceholder="Search by title or author..."
        onEdit={isAdmin() ? (id) => navigate(`/books/${id}/edit`) : undefined}
        onDelete={isAdmin() ? (id) => {
          const book = data?.data?.content.find(b => b.id === id);
          if (book) setDeleteTarget(book);
        } : undefined}
        addButton={
          isAdmin() && (
            <button className="btn btn-primary" onClick={() => navigate('/books/new')}>
              + Add Book
            </button>
          )
        }
      />
      <ConfirmDeleteModal
        isOpen={!!deleteTarget}
        itemName={deleteTarget?.title ?? ''}
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
