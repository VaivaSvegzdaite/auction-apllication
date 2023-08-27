import React, {Component} from "react";

import AdminService from "../../../services/admin.service.jsx";
import swal from "sweetalert";
import DataTable from "./DataTable.jsx";

export class Users extends Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
        };
    }

    componentDidMount() {
        AdminService.getAllUsers().then(
            response => {
                this.setState({
                    content: response.data,
                    users: response.data,
                });
            },
            error => {
                this.setState({
                    content:
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString()
                });
            }
        );
    }

    handleDelete = async (user) => {
        const originalUsers = this.state.users;
        const users = originalUsers.filter((u) => u.id !== user.id);
        this.setState({users});

        swal({
            text: "Dp you really want to delete user?",
            buttons: ["No", "Yes"],
            dangerMode: true,
        }).then(actionConfirmed => {
            if (actionConfirmed) {
                AdminService.deleteUser(user.id).then(
                    (response) => {
                        +response.status < 400 &&
                        swal({
                            title: "User deleted!",
                            icon: "success",
                            buttons: {
                                confirm: {text: "Close", className: "sweet-confirm"},
                            },
                        }).then(function () {
                            window.location.reload();
                        });
                    },
                    (error) => {
                        this.setState({users: originalUsers});
                        +error.response.status > 400 &&
                        swal({
                            title: "Something went wrong...",
                            icon: "warning",
                            button: "Close",
                            dangerMode: true,
                        });
                    }
                );
            }
        })
    };


    render() {
        const {users} = this.state;

        return (
            <React.Fragment>
                <DataTable
                    users={users}
                    onDelete={this.handleDelete}
                />
            </React.Fragment>
        );
    }
}

export default Users;
