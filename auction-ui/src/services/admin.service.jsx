import axios from "axios";
import authHeader from "./auth.header.jsx";

const API_URL = "http://localhost:8080/api/admin";

class AdminService {

    getAllUsers(page, size) {
        return axios.get(API_URL + `/users`, {headers: authHeader()})
    }

    deleteUser(id) {
        return axios.delete(API_URL + "/users/" + id, {headers: authHeader(),});
    }


}

export default new AdminService();
