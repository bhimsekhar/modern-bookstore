import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import FormField from '../../components/common/FormField';
import { registerSchema, type RegisterFormData } from '../../schemas/authSchema';
import * as authApi from '../../api/auth';

export default function RegisterPage() {
  const navigate = useNavigate();
  const { register, handleSubmit, formState: { errors, isSubmitting } } = useForm<RegisterFormData>({
    resolver: zodResolver(registerSchema),
  });

  const onSubmit = async (data: RegisterFormData) => {
    try {
      await authApi.register(data);
      toast.success('Registration successful! Please login.');
      navigate('/login');
    } catch (err: any) {
      toast.error(err.response?.data?.message || 'Registration failed');
    }
  };

  return (
    <div className="container mt-4">
      <div className="row justify-content-center">
        <div className="col-md-7">
          <div className="card shadow">
            <div className="card-header bg-dark text-white text-center py-3">
              <h4 className="mb-0">📚 Create Account</h4>
            </div>
            <div className="card-body p-4">
              <form onSubmit={handleSubmit(onSubmit)}>
                <div className="row">
                  <div className="col-md-6">
                    <FormField label="Username" name="username" register={register} error={errors.username} required placeholder="3-50 chars, alphanumeric" />
                  </div>
                  <div className="col-md-6">
                    <FormField label="Password" name="password" type="password" register={register} error={errors.password} required placeholder="Min 8 chars, letter + digit" />
                  </div>
                </div>
                <div className="row">
                  <div className="col-md-6">
                    <FormField label="First Name" name="firstname" register={register} error={errors.firstname} required placeholder="Your first name" />
                  </div>
                  <div className="col-md-6">
                    <FormField label="Last Name" name="lastname" register={register} error={errors.lastname} required placeholder="Your last name" />
                  </div>
                </div>
                <FormField label="Email" name="email" type="email" register={register} error={errors.email} required placeholder="you@example.com" />
                <FormField label="Address (optional)" name="address" type="textarea" register={register} error={errors.address} placeholder="Your address" rows={2} />
                <FormField label="Phone (optional, digits only)" name="phone" register={register} error={errors.phone} placeholder="1234567890" />
                <button type="submit" className="btn btn-dark w-100 mt-2" disabled={isSubmitting}>
                  {isSubmitting ? <><span className="spinner-border spinner-border-sm me-2" />Creating account...</> : 'Create Account'}
                </button>
              </form>
              <hr />
              <p className="text-center mb-0">
                Already have an account? <Link to="/login">Login here</Link>
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
