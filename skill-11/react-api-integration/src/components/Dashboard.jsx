import './Navbar.css'

function Dashboard({ setPage }) {
  return (
    <div className="container">
      <h1>React API Integration Dashboard</h1>

      <div className="btn-group">
        <button onClick={() => setPage('home')}>Home</button>
        <button onClick={() => setPage('local')}>Local Users</button>
        <button onClick={() => setPage('users')}>Users API</button>
        <button onClick={() => setPage('posts')}>Fake API Posts</button>
      </div>
    </div>
  )
}

export default Dashboard