import { useState } from 'react'
import Dashboard from './components/Dashboard'
import LocalUserList from './components/LocalUserList'
import UserList from './components/UserList'
import FakePostList from './components/FakePostList'
import './App.css'

function App() {
  const [x, setX] = useState('home')

  return (
    <div>
      <Dashboard setPage={setX} />

      {x === 'home' && (
        <div className="home-text">
          <h2>Welcome</h2>
          <p>Click any button above to view data.</p>
        </div>
      )}

      {x === 'local' && <LocalUserList />}
      {x === 'users' && <UserList />}
      {x === 'posts' && <FakePostList />}
    </div>
  )
}

export default App