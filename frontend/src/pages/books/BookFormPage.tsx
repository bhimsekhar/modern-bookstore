import { useNavigate } from 'react-router-dom';
import BookForm from '../../components/books/BookForm';
import { useCreateBook } from '../../hooks/useBooks';
import type { BookFormData } from '../../schemas/bookSchema';

export default function BookFormPage() {
  const navigate = useNavigate();
  const createBook = useCreateBook();

  const onSubmit = (data: BookFormData) => {
    createBook.mutate(
      { ...data, description: data.description || '' },
      { onSuccess: () => navigate('/books') }
    );
  };

  return (
    <div className="container mt-4">
      <BookForm title="Add New Book" onSubmit={onSubmit} isSubmitting={createBook.isPending} />
    </div>
  );
}
