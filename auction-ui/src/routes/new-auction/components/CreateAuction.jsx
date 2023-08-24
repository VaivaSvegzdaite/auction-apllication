import { useState, useEffect } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import axios from "axios";

const auctionTypes = [
    "STANDARD",
    "RESERVE"
]

export default function CreateAuction({
    productId, 
    userId, 
    activeAuction, 
    setActiveAuction, 
    variant, 
    setIsFormOpen, 
    setRequestState}) {

    const [ auction, setAuction ] = useState(() => ({
        type: "STANDARD",
        startTime: "yyyy-MM-ddThh:mm",
        endTime: "yyyy-MM-ddThh:mm",
        startingPrice: 1,
        product: {id: productId},
        user: {id: userId}
    }))
    const [ isTimeError, setIsTimeError ] = useState(false);
    const [ isLoading, setIsLoading] = useState(false);

    const checkValidPeriod = (start, end) => {
        const startTime = new Date(start);
        const endTime = new Date(end);
        return startTime < endTime;
    }
 
    const today = () => { 
        const d = new Date().toISOString()
        return d.substring(0, 16)
    }

    useEffect(() => {
        if (variant === "UPDATE") {
            setAuction({...activeAuction})
        }
    }, [])
    
    const submitAuction = (e) => {
        e.preventDefault();

        const data = {...auction}

        setIsLoading(true);
        if (variant === "CREATE") {
            axios.post(
            'http://localhost:8080/api/auction/', 
                data
            ).then(response => {
                setIsLoading(false);
                setActiveAuction(response.data);
                setRequestState({reqSent: true, isError: false, resMessage: "Auction successfully created" });
                setAuction({
                    type: "STANDARD",
                    startTime: "yyyy-MM-ddThh:mm",
                    endTime: "yyyy-MM-ddThh:mm",
                    startingPrice: 1,
                    product: {id: productId},
                    user: {id: userId}
                })
            })
            .catch(err => {
                console.log(err);
                setIsLoading(false);
                setRequestState({reqSent: true, isError: true, resMessage: "Network error, try again later"});
            }) 
        } else if (variant === "UPDATE") {
            axios.put(
                `http://localhost:8080/api/auction/${activeAuction.id}`, 
                    data
                ).then(response => {
                    setIsLoading(false);
                    setActiveAuction(response.data);
                    setRequestState({reqSent: true, isError: false, resMessage: "Auction successfully updated" });
                })
                .catch(err => {
                    console.log(err);
                    setIsLoading(false);
                    setRequestState({reqSent: true, isError: true, resMessage: "Network error, try again later"});
                })
        }
        setIsLoading(false);
        setIsFormOpen(false);
    }

    return (
        <Form
            onSubmit={submitAuction}
        >
            <div className="form-group">
                <label htmlFor="auction-type">Choose auction type:</label>
                <div className="form-check">
                    <Input 
                        className="form-check-input" type="radio" name="type" id="STANDARD"
                        value={"STANDARD" || ""}
                        onChange={(e) => {
                            setAuction(prev => ({... prev, type: e.target.value}))
                        }}
                        checked={auction.type === 'STANDARD'}
                    />
                    <label className="form-check-label" htmlFor="STANDARD">
                        Standard
                    </label>
                </div>
                <div className="form-check">
                    <Input 
                        className="form-check-input" type="radio" name="type" id="RESERVE" 
                        value={"RESERVE" || ""} 
                        onChange={(e) => {
                            setAuction(prev => ({... prev, type: e.target.value}))
                        }}
                        checked={auction.type === 'RESERVE'}
                    />
                    <label className="form-check-label" htmlFor="RESERVE">
                        Reserve
                </label>
                </div>
            </div>

            <div className="form-group">
                <label htmlFor="price">Starting price €</label>
                <Input
                    type="number"
                    className="form-control"
                    name="price"
                    min={1}
                    placeholder="0.00 €"
                    value={auction.startingPrice}
                    onChange={(e) => {
                        setAuction(prev => ({... prev, startingPrice: Number(e.target.value)}))
                    }}  
                    required
                />
            </div>

            <div className="form-group">
                <label htmlFor="start-time">Start date and time</label>
                <Input
                    type="datetime-local"
                    className="form-control"
                    name="start-time"
                    min={today()}
                    value={auction.startTime}
                    onChange={(e) => {
                        setAuction(prev => ({... prev, startTime: e.target.value}))
                    }}  
                    required
                />
            </div>

            <div className="form-group">
                <label htmlFor="end-time">End date and time</label>
                <Input
                    type="datetime-local"
                    className="form-control"
                    name="end-time"
                    min={today()}
                    value={auction.endTime}
                    onChange={(e) => {
                        setAuction(prev => ({... prev, endTime: e.target.value}))
                        if(!checkValidPeriod(auction.startTime, e.target.value)) {
                            setIsTimeError(true);
                        } else { 
                            setIsTimeError(false);
                         }
                    }}  
                    required
                />
                {isTimeError && (
                        <div className="alert alert-danger mt-1 text-small" role="alert">
                            End time cannot be before start time.
                        </div>
                    )}
            </div>


            <div className="form-group">
                <button
                    type="submit"
                    className="btn btn-dark btn-block"
                    disabled={isLoading}
                >
                    {isLoading && (
                        <span className="spinner-border spinner-border-sm"></span>
                    )}
                    {variant === "CREATE" ? "Create auction" : "Update auction"}
                </button>
                
            </div>
        </Form>
    )
}