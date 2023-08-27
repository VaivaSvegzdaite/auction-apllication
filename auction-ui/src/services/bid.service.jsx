import axios from "axios";
import authHeader from "./auth.header.jsx";

const API_URL = "http://localhost:8080/api/bid";

class BidService {

    create(body) {
        return axios.post(API_URL + '/', body, { headers: authHeader() });
    }

}

export default new BidService();
