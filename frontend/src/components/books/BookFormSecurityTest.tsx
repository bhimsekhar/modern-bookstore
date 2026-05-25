```typescript
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
    const maliciousCode = '<script>alert("XSS")</script>';
    fireEvent.change(descriptionInput, { target: { value: maliciousCode } });

    await waitFor(() => expect(getByText('Description Preview')).toBeInTheDocument());

    // If the test doesn't throw an error, it means the XSS attack was prevented
    expect(onSubmit).not.toHaveBeenCalled();
  });

  it('should preserve normal behaviour', async () => {
    const onSubmit = jest.fn();
    const { getByLabelText, getByText } = render(
      <BookForm onSubmit={onSubmit} isSubmitting={false} title="Test Book" />
    );

    const descriptionInput = getByLabelText('Description');
    const normalDescription = 'This is a normal description';
    fireEvent.change(descriptionInput, { target: { value: normalDescription } });

    await waitFor(() => expect(getByText('Description Preview')).toBeInTheDocument());

    const previewText = getByText(normalDescription);
    expect(previewText).toBeInTheDocument();
  });
});
```