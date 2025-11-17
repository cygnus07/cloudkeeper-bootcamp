import { useNavigate } from 'react-router-dom'
import { useEffect } from 'react'

const Login = () => {
    const navigate = useNavigate()
    const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true'

    useEffect(() => {
        if (isLoggedIn) {
            navigate('/dashboard', { replace: true })
        }
    }, [isLoggedIn])

    const handleLogin = () => {
        localStorage.setItem('isLoggedIn', 'true')
        navigate('/dashboard')
    }

    return (
        <div className="login-container">
            <h1>Login page</h1>
            <button onClick={handleLogin}>Login</button>
        </div>
    )
}

export default Login