import { Link, NavLink } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';

export default function NavBar() {
  const { user, token, logout, isAdmin } = useAuth();

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
      <div className="container">
        <Link className="navbar-brand fw-bold" to="/">
          📚 Bookstore
        </Link>
        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav me-auto">
            <li className="nav-item">
              <NavLink className="nav-link" to="/">Home</NavLink>
            </li>
            {token && (
              <li className="nav-item">
                <NavLink className="nav-link" to="/books">Books</NavLink>
              </li>
            )}
            {token && isAdmin() && (
              <li className="nav-item">
                <NavLink className="nav-link" to="/employees">Employees</NavLink>
              </li>
            )}
            {token && (
              <li className="nav-item">
                <NavLink className="nav-link" to="/purchases">Purchases</NavLink>
              </li>
            )}
            {token && isAdmin() && (
              <li className="nav-item">
                <NavLink className="nav-link" to="/admin/feedback">Feedback</NavLink>
              </li>
            )}
            {!token && (
              <li className="nav-item">
                <NavLink className="nav-link" to="/feedback/new">Submit Feedback</NavLink>
              </li>
            )}
          </ul>

          <ul className="navbar-nav">
            {!token ? (
              <>
                <li className="nav-item">
                  <NavLink className="nav-link" to="/login">Login</NavLink>
                </li>
                <li className="nav-item">
                  <NavLink className="btn btn-outline-light btn-sm ms-2" to="/register">Register</NavLink>
                </li>
              </>
            ) : (
              <>
                <li className="nav-item">
                  <span className="nav-link text-light">Welcome, {user?.firstname}</span>
                </li>
                <li className="nav-item">
                  <button className="btn btn-outline-light btn-sm ms-2" onClick={logout}>
                    Logout
                  </button>
                </li>
              </>
            )}
          </ul>
        </div>
      </div>
    </nav>
  );
}
