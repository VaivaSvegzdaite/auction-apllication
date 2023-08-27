import { useState, useEffect } from "react";
import ProductCard from "../my-products/components/ProductCard";
import { Link } from "react-router-dom";
import axios from "axios";
import authHeader from "../../services/auth.header.jsx";
import MyProducts from "../my-products/MyProducts";

export default function MyAuctions({currentUser}) {

    const [myAuctions, setMyAuctions] = useState();
    const userId = currentUser?.id;

    useEffect(() => {
        if (!userId) {
            return;
        }

        axios.get(
            `http://localhost:8080/api/auction/user/${userId}`, {headers: authHeader()}
            ).then(response => {
                setMyAuctions(response.data)
            })
            .catch(err => {
                console.log(err.response.data);
            })

    }, [userId])

    return (
        <div className="container">
            <div className="row">
                <div className="col-9">
                    <h4 className="pt-4">My auctions</h4>                   
                    { myAuctions ?
                        myAuctions.map(auction => {
                            return <ProductCard product={auction.product} variant={"AUCTION"} auctionId={auction.id} key={auction.id} />
                        }) :
                        <div className="card card-container mt-4">
                            <p>No auctions yet</p>
                            <Link 
                            className="btn btn-dark" 
                            to={`/my-products`}
                            >
                                Create an auction
                            </Link>
                        </div>
                    }
                </div>
                <div className="col-3">
                    <div className="card-body mt-5 border rounded border-dark">
                        <p>New product to auction? Go to my products and create a new product for auction.</p> 
                        <Link 
                            className="btn btn-dark" 
                            to="/my-products"
                        >
                            My products
                        </Link>
                    </div>
                    
                </div>
            </div>
        </div>
    )
}
