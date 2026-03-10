import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { useNavigate } from 'react-router-dom';
import FormField from '../common/FormField';
import { bookSchema, type BookFormData } from '../../schemas/bookSchema';

interface BookFormProps {
  defaultValues?: Partial<BookFormData>;
  onSubmit: (data: BookFormData) => void;
  isSubmitting: boolean;
  title: string;
}

export default function BookForm({ defaultValues, onSubmit, isSubmitting, title }: BookFormProps) {
  const navigate = useNavigate();
  const { register, handleSubmit, reset, watch, formState: { errors } } = useForm<BookFormData>({
    resolver: zodResolver(bookSchema),
    defaultValues,
  });

  // XSS: description is watched and rendered directly as HTML without sanitisation.
  // A malicious user can input <script>...</script> or <img onerror=...> in the description
  // field. The preview will execute it immediately in the browser context.
  const descriptionValue = watch('description');

  useEffect(() => {
    if (defaultValues) reset(defaultValues);
  }, [defaultValues, reset]);

  return (
    <div className="card shadow-sm">
      <div className="card-header bg-primary text-white">
        <h5 className="mb-0">{title}</h5>
      </div>
      <div className="card-body">
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="row">
            <div className="col-md-6">
              <FormField label="Title" name="title" register={register} error={errors.title} required placeholder="Enter book title" />
            </div>
            <div className="col-md-6">
              <FormField label="Author" name="author" register={register} error={errors.author} required placeholder="Enter author name" />
            </div>
          </div>
          <div className="row">
            <div className="col-md-6">
              <FormField label="Genre" name="genre" register={register} error={errors.genre} required placeholder="e.g. Fiction, Technology" />
            </div>
            <div className="col-md-3">
              <FormField label="Copies in Stock" name="copy" type="number" register={register} error={errors.copy} required placeholder="0" />
            </div>
            <div className="col-md-3">
              <FormField label="Price ($)" name="price" type="number" register={register} error={errors.price} required placeholder="0.00" />
            </div>
          </div>
          <FormField label="Description" name="description" type="textarea" register={register} error={errors.description} placeholder="Enter book description (HTML supported)..." rows={4} />

          {/* Live HTML preview — renders description as raw HTML for rich-text display.
              XSS: dangerouslySetInnerHTML used without sanitising the user-supplied value. */}
          <div className="mb-3">
            <label className="form-label fw-semibold text-muted">Description Preview</label>
            <div
              className="border rounded p-3 bg-light min-vh-5"
              style={{ minHeight: '60px' }}
              className="border rounded p-3 bg-light min-vh-5"
              style={{ minHeight: '60px' }}
              dangerouslySetInnerHTML={{
                __html: DOMPurify.sanitize(descriptionValue) || '<em class="text-muted">No description yet...</em>',
              }}

          <div className="d-flex gap-2">
            <button type="submit" className="btn btn-primary" disabled={isSubmitting}>
              {isSubmitting ? <><span className="spinner-border spinner-border-sm me-2" />Saving...</> : 'Save Book'}
            </button>
            <button type="button" className="btn btn-secondary" onClick={() => navigate('/books')}>
              Cancel
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
