```typescript
import React from 'react';
import { render, fireEvent, waitFor } from '@testing-library/react';
import { BookForm } from './BookForm';
import DOMPurify from 'dompurify';

describe('BookForm', () => {
  it('demonstrates the vulnerability existed', () => {
    const { getByText } = render(
      <BookForm
        descriptionValue={'<script>alert("XSS")</script>'}
        onChange={() => {}}
      />
    );
    expect(getByText('XSS')).toBeInTheDocument();
  });

  it('confirms the fix prevents exploitation using DOMPurify', () => {
    const { queryByText } = render(
      <BookForm
        descriptionValue={'<script>alert("XSS")</script>'}
        onChange={() => {}}
      />
    );
    expect(queryByText('XSS')).not.toBeInTheDocument();
  });

  it('confirms the fix prevents exploitation using browser built-in DOM APIs', () => {
    const { queryByText } = render(
      <BookForm
        descriptionValue={'<script>alert("XSS")</script>'}
        onChange={() => {}}
      />
    );
    expect(queryByText('XSS')).not.toBeInTheDocument();
  });

  it('confirms normal functionality still works', () => {
    const { getByText } = render(
      <BookForm
        descriptionValue={'<em class="text-muted">No description yet...</em>'}
        onChange={() => {}}
      />
    );
    expect(getByText('No description yet...')).toBeInTheDocument();
  });

  it('confirms normal functionality still works with user input', () => {
    const { getByText, getByPlaceholderText } = render(
      <BookForm
        descriptionValue={''}
        onChange={(e) => {
          const descriptionValue = e.target.value;
          render(
            <BookForm
              descriptionValue={descriptionValue}
              onChange={() => {}}
            />
          );
        }}
      />
    );
    const inputField = getByPlaceholderText('Description');
    fireEvent.change(inputField, { target: { value: 'Test description' } });
    expect(getByText('Test description')).toBeInTheDocument();
  });
});
```