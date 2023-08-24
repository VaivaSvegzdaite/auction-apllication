import { useState, useEffect } from "react";
import axios from "axios";
import {useParams} from "react-router-dom";
import CreateAuction from "./components/CreateAuction";
import {format} from "date-fns";

export default function NewAuction({currentUser}) {
    const {productId} = useParams();
    const [product, setProduct] = useState();
    const [ongoingAuction, setOngoingAuction] = useState();

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
                            {ongoingAuction && <h6 className="card-title">This product is already in auction</h6>}
                        </div>
                        <div className="col-md-4">
                            <div className="card-body">
                                <h5 className="card-title">{product.name}</h5>
                                <p className="card-text">{product.description}</p>
                                {ongoingAuction && (
                                    <div className="card">
                                        <div className="card-body">
                                        <h5 className="card-title text-center">Auction</h5>
                                        <p className="card-text"><strong>Type:</strong> {ongoingAuction.type}</p>

                                        <p className="card-text"><strong>Minimum price:</strong> {ongoingAuction.startingPrice} €</p>
                                        <p className="card-text"><strong>Start:</strong> {format(new Date(ongoingAuction.startTime), 'MMM dd, yyyy - hh:mm')}</p>
                                        <p className="card-text"><strong>End:</strong> {format(new Date(ongoingAuction.endTime), 'MMM dd, yyyy - hh:mm')}</p>
                                        </div>
                                    </div>   
                                )}
                            </div>
                        </div>
                        <div className="col-md-4">
                            <CreateAuction productId={productId} userId={currentUser.id} setOngoingAuction={setOngoingAuction}/>
                        </div>
                    </div>
                </div>  
            )}
        </div>
    )
}