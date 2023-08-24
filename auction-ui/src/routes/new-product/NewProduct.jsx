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
            {requestState.reqSent && addedProduct ? ( 
                <div className="card card-container mt-4">
                    <p>{requestState.resMessage}</p>
                    <Link 
                    className="btn btn-dark" 
                    to={`/new-auction/${addedProduct.id}`}
                    >
                        Next
                    </Link>
                </div>
            ) : ( 
                <>
                    <h5 className="pt-4">Create a product:</h5>
                    <AddProduct currentUser={currentUser} requestState={requestState} setRequestState={setRequestState} setAddedProduct={setAddedProduct}/>
                </>
            )
            }
            
        </div>
    )
}