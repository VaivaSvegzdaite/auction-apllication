import axios from "axios";
import authHeader from "./auth.header.jsx";

const API_URL = "http://localhost:8080/api/auction";

class AuctionService {

    getAllAuctions() {
        return axios.get(API_URL + `/`, {headers: authHeader()})
    }

    getAuctionById(id) {
        return axios.get(API_URL + `/` + id,{headers: authHeader()})
    }

}

export default new AuctionService();
