import { BrowserRouter, Routes, Route, Outlet } from 'react-router-dom';
import { QueryClientProvider } from '@tanstack/react-query';
import { ToastContainer } from 'react-toastify';
import { AuthProvider } from './context/AuthContext';
import { queryClient } from './hooks/queryClient';
import ProtectedRoute from './routes/ProtectedRoute';
import AdminRoute from './routes/AdminRoute';
import NavBar from './components/common/NavBar';

// Pages
import HomePage from './pages/HomePage';
import NotFoundPage from './pages/NotFoundPage';
import LoginPage from './pages/auth/LoginPage';
import RegisterPage from './pages/auth/RegisterPage';
import BookListPage from './pages/books/BookListPage';
import BookFormPage from './pages/books/BookFormPage';
import BookEditPage from './pages/books/BookEditPage';
import EmployeeListPage from './pages/employees/EmployeeListPage';
import EmployeeFormPage from './pages/employees/EmployeeFormPage';
import EmployeeEditPage from './pages/employees/EmployeeEditPage';
import PurchaseListPage from './pages/purchases/PurchaseListPage';
import PurchaseFormPage from './pages/purchases/PurchaseFormPage';
import PurchaseEditPage from './pages/purchases/PurchaseEditPage';
import FeedbackFormPage from './pages/feedback/FeedbackFormPage';
import FeedbackSuccessPage from './pages/feedback/FeedbackSuccessPage';
import FeedbackListPage from './pages/feedback/FeedbackListPage';

function Layout() {
  return (
    <>
      <NavBar />
      <main>
        <Outlet />
      </main>
    </>
  );
}

function AppRoutes() {
  return (
    <Routes>
      {/* Auth routes — no layout */}
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />

      {/* All routes with layout */}
      <Route element={<Layout />}>
        <Route path="/" element={<HomePage />} />
        <Route path="/feedback/new" element={<FeedbackFormPage />} />
        <Route path="/feedback/success" element={<FeedbackSuccessPage />} />

        {/* Protected — any authenticated user */}
        <Route path="/books" element={<ProtectedRoute><BookListPage /></ProtectedRoute>} />
        <Route path="/books/new" element={<AdminRoute><BookFormPage /></AdminRoute>} />
        <Route path="/books/:id/edit" element={<AdminRoute><BookEditPage /></AdminRoute>} />

        {/* Admin only */}
        <Route path="/employees" element={<AdminRoute><EmployeeListPage /></AdminRoute>} />
        <Route path="/employees/new" element={<AdminRoute><EmployeeFormPage /></AdminRoute>} />
        <Route path="/employees/:id/edit" element={<AdminRoute><EmployeeEditPage /></AdminRoute>} />
        <Route path="/admin/feedback" element={<AdminRoute><FeedbackListPage /></AdminRoute>} />

        {/* Admin + Employee */}
        <Route path="/purchases" element={<ProtectedRoute><PurchaseListPage /></ProtectedRoute>} />
        <Route path="/purchases/new" element={<ProtectedRoute><PurchaseFormPage /></ProtectedRoute>} />
        <Route path="/purchases/:id/edit" element={<ProtectedRoute><PurchaseEditPage /></ProtectedRoute>} />

        <Route path="*" element={<NotFoundPage />} />
      </Route>
    </Routes>
  );
}

export default function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <AuthProvider>
          <AppRoutes />
          <ToastContainer
            position="top-right"
            autoClose={3000}
            hideProgressBar={false}
            newestOnTop
            closeOnClick
            pauseOnHover
          />
        </AuthProvider>
      </BrowserRouter>
    </QueryClientProvider>
  );
}
