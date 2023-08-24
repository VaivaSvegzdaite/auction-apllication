import { useState, useEffect } from "react";
import ProductCard from "./components/ProductCard";
import AddProduct from "../new-product/components/AddProduct";
import { Link } from "react-router-dom";
import axios from "axios";

export default function MyProducts({currentUser}) {
    const [products, setProducts] = useState();
    const userId = currentUser.id;

    useEffect(() => {
        axios.get(
            `http://localhost:8080/api/product/user/${userId}`
            ).then(response => {
                setProducts(response.data)
            })
            .catch(err => {
                console.log(err.response.data);
            }) 
    }, [])

    return (
        <div className="container">
            <div className="row">
                <div className="col-8">
                    <h4 className="pt-4">List of saved products to auction</h4>
                    
                    {products && products.map(product => {
                        return <ProductCard product={product} key={product.id}/>
                    })}
                </div>
                <div className="col-4 mt-5">
                    <Link 
                        className="btn btn-dark" 
                        to="/new-product"
                    >
                        Add a new product
                    </Link>
                </div>
            </div>
        </div>
    )
}