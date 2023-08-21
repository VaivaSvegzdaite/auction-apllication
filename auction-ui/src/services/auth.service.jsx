import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/";

class AuthService {
    login(username, password) {
        debugger
        return axios
            .post(API_URL + "signin", {
                username,
                password
            })
            .then(response => {

                if (response.data.token) {
                    sessionStorage.setItem("user", JSON.stringify(response.data));
                }

                return response.data;
            });
    }

    logout() {
        sessionStorage.removeItem("user");
    }

    register(username, email, password) {
        return axios.post(API_URL + "signup", {
            username,
            email,
            password
        });
    }

    getCurrentUser() {
        return JSON.parse(sessionStorage.getItem('user'));
    }

    hasAdminRole(user) {
        return user && user.roles.includes("ROLE_ADMIN");
    }
}

export default new AuthService();
