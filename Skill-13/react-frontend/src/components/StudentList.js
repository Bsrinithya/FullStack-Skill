import React, { useEffect, useState } from 'react';
import { getAllStudents, deleteStudent } from '../services/api';
import './StudentList.css';

function StudentList() {
  const [students, setStudents] = useState([]);
  const [loading,  setLoading]  = useState(true);
  const [error,    setError]    = useState(null);

  const fetchStudents = async () => {
    try {
      setLoading(true);
      setError(null);
      const res = await getAllStudents();
      setStudents(res.data);
    } catch (err) {
      setError('Could not reach backend. Is the Spring Boot JAR running on port 8080?');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { fetchStudents(); }, []);

  const handleDelete = async (id) => {
    if (!window.confirm('Delete this student?')) return;
    try {
      await deleteStudent(id);
      setStudents(prev => prev.filter(s => s.id !== id));
    } catch {
      alert('Delete failed.');
    }
  };

  if (loading) return <div className="sl-state">Loading students...</div>;
  if (error)   return <div className="sl-state sl-error">{error}</div>;

  return (
    <div className="sl-wrap">
      <div className="sl-topbar">
        <h2 className="sl-heading">All Students</h2>
        <span className="sl-count">{students.length} record{students.length !== 1 ? 's' : ''}</span>
      </div>

      {students.length === 0 ? (
        <div className="sl-empty">No students found. Add one using the button above.</div>
      ) : (
        <div className="sl-table-wrap">
          <table className="sl-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Course</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {students.map(s => (
                <tr key={s.id}>
                  <td className="sl-id">#{s.id}</td>
                  <td className="sl-name">{s.name}</td>
                  <td>{s.email}</td>
                  <td><span className="sl-badge">{s.course}</span></td>
                  <td>
                    <button
                      className="sl-del-btn"
                      onClick={() => handleDelete(s.id)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default StudentList;
