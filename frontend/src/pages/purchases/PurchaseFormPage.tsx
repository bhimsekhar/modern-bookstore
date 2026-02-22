import { useNavigate } from 'react-router-dom';
import PurchaseForm from '../../components/purchases/PurchaseForm';
import { useCreatePurchase } from '../../hooks/usePurchases';
import type { PurchaseFormData } from '../../schemas/purchaseSchema';

export default function PurchaseFormPage() {
  const navigate = useNavigate();
  const createPurchase = useCreatePurchase();

  const onSubmit = (data: PurchaseFormData) => {
    createPurchase.mutate(data, { onSuccess: () => navigate('/purchases') });
  };

  return (
    <div className="container mt-4">
      <PurchaseForm title="Record New Purchase" onSubmit={onSubmit} isSubmitting={createPurchase.isPending} />
    </div>
  );
}
