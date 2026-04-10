import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

function Register() {
  const navigate = useNavigate();
  const [form, setForm] = useState({ username: '', email: '', password: '', fullName: '' });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(''); setSuccess('');
    if (!form.username || !form.email || !form.password || !form.fullName) {
      setError('All fields are required.'); return;
    }
    setLoading(true);
    try {
      const res = await fetch('http://localhost:8080/api/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(form),
      });
      const data = await res.json();
      if (!res.ok) { setError(data.message || 'Registration failed.'); return; }
      setSuccess('Account created! Redirecting to login…');
      setTimeout(() => navigate('/login'), 1500);
    } catch {
      setError('Cannot reach server. Make sure the backend is running.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="page">
      <div className="card">
        <h1>Create Account</h1>
        <p className="subtitle">Skill-14 · FSAD Workbook</p>

        {error   && <div className="alert alert-error">{error}</div>}
        {success && <div className="alert alert-success">{success}</div>}

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Full Name</label>
            <input name="fullName" placeholder="John Doe" value={form.fullName} onChange={handleChange} />
          </div>
          <div className="form-group">
            <label>Username</label>
            <input name="username" placeholder="johndoe" value={form.username} onChange={handleChange} />
          </div>
          <div className="form-group">
            <label>Email</label>
            <input name="email" type="email" placeholder="john@example.com" value={form.email} onChange={handleChange} />
          </div>
          <div className="form-group">
            <label>Password</label>
            <input name="password" type="password" placeholder="••••••••" value={form.password} onChange={handleChange} />
          </div>
          <button className="btn btn-primary" type="submit" disabled={loading}>
            {loading ? 'Registering…' : 'Register →'}
          </button>
        </form>

        <p className="link-text">Already have an account? <Link to="/login">Sign in</Link></p>
      </div>
    </div>
  );
}

export default Register;
