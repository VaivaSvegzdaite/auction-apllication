import { useState, useEffect } from "react";
import ProductCard from "./components/product/ProductCard";
import AddProduct from "./components/product/AddProduct";
import axios from "axios";

export default function CreateAuctionProduct({currentUser}) {
    const [products, setProducts] = useState();

    useEffect(() => {
        axios.get(
            'http://localhost:8080/api/product/'
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
                    <h4 className="pt-4">Choose a saved product to auction</h4>
                    <>List of products to choose for auction</>
                    {products && products.map(product => {
                        return <ProductCard product={product}/>
                    })}
                </div>
                <div className="col">
                    <h4 className="pt-4 ">Add a new product</h4>
                    <AddProduct currentUser={currentUser} setProducts={setProducts}/>
                </div>
            </div>
        </div>
    )
}