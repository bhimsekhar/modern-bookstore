import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import FormField from '../../components/common/FormField';
import { loginSchema, type LoginFormData } from '../../schemas/authSchema';
import { useAuth } from '../../context/AuthContext';

export default function LoginPage() {
  const { login } = useAuth();
  const navigate = useNavigate();
  const { register, handleSubmit, formState: { errors, isSubmitting } } = useForm<LoginFormData>({
    resolver: zodResolver(loginSchema),
  });

  const onSubmit = async (data: LoginFormData) => {
    try {
      await login(data.username, data.password);
      toast.success('Welcome back!');
      navigate('/books');
    } catch (err: any) {
      toast.error(err.response?.data?.message || 'Invalid username or password');
    }
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-5">
          <div className="card shadow">
            <div className="card-header bg-dark text-white text-center py-3">
              <h4 className="mb-0">📚 Bookstore Login</h4>
            </div>
            <div className="card-body p-4">
              <form onSubmit={handleSubmit(onSubmit)}>
                <FormField label="Username" name="username" register={register} error={errors.username} required placeholder="Enter your username" />
                <FormField label="Password" name="password" type="password" register={register} error={errors.password} required placeholder="Enter your password" />
                <button type="submit" className="btn btn-dark w-100 mt-2" disabled={isSubmitting}>
                  {isSubmitting ? <><span className="spinner-border spinner-border-sm me-2" />Logging in...</> : 'Login'}
                </button>
              </form>
              <hr />
              <p className="text-center mb-0">
                Don't have an account? <Link to="/register">Register here</Link>
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
