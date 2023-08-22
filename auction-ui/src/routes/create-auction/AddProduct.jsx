import { useState } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import UploadPicWidget from "./components/UploadPicWidget";
import axios from "axios";

const productCategories = [
    "ANTIQUES",
    "ART",
    "BOOKS",
    "CLOTHING",
    "COINS",
    "ELECTRONICS",
    "GARDEN",
    "HOME_APPLIANCES",
    "JEWELRY",
    "MUSICAL_INSTRUMENTS",
    "VEHICLES"
]


export default function AddProduct({currentUser}) {

    const [ imgUrl, setImgUrl ] = useState();
    const [ product, setProduct ] = useState(() => ({
        name: '',
        description: '',
        startingPrice: 1,
        category: 'ANTIQUES'
    }))
    const [ isLoading, setIsLoading] = useState(false);

    const submitProduct = (e) => {
        e.preventDefault();

        const data = {
            ...product,
            url: imgUrl,
            user: {id: currentUser.id}
        }

        setIsLoading(true);
        axios.post('http://localhost:8080/api/product/', {
                data
            }).then(response => {
                response.json;
                setIsLoading(false);
            })
            .catch(error => {
                console.log(error);
                setIsLoading(false);
            })

        // fetch('http//:localhost:8080/api/product/', {
        //     method: "POST",
        //     headers: {
        //       "Content-Type": "application/json",
        //     },
        //     body: JSON.stringify(data)
        // }).then(response => {
        //     response.json;
        //     setIsLoading(false);
        // })
        // .catch(error => {
        //     console.log(error);
        //     setIsLoading(false);
        // })
        
    }
    

    return (
        <div className="container">
        <h3 className="pt-4">Add a product</h3>
        
        <div className="card card-container">
            <Form
                onSubmit={submitProduct}
            >
                <div className="form-group">
                    <label htmlFor="name">Product name</label>
                    <Input
                        type="text"
                        className="form-control"
                        name="name"
                        value={product.name}
                        onChange={(e) => {
                            setProduct(prev => ({... prev, name: e.target.value}))
                        }}
                        required
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="description">Description</label>
                    <Input
                        type="text"
                        className="form-control"
                        name="description"
                        value={product.description}
                        onChange={(e) => {
                            setProduct(prev => ({... prev, description: e.target.value}))
                        }}
                        required
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="starting-price">Starting price</label>
                    <Input
                        type="number"
                        className="form-control"
                        name="starting-price"
                        value={product.startingPrice}
                        onChange={(e) => {
                            setProduct(prev => ({... prev, startingPrice: Number(e.target.value)}))
                        }}
                        min={1}
                        required
                    />
                </div>

                
                <div className="form-group">
                    <label htmlFor="category">Category</label>
                    <select
                        className="form-select"
                        onChange={(e) => {
                            setProduct(prev => ({... prev, category: e.target.value}))
                        }}
                        value={product.category}
                        required
                    >
                        {productCategories.map(category => {
                            return <option value={category} key={category}>{category}</option>
                        })}
                    </select>
                </div>

                <div className="form-group">
                    <UploadPicWidget setImgUrl={setImgUrl}/>
                    {imgUrl && 
                        <div className="alert alert-success" role="alert">
                            Image uploaded successfully
                        </div>}
                </div>

                

                <div className="form-group">
                    <button
                        className="btn btn-dark btn-block"
                        disabled={isLoading}
                    >
                        {isLoading && (
                            <span className="spinner-border spinner-border-sm"></span>
                        )}
                        <span>Add product</span>
                    </button>
                </div>
            </Form>
        </div>
        </div>
)
}