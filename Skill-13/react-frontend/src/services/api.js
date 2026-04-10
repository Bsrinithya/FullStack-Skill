import axios from 'axios';

// TASK 3: API base URL read from environment variable
const API_BASE = process.env.REACT_APP_API_URL || 'http://localhost:8080';

const api = axios.create({
  baseURL: `${API_BASE}/api`,
  headers: { 'Content-Type': 'application/json' },
  timeout: 10000,
});

// ── Student APIs ──────────────────────────────────────
export const getAllStudents  = ()         => api.get('/students');
export const getStudentById = (id)       => api.get(`/students/${id}`);
export const addStudent     = (student)  => api.post('/students', student);
export const updateStudent  = (id, s)    => api.put(`/students/${id}`, s);
export const deleteStudent  = (id)       => api.delete(`/students/${id}`);

export default api;
