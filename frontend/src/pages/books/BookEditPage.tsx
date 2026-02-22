import { useNavigate, useParams } from 'react-router-dom';
import BookForm from '../../components/books/BookForm';
import { useBook, useUpdateBook } from '../../hooks/useBooks';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import type { BookFormData } from '../../schemas/bookSchema';

export default function BookEditPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const bookId = Number(id);
  const { data, isLoading } = useBook(bookId);
  const updateBook = useUpdateBook(bookId);

  if (isLoading) return <LoadingSpinner />;

  const book = data?.data;
  const defaultValues: Partial<BookFormData> = book
    ? { title: book.title, author: book.author, genre: book.genre, description: book.description ?? '', copy: book.copy, price: book.price }
    : {};

  const onSubmit = (formData: BookFormData) => {
    updateBook.mutate(
      { ...formData, description: formData.description || '' },
      { onSuccess: () => navigate('/books') }
    );
  };

  return (
    <div className="container mt-4">
      <BookForm title="Edit Book" defaultValues={defaultValues} onSubmit={onSubmit} isSubmitting={updateBook.isPending} />
    </div>
  );
}
