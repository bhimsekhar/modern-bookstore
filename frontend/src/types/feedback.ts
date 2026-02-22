export interface Feedback {
  id: number;
  name: string;
  phone?: string;
  email?: string;
  feedback: string;
  dateCreated: string;
}

export interface FeedbackRequest {
  name: string;
  phone?: string;
  email?: string;
  feedback: string;
}
