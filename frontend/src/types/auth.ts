export interface AuthUser {
  username: string;
  firstname: string;
  roles: string[];
}

export interface JwtResponse {
  token: string;
  tokenType: string;
  expiresIn: number;
  username: string;
  firstname: string;
  roles: string[];
}

export interface PagedResponse<T> {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

export interface ApiResponse<T> {
  status: string;
  data: T;
  message?: string;
}
