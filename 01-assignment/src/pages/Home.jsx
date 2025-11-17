import { Link } from 'react-router-dom'

const Home = () => {
  return (
    <div className="home-container">
        <h1>Welcome to Home Page</h1>
        <h3>This is a public page, accessible by all</h3>
        <Link to="/login">
          <button>Login</button>
        </Link>
    </div>
  )
}

export default Home