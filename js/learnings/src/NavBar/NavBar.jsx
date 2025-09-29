import { Link } from "react-router-dom/cjs/react-router-dom.min";
import { ReactComponent as Logo } from "../assets/Watch Movie Icon.svg"
import "./NavBar.css"



function NavBar() {
    return (
        <nav>
            <div className="navbar">
                <Link className="navbar-item" to="/"><Logo className="navbar-logo" fill="#FF0000" /></Link>

                <Link className="navbar-item" to="/">Movies</Link>
                <Link className="navbar-item" to="/watchlist">Watchlist</Link>
            </div>

        </nav >
    );
}

export default NavBar;