import ProtectedRoute from './ProtectedRoute';

export default function AdminRoute({ children }: { children: React.ReactNode }) {
  return <ProtectedRoute requiredRole="ROLE_ADMIN">{children}</ProtectedRoute>;
}
