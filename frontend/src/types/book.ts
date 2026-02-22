export interface Book {
  id: number;
  title: string;
  author: string;
  genre: string;
  description?: string;
  copy: number;
  price: number;
}

export interface BookRequest {
  title: string;
  author: string;
  genre: string;
  description?: string;
  copy: number;
  price: number;
}
