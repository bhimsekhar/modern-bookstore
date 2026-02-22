import { Link } from 'react-router-dom';

export default function FeedbackSuccessPage() {
  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-6 text-center">
          <div className="card shadow border-success">
            <div className="card-body py-5">
              <div style={{ fontSize: '4rem' }}>✅</div>
              <h2 className="text-success mt-3">Thank You!</h2>
              <p className="text-muted lead mt-2">
                Your feedback has been submitted successfully.<br />
                We appreciate you taking the time to share your thoughts.
              </p>
              <hr />
              <Link to="/" className="btn btn-primary me-2">Back to Home</Link>
              <Link to="/feedback/new" className="btn btn-outline-secondary">Submit More Feedback</Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
