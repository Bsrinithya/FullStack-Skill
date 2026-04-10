import React, { useState } from 'react';
import { addStudent } from '../services/api';
import './StudentForm.css';

const INITIAL = { name: '', email: '', course: '' };

function StudentForm({ onSuccess, onCancel }) {
  const [form,    setForm]    = useState(INITIAL);
  const [loading, setLoading] = useState(false);
  const [error,   setError]   = useState(null);
  const [success, setSuccess] = useState(false);

  const handleChange = e => {
    setForm(prev => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async e => {
    e.preventDefault();
    if (!form.name || !form.email || !form.course) {
      setError('All fields are required.');
      return;
    }
    try {
      setLoading(true);
      setError(null);
      await addStudent(form);
      setSuccess(true);
      setTimeout(() => onSuccess(), 800);
    } catch (err) {
      setError('Failed to add student. Check if the backend is running.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="sf-wrap">
      <h2 className="sf-heading">Add New Student</h2>

      {error   && <div className="sf-msg sf-error">{error}</div>}
      {success && <div className="sf-msg sf-success">Student added! Redirecting...</div>}

      <form onSubmit={handleSubmit} className="sf-form">
        <div className="sf-group">
          <label className="sf-label">Full Name</label>
          <input
            className="sf-input"
            type="text"
            name="name"
            placeholder="e.g. Ravi Kumar"
            value={form.name}
            onChange={handleChange}
          />
        </div>

        <div className="sf-group">
          <label className="sf-label">Email Address</label>
          <input
            className="sf-input"
            type="email"
            name="email"
            placeholder="e.g. ravi@college.edu"
            value={form.email}
            onChange={handleChange}
          />
        </div>

        <div className="sf-group">
          <label className="sf-label">Course</label>
          <select className="sf-input" name="course" value={form.course} onChange={handleChange}>
            <option value="">Select a course</option>
            <option value="B.Tech CSE">B.Tech CSE</option>
            <option value="B.Tech CS&IT">B.Tech CS&IT</option>
            <option value="B.Tech AI&DS">B.Tech AI&DS</option>
            <option value="MCA">MCA</option>
            <option value="M.Tech">M.Tech</option>
          </select>
        </div>

        <div className="sf-actions">
          <button type="button" className="sf-cancel" onClick={onCancel}>Cancel</button>
          <button type="submit" className="sf-submit" disabled={loading}>
            {loading ? 'Saving...' : 'Add Student'}
          </button>
        </div>
      </form>
    </div>
  );
}

export default StudentForm;
