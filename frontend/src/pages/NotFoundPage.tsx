import { Link } from 'react-router-dom';

export default function NotFoundPage() {
  return (
    <div className="container mt-5 text-center">
      <h1 className="display-1 text-muted">404</h1>
      <h2>Page Not Found</h2>
      <p className="text-muted">The page you're looking for doesn't exist.</p>
      <Link to="/" className="btn btn-primary">Go Home</Link>
    </div>
  );
}
