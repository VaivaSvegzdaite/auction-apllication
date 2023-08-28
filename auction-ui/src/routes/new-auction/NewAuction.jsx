import { useState, useEffect } from "react";
import axios from "axios";
import {useParams} from "react-router-dom";
import CreateAuction from "./components/CreateAuction";
import {format, isAfter} from "date-fns";
import authHeader from "../../services/auth.header";
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

    const hasEnded = activeAuction ? isAfter(new Date(), new Date(activeAuction.endTime)) : "";

    useEffect(() => {
        axios.get(
            `http://localhost:8080/api/product/${productId}`, {
                headers: authHeader()
            }).then(response => {
                setProduct(response.data)
            })
            .catch(err => {
                console.log(err.response.data);
            }) 

        axios.get(
            `http://localhost:8080/api/auction/product/${productId}`, {
                headers: authHeader()
            }).then(response => {
                setActiveAuction(response.data)
            })
            .catch(err => {
                if (err.response.status == 404) {
                    return;
                } else {
                    console.log(err);
                }
            }) 
    }, [])

    return (        
        <div className="container">
            <h5 className="pt-4">Auction for the following product:</h5>
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
                                    <div className="card-body">
                                        <p className="card-text"><strong>Auction type:</strong> {activeAuction.type}</p>
                                        <p className="card-text"><strong>Minimum price:</strong> {activeAuction.startingPrice} €</p>
                                        <p className="card-text"><strong>Start:</strong> {format(new Date(activeAuction.startTime), 'EEE, dd MMMM yyyy - HH:mm')}</p>
                                        <p className="card-text"><strong>End:</strong> {format(new Date(activeAuction.endTime), 'EEE, dd MMMM yyyy - HH:mm')}</p>
                                    </div> 
                                    {requestState.reqSent && (
                                        <div className="form-group">
                                            <div className={requestState.isError ? "alert alert-danger" : "alert alert-success"} role="alert">
                                                {requestState.resMessage}
                                            </div>
                                        </div>
                                    )}
                                </>
                            }
                            {hasEnded ? (
                                <p className="ended-message text-danger font-weight-bold pl-4">Auction Ended</p>
                            ) : (
                            <button 
                                className="btn btn-dark btn-block" 
                                type="button" 
                                onClick={() => setIsFormOpen(!isFormOpen)}
                            >
                                {!activeAuction && !isFormOpen && "Create new auction"}
                                {(activeAuction && !isFormOpen)  && "Update auction"}
                                {activeAuction && isFormOpen && "Close form"}
                            </button>
                            )}
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
                    <div className="row mt-4">
                        <div className="col-md-6">
                        {
                            activeAuction && activeAuction.bids && (<>
                            <h5 className="card-title">Bids</h5>
                            <table className="table table-striped">
                                <thead>
                                    <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">username</th>
                                    <th scope="col">bid</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        activeAuction.bids &&
                                        activeAuction.bids.map((bid, index) => {
                                            return (
                                                <tr>
                                                <th scope="row">{index+1}</th>
                                                <td>{bid.user.username}</td>
                                                <td>{bid.price}</td>
                                                </tr>
                                            )
                                        })
                                    }
                                </tbody>
                            </table>
                            </>)}
                        </div>
                    </div>                
                </div>  
            )}
            <Link to="/my-products" className="link text-dark">Back to my products</Link> 
        </div>
    )
}
