import React from "react";
import UsersComponent from "../admin/users/Users.component.jsx";
import Profile from "../profile/Profile.component.jsx";

export default function BoardAdmin() {

    return (
        <div className="row">
        <div className="card w-75">

            <div className="row">
                <div className="col-md-3">
                    <Profile/>
                </div>
                <div className="col-md-9">
                    <h4 className="text-center">Users</h4>
                    <UsersComponent/>
             </div>
            </div>
        </div>
        </div>
    );
}
