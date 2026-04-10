import React, { useState } from 'react';
import StudentList from './components/StudentList';
import StudentForm from './components/StudentForm';
import './App.css';

function App() {
  const [view, setView] = useState('list'); // 'list' | 'add'
  const [refresh, setRefresh] = useState(0);

  const handleAdded = () => {
    setView('list');
    setRefresh(r => r + 1);
  };

  return (
    <div className="app-wrapper">
      {/* Header */}
      <header className="app-header">
        <div className="header-inner">
          <div>
            <span className="header-badge">FSAD Skill-13</span>
            <h1 className="header-title">Student Management System</h1>
            <p className="header-sub">Spring Boot + React · Deployed Full Stack</p>
          </div>
          <nav className="header-nav">
            <button
              className={`nav-btn ${view === 'list' ? 'active' : ''}`}
              onClick={() => setView('list')}
            >
              Student List
            </button>
            <button
              className={`nav-btn ${view === 'add' ? 'active' : ''}`}
              onClick={() => setView('add')}
            >
              + Add Student
            </button>
          </nav>
        </div>
      </header>

      {/* Env info bar */}
      <div className="env-bar">
        <span>API: <code>{process.env.REACT_APP_API_URL}</code></span>
        <span>ENV: <code>{process.env.REACT_APP_ENV}</code></span>
      </div>

      {/* Main content */}
      <main className="app-main">
        {view === 'list' && <StudentList key={refresh} />}
        {view === 'add'  && <StudentForm onSuccess={handleAdded} onCancel={() => setView('list')} />}
      </main>

      <footer className="app-footer">
        Department of CSE / CS&IT / AI&DS · Course: 24SDCS02
      </footer>
    </div>
  );
}

export default App;
