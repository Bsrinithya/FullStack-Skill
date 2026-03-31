import axios from 'axios'
import { useEffect, useState } from 'react'
import './Navbar.css'

function FakePostList() {
  const [x, setX] = useState([])
  const [y, setY] = useState(true)
  const [z, setZ] = useState('')
  const [a, setA] = useState('')

  const b = () => {
    setY(true)
    setZ('')

    axios.get('https://dummyjson.com/posts')
      .then((r) => {
        setX(r.data.posts)
        setY(false)
      })
      .catch(() => {
        setZ('Failed to fetch posts')
        setY(false)
      })
  }

  useEffect(() => {
    b()
  }, [])

  const c = x.filter((i) =>
    i.title.toLowerCase().includes(a.toLowerCase()) ||
    i.body.toLowerCase().includes(a.toLowerCase())
  )

  return (
    <div className="container">
      <h2>Fake API Posts</h2>

      <div className="search-row">
        <input
          type="text"
          placeholder="Search title or body"
          value={a}
          onChange={(e) => setA(e.target.value)}
          className="search-box"
        />

        <button onClick={b}>Refresh</button>
      </div>

      {y && <p>Loading...</p>}
      {z && <p>{z}</p>}

      <div className="card-list">
        {c.map((i) => (
          <div className="card" key={i.id}>
            <h3>{i.title}</h3>
            <p>{i.body}</p>
          </div>
        ))}
      </div>
    </div>
  )
}

export default FakePostList