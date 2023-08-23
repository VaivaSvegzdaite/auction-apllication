import React, {Component} from "react";
import {Navigate} from "react-router-dom";
import AuthService from "../../services/auth.service";

export default class Profile extends Component {
    constructor(props) {
        super(props);

        this.state = {
            redirect: null,
            userReady: false,
            currentUser: {username: ""}
        };
    }

    componentDidMount() {
        const currentUser = AuthService.getCurrentUser();

        if (!currentUser) this.setState({redirect: "/home"});
        this.setState({currentUser: currentUser, userReady: true})
    }

    render() {
        if (this.state.redirect) {
            return <Navigate to={this.state.redirect}/>
        }

        const {currentUser} = this.state;

        return (

            <div className="row">
            <div className="card">
                {(this.state.userReady) ?
                    <div>
                            <h5>
                               My Account
                            </h5>
                        <p>
                            <strong>Id:</strong>{" "}
                            {currentUser.id}
                        </p>
                        <p>
                            <strong>Username:</strong>{" "}
                            {currentUser.username}
                        </p>
                        <p>
                            <strong>Email:</strong>{" "}
                            {currentUser.email}
                        </p>
                        <p>
                            <strong>Password:</strong>{" "}
                            <button className="btn-outline-success">Change</button>
                        </p>
                    </div> : null}
            </div>
            </div>
        );
    }
}
