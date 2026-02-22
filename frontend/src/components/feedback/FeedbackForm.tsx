import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import FormField from '../common/FormField';
import { feedbackSchema, type FeedbackFormData } from '../../schemas/feedbackSchema';

interface FeedbackFormProps {
  onSubmit: (data: FeedbackFormData) => void;
  isSubmitting: boolean;
}

export default function FeedbackForm({ onSubmit, isSubmitting }: FeedbackFormProps) {
  const { register, handleSubmit, formState: { errors } } = useForm<FeedbackFormData>({
    resolver: zodResolver(feedbackSchema),
  });

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <FormField label="Full Name" name="name" register={register} error={errors.name} required placeholder="Your name" />
      <div className="row">
        <div className="col-md-6">
          <FormField label="Phone Number (optional)" name="phone" register={register} error={errors.phone} placeholder="+1 555-0123" />
        </div>
        <div className="col-md-6">
          <FormField label="Email Address (optional)" name="email" type="email" register={register} error={errors.email} placeholder="you@example.com" />
        </div>
      </div>
      <div className="form-text text-muted mb-2">
        Please provide at least one contact method (phone or email).
      </div>
      <FormField label="Your Feedback" name="feedback" type="textarea" register={register} error={errors.feedback} required placeholder="Tell us what you think... (min 10 characters)" rows={5} />
      <button type="submit" className="btn btn-primary w-100" disabled={isSubmitting}>
        {isSubmitting ? <><span className="spinner-border spinner-border-sm me-2" />Submitting...</> : 'Submit Feedback'}
      </button>
    </form>
  );
}
