import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function HomePage() {
  const { token, isAdmin } = useAuth();

  return (
    <div className="container mt-4">
      <div className="p-5 mb-4 bg-dark text-white rounded-3 text-center">
        <h1 className="display-5 fw-bold">📚 Bookstore Management System</h1>
        <p className="lead mt-3">
          A modern platform to manage your book inventory, employees, purchases, and customer feedback.
        </p>
        {!token ? (
          <div className="mt-3">
            <Link to="/login" className="btn btn-primary btn-lg me-3">Login</Link>
            <Link to="/register" className="btn btn-outline-light btn-lg me-3">Register</Link>
            <Link to="/feedback/new" className="btn btn-info btn-lg">Submit Feedback</Link>
          </div>
        ) : (
          <Link to="/books" className="btn btn-primary btn-lg mt-2">Manage Books</Link>
        )}
      </div>

      <div className="row g-4 mt-2">
        <div className="col-md-3">
          <div className="card h-100 shadow-sm border-primary">
            <div className="card-body text-center">
              <div style={{ fontSize: '2.5rem' }}>📖</div>
              <h5 className="card-title mt-2">Books</h5>
              <p className="card-text text-muted">Manage the complete book inventory with full CRUD operations.</p>
              {token && <Link to="/books" className="btn btn-primary btn-sm">View Books</Link>}
            </div>
          </div>
        </div>
        <div className="col-md-3">
          <div className="card h-100 shadow-sm border-success">
            <div className="card-body text-center">
              <div style={{ fontSize: '2.5rem' }}>👷</div>
              <h5 className="card-title mt-2">Employees</h5>
              <p className="card-text text-muted">Track employee records, departments, and salaries.</p>
              {token && isAdmin() && <Link to="/employees" className="btn btn-success btn-sm">View Employees</Link>}
            </div>
          </div>
        </div>
        <div className="col-md-3">
          <div className="card h-100 shadow-sm border-warning">
            <div className="card-body text-center">
              <div style={{ fontSize: '2.5rem' }}>🛒</div>
              <h5 className="card-title mt-2">Purchases</h5>
              <p className="card-text text-muted">Record and track all book sales and transactions.</p>
              {token && <Link to="/purchases" className="btn btn-warning btn-sm text-dark">View Purchases</Link>}
            </div>
          </div>
        </div>
        <div className="col-md-3">
          <div className="card h-100 shadow-sm border-info">
            <div className="card-body text-center">
              <div style={{ fontSize: '2.5rem' }}>💬</div>
              <h5 className="card-title mt-2">Feedback</h5>
              <p className="card-text text-muted">Collect and review customer feedback about our store.</p>
              {token && isAdmin()
                ? <Link to="/admin/feedback" className="btn btn-info btn-sm">View Feedback</Link>
                : <Link to="/feedback/new" className="btn btn-info btn-sm">Submit Feedback</Link>
              }
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
