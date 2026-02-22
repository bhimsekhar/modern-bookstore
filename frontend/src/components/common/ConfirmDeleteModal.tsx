interface ConfirmDeleteModalProps {
  isOpen: boolean;
  itemName: string;
  onConfirm: () => void;
  onCancel: () => void;
  isDeleting: boolean;
}

export default function ConfirmDeleteModal({
  isOpen, itemName, onConfirm, onCancel, isDeleting
}: ConfirmDeleteModalProps) {
  if (!isOpen) return null;

  return (
    <div className="modal d-block" style={{ backgroundColor: 'rgba(0,0,0,0.5)' }} tabIndex={-1}>
      <div className="modal-dialog modal-dialog-centered">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title">Confirm Delete</h5>
            <button type="button" className="btn-close" onClick={onCancel} disabled={isDeleting} />
          </div>
          <div className="modal-body">
            <p>Are you sure you want to delete <strong>{itemName}</strong>?</p>
            <p className="text-muted small">This action cannot be undone.</p>
          </div>
          <div className="modal-footer">
            <button type="button" className="btn btn-secondary" onClick={onCancel} disabled={isDeleting}>
              Cancel
            </button>
            <button type="button" className="btn btn-danger" onClick={onConfirm} disabled={isDeleting}>
              {isDeleting ? (
                <><span className="spinner-border spinner-border-sm me-2" />Deleting...</>
              ) : 'Delete'}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
