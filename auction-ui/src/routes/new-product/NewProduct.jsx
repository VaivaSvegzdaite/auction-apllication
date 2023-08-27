import AddProduct from "./components/AddProduct";
import { useState } from "react";
import { Link } from "react-router-dom";

export default function NewProduct({currentUser}) {
    const [ addedProduct, setAddedProduct] = useState();
    const [ requestState, setRequestState] = useState({
        reqSent: false,
        isError: false,
        resMessage: ''
    });

    return (
        <div className="container">
            <div className="row justify-content-center">
            <div className="col-10">
                <div className="row justify-content-center">
                    <div className="col-md-10">
                    <h5 className="pt-4 pl-4 ml-4">Create a product:</h5>
                    </div>
                </div>
                <div className="row justify-content-center">
                    <div className="col-md-6">
                        
                        <AddProduct currentUser={currentUser} requestState={requestState} setRequestState={setRequestState} setAddedProduct={setAddedProduct}/>
                    </div>
                    <div className="col-md-4">
                        { requestState.reqSent && addedProduct && 
                            <div className="card card-container mt-4">
                                <p>{requestState.resMessage}</p>
                                <Link 
                                className="btn btn-dark" 
                                to={`/new-auction/${addedProduct.id}`}
                                >
                                    Next
                                </Link>
                            </div>
                        }
                    </div>  
                </div>
                </div>
            </div>
        </div>
    )
}