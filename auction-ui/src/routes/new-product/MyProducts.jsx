import { useState, useEffect } from "react";
import ProductCard from "./components/ProductCard";
import AddProduct from "./components/AddProduct";
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
                    <h4 className="pt-4">Choose a saved product to auction</h4>
                    <>List of products to choose for auction</>
                    {products && products.map(product => {
                        return <ProductCard product={product} key={product.id}/>
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