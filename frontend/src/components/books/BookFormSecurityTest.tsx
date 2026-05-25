import React from 'react';
import { render, fireEvent, waitFor } from '@testing-library/react';
import { BookForm } from './BookForm';

describe('BookForm', () => {
  it('should prevent XSS attack', async () => {
    const onSubmit = jest.fn();
    const { getByLabelText, getByText } = render(
      <BookForm onSubmit={onSubmit} isSubmitting={false} title="Test Book" />
    );

    const descriptionInput = getByLabelText('Description');
    fireEvent.change(descriptionInput, { target: { value: '<script>alert("XSS")</script>' } });

    await waitFor(() => {
      expect(getByText('Description Preview')).toBeInTheDocument();
    });

    const previewElement = getByText('Description Preview').parentElement;
    expect(previewElement.innerHTML).not.toContain('<script>alert("XSS")</script>');
  });

  it('should render description preview correctly', async () => {
    const onSubmit = jest.fn();
    const { getByLabelText, getByText } = render(
      <BookForm onSubmit={onSubmit} isSubmitting={false} title="Test Book" />
    );

    const descriptionInput = getByLabelText('Description');
    fireEvent.change(descriptionInput, { target: { value: 'This is a <b>test</b> description' } });

    await waitFor(() => {
      expect(getByText('Description Preview')).toBeInTheDocument();
    });

    const previewElement = getByText('Description Preview').parentElement;
    expect(previewElement.innerHTML).toContain('This is a <b>test</b> description');
  });
});