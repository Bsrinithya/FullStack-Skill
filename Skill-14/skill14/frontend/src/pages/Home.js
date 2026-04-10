import React from 'react';
import { useNavigate } from 'react-router-dom';
import Navbar from '../components/Navbar';

function Home() {
  const navigate = useNavigate();
  const user = sessionStorage.getItem('loggedInUser') || localStorage.getItem('loggedInUser');

  return (
    <>
      <Navbar />
      <div className="page home-content">
        <div className="card" style={{ textAlign: 'center', maxWidth: 560 }}>
          <div className="badge">Authenticated Session</div>
          <h1>Hello, {user}!</h1>
          <p style={{ color: 'var(--muted)', marginTop: 12, lineHeight: 1.7, fontSize: '0.9rem' }}>
            You have successfully logged in. Your session is stored in <code style={{ color: 'var(--accent)', background: 'rgba(108,99,255,0.1)', padding: '2px 6px', borderRadius: 4 }}>sessionStorage</code>.
            Navigate to your profile to view full details from the database.
          </p>
          <div className="home-actions">
            <button className="btn btn-primary" style={{ width: 'auto', padding: '11px 28px' }} onClick={() => navigate('/profile')}>
              View Profile →
            </button>
          </div>
        </div>
      </div>
    </>
  );
}

export default Home;
