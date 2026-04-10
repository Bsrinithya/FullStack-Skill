import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Navbar from '../components/Navbar';

function Profile() {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const username = sessionStorage.getItem('loggedInUser') || localStorage.getItem('loggedInUser');
    if (!username) { navigate('/login'); return; }

    fetch(`http://localhost:8080/api/auth/profile/${username}`)
      .then(res => {
        if (!res.ok) throw new Error('User not found');
        return res.json();
      })
      .then(data => setUser(data))
      .catch(() => setError('Failed to load profile. Is the backend running?'))
      .finally(() => setLoading(false));
  }, [navigate]);

  const initial = user ? user.fullName?.charAt(0).toUpperCase() : '?';

  return (
    <>
      <Navbar />
      <div className="page" style={{ paddingTop: 100 }}>
        <div className="card profile-card">
          <h1 style={{ textAlign: 'center', marginBottom: 4 }}>My Profile</h1>
          <p className="subtitle" style={{ textAlign: 'center' }}>Fetched from database</p>

          {loading && <div className="spinner" />}
          {error && <div className="alert alert-error">{error}</div>}

          {user && (
            <>
              <div className="avatar">{initial}</div>
              <div className="profile-field">
                <span className="field-label">Full Name</span>
                <span className="field-value">{user.fullName}</span>
              </div>
              <div className="profile-field">
                <span className="field-label">Username</span>
                <span className="field-value">@{user.username}</span>
              </div>
              <div className="profile-field">
                <span className="field-label">Email</span>
                <span className="field-value">{user.email}</span>
              </div>
              <div className="profile-field">
                <span className="field-label">User ID</span>
                <span className="field-value" style={{ color: 'var(--muted)', fontSize: '0.8rem' }}>#{user.id}</span>
              </div>
              <div style={{ marginTop: 24, textAlign: 'center' }}>
                <button className="btn btn-danger" style={{ width: 'auto', padding: '10px 28px' }}
                  onClick={() => { localStorage.removeItem('loggedInUser'); sessionStorage.removeItem('loggedInUser'); navigate('/login'); }}>
                  Logout
                </button>
              </div>
            </>
          )}
        </div>
      </div>
    </>
  );
}

export default Profile;
