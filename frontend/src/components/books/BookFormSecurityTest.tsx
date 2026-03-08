import React from 'react';
import { render, fireEvent, waitFor } from '@testing-library/react';
import { BookForm } from './BookForm';

describe('BookForm', () => {
  it('should prevent XSS attacks', async () => {
    const { getByLabelText, getByText } = render(<BookForm onSubmit={jest.fn()} isSubmitting={false} title="Test" />);
    const descriptionInput = getByLabelText('Description');
    fireEvent.change(descriptionInput, { target: { value: '<script>alert("XSS")</script>' } });
    await waitFor(() => expect(getByText('Description Preview')).toBeInTheDocument());
    expect(getByText('Description Preview')).not.toContainHTML('<script>alert("XSS")</script>');
  });

  it('should render description correctly', async () => {
    const { getByLabelText, getByText } = render(<BookForm onSubmit={jest.fn()} isSubmitting={false} title="Test" />);
    const descriptionInput = getByLabelText('Description');
    fireEvent.change(descriptionInput, { target: { value: 'Hello, <b>world</b>!' } });
    await waitFor(() => expect(getByText('Description Preview')).toBeInTheDocument());
    expect(getByText('Description Preview')).toContainHTML('Hello, <b>world</b>!');
  });
});