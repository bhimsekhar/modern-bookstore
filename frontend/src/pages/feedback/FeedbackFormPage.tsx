import { useNavigate } from 'react-router-dom';
import FeedbackForm from '../../components/feedback/FeedbackForm';
import { useSubmitFeedback } from '../../hooks/useFeedback';
import type { FeedbackFormData } from '../../schemas/feedbackSchema';

export default function FeedbackFormPage() {
  const navigate = useNavigate();
  const submitFeedback = useSubmitFeedback();

  const onSubmit = (data: FeedbackFormData) => {
    submitFeedback.mutate(
      { name: data.name, phone: data.phone || undefined, email: data.email || undefined, feedback: data.feedback },
      { onSuccess: () => navigate('/feedback/success') }
    );
  };

  return (
    <div className="container mt-4">
      <div className="row justify-content-center">
        <div className="col-md-8">
          <div className="card shadow">
            <div className="card-header bg-info text-white">
              <h4 className="mb-0">💬 Share Your Feedback</h4>
            </div>
            <div className="card-body p-4">
              <p className="text-muted">We'd love to hear from you! No login required.</p>
              <FeedbackForm onSubmit={onSubmit} isSubmitting={submitFeedback.isPending} />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
