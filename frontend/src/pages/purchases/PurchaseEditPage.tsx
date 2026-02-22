import { useNavigate, useParams } from 'react-router-dom';
import PurchaseForm from '../../components/purchases/PurchaseForm';
import { usePurchase, useUpdatePurchase } from '../../hooks/usePurchases';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import type { PurchaseFormData } from '../../schemas/purchaseSchema';

export default function PurchaseEditPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const purchaseId = Number(id);
  const { data, isLoading } = usePurchase(purchaseId);
  const updatePurchase = useUpdatePurchase(purchaseId);

  if (isLoading) return <LoadingSpinner />;

  const purchase = data?.data;
  const defaultValues: Partial<PurchaseFormData> = purchase
    ? { name: purchase.name, phone: purchase.phone, books: purchase.books, quantity: purchase.quantity, totalPrice: purchase.totalPrice }
    : {};

  const onSubmit = (formData: PurchaseFormData) => {
    updatePurchase.mutate(formData, { onSuccess: () => navigate('/purchases') });
  };

  return (
    <div className="container mt-4">
      <PurchaseForm
        title="Edit Purchase"
        defaultValues={defaultValues}
        onSubmit={onSubmit}
        isSubmitting={updatePurchase.isPending}
        datePurchased={purchase?.datePurchased}
      />
    </div>
  );
}
