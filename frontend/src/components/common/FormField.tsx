import type { FieldError, UseFormRegister } from 'react-hook-form';

interface FormFieldProps {
  label: string;
  name: string;
  type?: 'text' | 'number' | 'email' | 'password' | 'textarea';
  placeholder?: string;
  register: UseFormRegister<any>;
  error?: FieldError;
  required?: boolean;
  readOnly?: boolean;
  rows?: number;
}

export default function FormField({
  label, name, type = 'text', placeholder, register, error, required, readOnly, rows = 3
}: FormFieldProps) {
  return (
    <div className="mb-3">
      <label htmlFor={name} className="form-label fw-semibold">
        {label}{required && <span className="text-danger ms-1">*</span>}
      </label>
      {type === 'textarea' ? (
        <textarea
          id={name}
          className={`form-control ${error ? 'is-invalid' : ''}`}
          placeholder={placeholder}
          rows={rows}
          readOnly={readOnly}
          {...register(name)}
        />
      ) : (
        <input
          id={name}
          type={type}
          className={`form-control ${error ? 'is-invalid' : ''}`}
          placeholder={placeholder}
          readOnly={readOnly}
          step={type === 'number' ? 'any' : undefined}
          {...register(name)}
        />
      )}
      {error && <div className="invalid-feedback">{error.message}</div>}
    </div>
  );
}
