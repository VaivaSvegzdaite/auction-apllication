import { useState, useEffect } from "react";
import axios from "axios";
import {useParams} from "react-router-dom";
import CreateAuction from "./components/CreateAuction";
import {format} from "date-fns";
import { Link } from "react-router-dom";

export default function NewAuction({currentUser}) {
    const {productId} = useParams();
    const [product, setProduct] = useState();
    const [activeAuction, setActiveAuction] = useState();
    const [isFormOpen, setIsFormOpen] = useState(false);
    const [ requestState, setRequestState] = useState({
        reqSent: false,
        isError: false,
        resMessage: ''
    });

    useEffect(() => {
        axios.get(
            `http://localhost:8080/api/product/${productId}`
            ).then(response => {
                setProduct(response.data)
            })
            .catch(err => {
                console.log(err.response.data);
            }) 

        axios.get(
            `http://localhost:8080/api/auction/product/${productId}`
            ).then(response => {
                setActiveAuction(response.data[0])
            })
            .catch(err => {
                console.log(err);
            }) 
    }, [])

    return (        
        <div className="container">
            <h5 className="pt-4">Create or update an auction for the following product:</h5>
           {product && (
                <div className="card mt-4">
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
                            { activeAuction && !isFormOpen &&
                                <>
                                    <h6 className="card-title pt-3 pl-3">This product is already in auction:</h6>
                                    <div className="card-body">
                                        <p className="card-text"><strong>Auction type:</strong> {activeAuction.type}</p>
                                        <p className="card-text"><strong>Minimum price:</strong> {activeAuction.startingPrice} €</p>
                                        <p className="card-text"><strong>Start:</strong> {format(new Date(activeAuction.startTime), 'EEE, dd MMMM yyyy - HH:mm')}</p>
                                        <p className="card-text"><strong>End:</strong> {format(new Date(activeAuction.endTime), 'EEE, dd MMMM yyyy - HH:mm')}</p>
                                    </div> 
                                    <button 
                                        className="btn btn-dark btn-block" 
                                        type="button" 
                                        onClick={() => setIsFormOpen(!isFormOpen)}
                                    >
                                        {activeAuction ? "Update auction" : "Create new auction"}
                                    </button>
                                    {requestState.reqSent && (
                                        <div className="form-group">
                                            <div className={requestState.isError ? "alert alert-danger" : "alert alert-success"} role="alert">
                                                {requestState.resMessage}
                                            </div>
                                        </div>
                                    )}
                                </>
                            }
                            { isFormOpen && 
                                <CreateAuction  
                                    productId={productId} 
                                    userId={currentUser.id} 
                                    activeAuction={activeAuction} 
                                    setActiveAuction={setActiveAuction} 
                                    variant={activeAuction ? "UPDATE" : "CREATE"}
                                    setIsFormOpen={setIsFormOpen}
                                    setRequestState={setRequestState}
                                />
                            }
                        </div>
                    </div>
                </div>  
            )}
            <Link to="/my-products" className="link text-dark">Back to my products</Link>
        </div>
    )
}