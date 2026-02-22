import React, { createContext, useContext, useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import * as authApi from '../api/auth';
import type { AuthUser } from '../types/auth';

interface AuthContextType {
  user: AuthUser | null;
  token: string | null;
  login: (username: string, password: string) => Promise<void>;
  logout: () => void;
  isAdmin: () => boolean;
  isEmployee: () => boolean;
}

const AuthContext = createContext<AuthContextType | null>(null);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [user, setUser] = useState<AuthUser | null>(() => {
    const stored = localStorage.getItem('user');
    return stored ? JSON.parse(stored) : null;
  });
  const [token, setToken] = useState<string | null>(() => localStorage.getItem('token'));
  const navigate = useNavigate();

  const login = useCallback(async (username: string, password: string) => {
    const response = await authApi.login({ username, password });
    const jwt = response.data;
    const authUser: AuthUser = {
      username: jwt.username,
      firstname: jwt.firstname,
      roles: jwt.roles,
    };
    localStorage.setItem('token', jwt.token);
    localStorage.setItem('user', JSON.stringify(authUser));
    setToken(jwt.token);
    setUser(authUser);
  }, []);

  const logout = useCallback(() => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setToken(null);
    setUser(null);
    navigate('/login');
  }, [navigate]);

  const isAdmin = useCallback(() =>
    user?.roles?.includes('ROLE_ADMIN') ?? false, [user]);

  const isEmployee = useCallback(() =>
    user?.roles?.includes('ROLE_EMPLOYEE') ?? false, [user]);

  return (
    <AuthContext.Provider value={{ user, token, login, logout, isAdmin, isEmployee }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth(): AuthContextType {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth must be used within AuthProvider');
  return ctx;
}
