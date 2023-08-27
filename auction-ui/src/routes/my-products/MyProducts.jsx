import { useState, useEffect } from "react";
import ProductCard from "./components/ProductCard";
import { Link } from "react-router-dom";
import axios from "axios";
import authHeader from "../../services/auth.header.jsx";

export default function MyProducts({currentUser}) {

    const [products, setProducts] = useState();
    const userId = currentUser.id;

    useEffect(() => {
        axios.get(
            `http://localhost:8080/api/product/user/${userId}`, {headers: authHeader()}
            ).then(response => {
                setProducts(response.data)
            })
            .catch(err => {
                console.log(err.response.data);
            }) 
    }, [])

    return (
        <div className="container">
            <div className="row justify-content-center">
                <div className="col-10">
                    <div className="row">
                        <div className="col-9">
                            <h4 className="pt-4">List of saved products to auction</h4> 
                        </div>
                        <div className="col-3">
                            <Link 
                                className="btn btn-dark mt-4" 
                                to="/new-product"
                            >
                                Add a new product
                            </Link>
                        </div>
                    </div>
                    <div className="row">
                        {products && products.map(product => {
                            return <ProductCard product={product} variant={"PRODUCT"} key={product.id} />
                        })}
                    </div>
                </div>
            </div>
        </div>
    )
}
