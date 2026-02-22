import { useState } from 'react';
import LoadingSpinner from './LoadingSpinner';

interface Column<T> {
  header: string;
  accessor: keyof T | ((row: T) => React.ReactNode);
}

interface DataTableProps<T extends { id: number }> {
  columns: Column<T>[];
  data: T[];
  onEdit?: (id: number) => void;
  onDelete?: (id: number) => void;
  isLoading: boolean;
  totalPages: number;
  currentPage: number;
  onPageChange: (page: number) => void;
  onSearch?: (term: string) => void;
  searchPlaceholder?: string;
  addButton?: React.ReactNode;
}

export default function DataTable<T extends { id: number }>({
  columns, data, onEdit, onDelete, isLoading,
  totalPages, currentPage, onPageChange, onSearch,
  searchPlaceholder = 'Search...', addButton,
}: DataTableProps<T>) {
  const [searchTerm, setSearchTerm] = useState('');

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    onSearch?.(searchTerm);
  };

  const renderCell = (row: T, col: Column<T>) => {
    if (typeof col.accessor === 'function') return col.accessor(row);
    const val = row[col.accessor];
    if (val === null || val === undefined) return '—';
    return String(val);
  };

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-3">
        {onSearch && (
          <form className="d-flex gap-2" onSubmit={handleSearch}>
            <input
              type="text"
              className="form-control"
              placeholder={searchPlaceholder}
              value={searchTerm}
              onChange={e => setSearchTerm(e.target.value)}
              style={{ minWidth: '250px' }}
            />
            <button type="submit" className="btn btn-outline-primary">Search</button>
            {searchTerm && (
              <button type="button" className="btn btn-outline-secondary" onClick={() => {
                setSearchTerm('');
                onSearch('');
              }}>Clear</button>
            )}
          </form>
        )}
        <div className="ms-auto">{addButton}</div>
      </div>

      {isLoading ? (
        <LoadingSpinner />
      ) : data.length === 0 ? (
        <div className="alert alert-info text-center">No records found.</div>
      ) : (
        <div className="table-responsive">
          <table className="table table-striped table-hover table-bordered align-middle">
            <thead className="table-dark">
              <tr>
                {columns.map((col, i) => (
                  <th key={i}>{col.header}</th>
                ))}
                {(onEdit || onDelete) && <th style={{ width: '120px' }}>Actions</th>}
              </tr>
            </thead>
            <tbody>
              {data.map(row => (
                <tr key={row.id}>
                  {columns.map((col, i) => (
                    <td key={i}>{renderCell(row, col)}</td>
                  ))}
                  {(onEdit || onDelete) && (
                    <td>
                      <div className="d-flex gap-1">
                        {onEdit && (
                          <button
                            className="btn btn-sm btn-warning"
                            onClick={() => onEdit(row.id)}
                            title="Edit"
                          >✏️</button>
                        )}
                        {onDelete && (
                          <button
                            className="btn btn-sm btn-danger"
                            onClick={() => onDelete(row.id)}
                            title="Delete"
                          >🗑️</button>
                        )}
                      </div>
                    </td>
                  )}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {totalPages > 1 && (
        <nav>
          <ul className="pagination justify-content-center">
            <li className={`page-item ${currentPage === 0 ? 'disabled' : ''}`}>
              <button className="page-link" onClick={() => onPageChange(currentPage - 1)}>
                &laquo; Prev
              </button>
            </li>
            {Array.from({ length: totalPages }, (_, i) => (
              <li key={i} className={`page-item ${currentPage === i ? 'active' : ''}`}>
                <button className="page-link" onClick={() => onPageChange(i)}>{i + 1}</button>
              </li>
            ))}
            <li className={`page-item ${currentPage === totalPages - 1 ? 'disabled' : ''}`}>
              <button className="page-link" onClick={() => onPageChange(currentPage + 1)}>
                Next &raquo;
              </button>
            </li>
          </ul>
        </nav>
      )}
    </div>
  );
}
