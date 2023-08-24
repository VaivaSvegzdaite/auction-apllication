import { useState, useEffect } from "react";
import axios from "axios";
import {useParams} from "react-router-dom";
import CreateAuction from "./components/CreateAuction";

export default function NewAuction({currentUser}) {
    const {productId} = useParams();
    const [product, setProduct] = useState();

    useEffect(() => {
        axios.get(
            `http://localhost:8080/api/product/${productId}`
            ).then(response => {
                setProduct(response.data)
            })
            .catch(err => {
                console.log(err.response.data);
            }) 
    }, [])

    return (        
        <div className="container">
            <h4 className="pt-4">Create an auction for:</h4>
           {product && (
                <div className="card">
                    <div className="row">
                        <div className="col-md-4">
                            <img src={product.url} className="card-img" alt=""/>
                        </div>
                        <div className="col-md-4">
                            <div className="card-body">
                                <h5 className="card-title">{product.name}</h5>
                                <p className="card-text">{product.description}</p>
                            </div>
                        </div>
                        <div className="col-md-4">
                            <CreateAuction productId={productId} userId={currentUser.id}/>
                        </div>
                    </div>
                </div>  
            )}
        </div>
    )
}