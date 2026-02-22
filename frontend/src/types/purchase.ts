export interface Purchase {
  id: number;
  name: string;
  phone: string;
  books: string;
  quantity: number;
  totalPrice: number;
  datePurchased: string;
}

export interface PurchaseRequest {
  name: string;
  phone: string;
  books: string;
  quantity: number;
  totalPrice: number;
}
