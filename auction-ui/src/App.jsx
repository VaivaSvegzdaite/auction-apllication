import { Component } from "react";
import { Routes, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AuthService from "./services/auth.service";

import Login from "./routes/login-signup/Login.component";
import Signup from "./routes/login-signup/Signup.component";
import Home from "./routes/home/Home.component.jsx";
import Profile from "./routes/profile/Profile.component";
import BoardUser from "./routes/boards/Board-user.component.jsx";
import BoardAdmin from "./routes/boards/Board-admin.component.jsx";
import Footer from "./components/Footer.jsx";
import MyProducts from "./routes/my-products/MyProducts";
import NewProduct from "./routes/new-product/NewProduct";
import NewAuction from "./routes/new-auction/NewAuction";

class App extends Component {
    constructor(props) {
        super(props);
        this.logOut = this.logOut.bind(this);

        this.state = {
            showAdminBoard: false,
            currentUser: undefined,
        };
    }

    componentDidMount() {
        const user = AuthService.getCurrentUser();

        if (user) {
            this.setState({
                currentUser: user,
                showAdminBoard: AuthService.hasAdminRole(user),
            });
        }
    }

    logOut() {
        AuthService.logout();
        this.setState({
            showAdminBoard: false,
            currentUser: undefined,
        });
    }

    render() {
        const { currentUser, showAdminBoard } = this.state;

        return (
            <div className="min-vh-100">
                <nav className="navbar navbar-expand navbar-light text-dark">
                    <Link to={"/"} className="navbar-brand">
                        AuctionApp
                    </Link>
                    <div className="navbar-nav mr-auto">
                        {showAdminBoard && (
                            <li className="nav-item">
                                <Link to={"/admin"} className="nav-link">
                                    Admin Board
                                </Link>
                            </li>
                        )}

                        {!showAdminBoard && currentUser && (
                            <li className="nav-item">
                                <Link to={"/auctions"} className="nav-link">
                                    All Auctions
                                </Link>
                            </li>
                        )}
                    </div>

                    {currentUser ? (
                        <div className="navbar-nav ml-auto">
                            <li className="nav-item">
                                <Link to={"/my-products"} className="nav-link">
                                    My Products
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link to={"/profile"} className="nav-link">
                                    {currentUser.username}
                                </Link>
                            </li>
                            <li className="nav-item">
                                <a href="/login" className="nav-link" onClick={this.logOut}>
                                    LogOut
                                </a>
                            </li>
                        </div>
                    ) : (
                        <div className="navbar-nav ml-auto">
                            <li className="nav-item">
                                <Link to={"/login"} className="nav-link">
                                    Login
                                </Link>
                            </li>

                            <li className="nav-item">
                                <Link to={"/signup"} className="nav-link">
                                    Sign Up
                                </Link>
                            </li>
                        </div>
                    )}
                </nav>

                <div className="min-vh-100 bg-warning">
                    <Routes>
                        <Route path="/" element={<Signup />} />
                        <Route path="/login" element={<Login />} />
                        <Route path="/signup" element={<Signup />} />
                        <Route path="/profile" element={<Profile />} />
                        <Route path="/my-products" element={<MyProducts currentUser={currentUser}/>} />
                        <Route path="/new-product" element={<NewProduct currentUser={currentUser}/>} />
                        <Route path="/new-auction/:productId" element={<NewAuction currentUser={currentUser}/>} />
                        <Route path="/auctions" element={<BoardUser />} />
                        <Route path="/admin" element={<BoardAdmin />} />
                    </Routes>
                </div>
                <Footer/>
            </div>
        );
    }
}

export default App;
