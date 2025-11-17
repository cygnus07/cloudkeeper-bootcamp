import { Outlet, Link, useNavigate } from "react-router-dom"

const Layout = () => {
    const navigate = useNavigate()

    const handleLogout = () => {
        localStorage.removeItem('isLoggedIn')
        navigate('/login')
    }

    return (
        <>
            <nav className="layout-container">
                <div className="nav">
                    <Link to="/dashboard/counter">
                        Counter
                    </Link>
                    <Link to="/dashboard/digital-watch">
                        Digital Watch
                    </Link>
                    <button onClick={handleLogout}>
                        Logout
                    </button>
                </div>
            </nav>

            <div className="content">
                <Outlet />
            </div>
        </>
    )
}

export default Layout