import React from 'react';
import { render, fireEvent, waitFor } from '@testing-library/react';
import { BookForm } from './BookForm';

describe('BookForm', () => {
  it('should prevent XSS attack', async () => {
    const onSubmit = jest.fn();
    const { getByLabelText, getByText } = render(
      <BookForm
        defaultValues={{}}
        onSubmit={onSubmit}
        isSubmitting={false}
        title="Test"
      />
    );

    const descriptionInput = getByLabelText('Description');
    fireEvent.change(descriptionInput, { target: { value: '<script>alert("XSS")</script>' } });

    await waitFor(() => {
      expect(getByText('Description Preview')).toBeInTheDocument();
    });

    expect(onSubmit).not.toHaveBeenCalled();
    expect(getByText('Description Preview')).not.toContainHTML('<script>alert("XSS")</script>');
  });

  it('should render description correctly', async () => {
    const onSubmit = jest.fn();
    const { getByLabelText, getByText } = render(
      <BookForm
        defaultValues={{}}
        onSubmit={onSubmit}
        isSubmitting={false}
        title="Test"
      />
    );

    const descriptionInput = getByLabelText('Description');
    fireEvent.change(descriptionInput, { target: { value: 'Hello, World!' } });

    await waitFor(() => {
      expect(getByText('Description Preview')).toBeInTheDocument();
    });

    expect(getByText('Description Preview')).toContainHTML('Hello, World!');
  });
});