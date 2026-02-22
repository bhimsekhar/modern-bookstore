import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { useNavigate } from 'react-router-dom';
import FormField from '../common/FormField';
import { purchaseSchema, type PurchaseFormData } from '../../schemas/purchaseSchema';

interface PurchaseFormProps {
  defaultValues?: Partial<PurchaseFormData>;
  onSubmit: (data: PurchaseFormData) => void;
  isSubmitting: boolean;
  title: string;
  datePurchased?: string;
}

export default function PurchaseForm({ defaultValues, onSubmit, isSubmitting, title, datePurchased }: PurchaseFormProps) {
  const navigate = useNavigate();
  const { register, handleSubmit, reset, formState: { errors } } = useForm<PurchaseFormData>({
    resolver: zodResolver(purchaseSchema),
    defaultValues,
  });

  useEffect(() => {
    if (defaultValues) reset(defaultValues);
  }, [defaultValues, reset]);

  return (
    <div className="card shadow-sm">
      <div className="card-header bg-warning text-dark">
        <h5 className="mb-0">{title}</h5>
      </div>
      <div className="card-body">
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="row">
            <div className="col-md-6">
              <FormField label="Buyer Full Name" name="name" register={register} error={errors.name} required placeholder="Enter buyer's name" />
            </div>
            <div className="col-md-6">
              <FormField label="Phone Number" name="phone" register={register} error={errors.phone} required placeholder="+1 555-0123" />
            </div>
          </div>
          <FormField label="Books Purchased" name="books" type="textarea" register={register} error={errors.books} required placeholder="Enter book title(s), comma-separated" rows={3} />
          <div className="row">
            <div className="col-md-6">
              <FormField label="Quantity" name="quantity" type="number" register={register} error={errors.quantity} required placeholder="1" />
            </div>
            <div className="col-md-6">
              <FormField label="Total Price ($)" name="totalPrice" type="number" register={register} error={errors.totalPrice} required placeholder="0.00" />
            </div>
          </div>
          {datePurchased && (
            <div className="mb-3">
              <label className="form-label fw-semibold">Date Purchased</label>
              <input type="text" className="form-control" value={new Date(datePurchased).toLocaleString()} readOnly />
              <div className="form-text text-muted">System-generated — cannot be changed</div>
            </div>
          )}
          <div className="d-flex gap-2">
            <button type="submit" className="btn btn-warning" disabled={isSubmitting}>
              {isSubmitting ? <><span className="spinner-border spinner-border-sm me-2" />Saving...</> : 'Save Purchase'}
            </button>
            <button type="button" className="btn btn-secondary" onClick={() => navigate('/purchases')}>
              Cancel
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
