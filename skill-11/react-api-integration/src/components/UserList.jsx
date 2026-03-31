import { useEffect, useState } from 'react'
import './Navbar.css'

function UserList() {
  const [x, setX] = useState([])
  const [y, setY] = useState(true)
  const [z, setZ] = useState('')

  useEffect(() => {
    fetch('https://jsonplaceholder.typicode.com/users')
      .then((r) => r.json())
      .then((d) => {
        setX(d)
        setY(false)
      })
      .catch(() => {
        setZ('Failed to fetch users')
        setY(false)
      })
  }, [])

  return (
    <div className="container">
      <h2>Users API</h2>

      {y && <p>Loading...</p>}
      {z && <p>{z}</p>}

      <div className="card-list">
        {x.map((i) => (
          <div className="card" key={i.id}>
            <h3>{i.name}</h3>
            <p><strong>Email:</strong> {i.email}</p>
            <p><strong>Phone:</strong> {i.phone}</p>
          </div>
        ))}
      </div>
    </div>
  )
}

export default UserList