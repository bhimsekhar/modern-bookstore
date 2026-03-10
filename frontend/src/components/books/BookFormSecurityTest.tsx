import React from 'react';
import { render, fireEvent, waitFor } from '@testing-library/react';
import { BookForm } from './BookForm';

describe('BookForm', () => {
  it('should prevent XSS attack', async () => {
    const onSubmit = jest.fn();
    const { getByLabelText } = render(
      <BookForm
        defaultValues={{}}
        onSubmit={onSubmit}
        isSubmitting={false}
        title="Test Book"
      />
    );

    const descriptionInput = getByLabelText('Description');
    fireEvent.change(descriptionInput, { target: { value: '<script>alert("XSS")</script>' } });

    await waitFor(() => {
      const descriptionPreview = document.querySelector('.border.rounded.p-3.bg-light.min-vh-5');
      expect(descriptionPreview?.innerHTML).not.toContain('<script>alert("XSS")</script>');
    });
  });

  it('should preserve normal behavior', async () => {
    const onSubmit = jest.fn();
    const { getByLabelText } = render(
      <BookForm
        defaultValues={{}}
        onSubmit={onSubmit}
        isSubmitting={false}
        title="Test Book"
      />
    );

    const descriptionInput = getByLabelText('Description');
    fireEvent.change(descriptionInput, { target: { value: 'Hello, <b>world</b>!' } });

    await waitFor(() => {
      const descriptionPreview = document.querySelector('.border.rounded.p-3.bg-light.min-vh-5');
      expect(descriptionPreview?.innerHTML).toContain('Hello, <b>world</b>!');
    });
  });
});