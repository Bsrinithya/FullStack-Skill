import React from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';

function Navbar() {
  const navigate = useNavigate();
  const location = useLocation();

  const handleLogout = () => {
    localStorage.removeItem('loggedInUser');
    sessionStorage.removeItem('loggedInUser');
    navigate('/login');
  };

  return (
    <nav>
      <Link to="/home" className="nav-brand">◈ AuthApp</Link>
      <div className="nav-links">
        <Link to="/home" className={`nav-link ${location.pathname === '/home' ? 'active' : ''}`}>Home</Link>
        <Link to="/profile" className={`nav-link ${location.pathname === '/profile' ? 'active' : ''}`}>Profile</Link>
        <button className="btn btn-outline" onClick={handleLogout}>Logout</button>
      </div>
    </nav>
  );
}

export default Navbar;
