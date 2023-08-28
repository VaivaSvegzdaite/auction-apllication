import React, { useState } from 'react';
import { Form, Button } from 'react-bootstrap';
import {isAfter} from 'date-fns';
import BidService from "../../services/bid.service.jsx";

const AuctionBid = ({auction, setAuction, lastPrice, currentUser}) => {
    const [ bidAmount, setBidAmount ] = useState('');
    const [ biddingError, setBiddingError ] = useState('');
    const [ isLoading, setIsLoading ] = useState(false);
    const [ requestState, setRequestState ] = useState({
        reqSent: false,
        isError: false,
        resMessage: ''
    });


    const handleBidChange = (event) => {
        if (event.target.value <= lastPrice) {
            setBiddingError('Bid amount must be greater than the last price.');
        } else {
            setBiddingError('')
        }
        setBidAmount(event.target.value);
    };

    const handleBidSubmit = (event) => {
        event.preventDefault();

        const currentTime = new Date();
        if (isAfter(currentTime, new Date(auction.endTime))) {
            setBiddingError('Auction has ended. Bidding is no longer allowed.');
            return;
        }

        const data = {
            price: bidAmount,
            productId: auction.product.id,
            userId: currentUser.id,
            auctionId: auction.id
        };

        setIsLoading(true);

        BidService.create(data).then(response => {
            setIsLoading(false);
            const updatedBids = [...auction.bids, response.data];
            const updatedAuction = {
                ...auction,
                startingPrice: data.price,
                bids: updatedBids
            };
            setAuction(updatedAuction)
            setRequestState({reqSent: true, isError: false, resMessage: "Bid successfull!" });
            setBidAmount('');

        }).catch(err => {
            console.log(err)
            setIsLoading(false);
            setRequestState({reqSent: true, isError: true, resMessage: "Network error, try again later"});
        });
        
    };

    return (
        <Form onSubmit={handleBidSubmit}>
            <Form.Group>
                <Form.Control
                    type="number"
                    min={1}
                    placeholder="Enter your bid"
                    value={bidAmount}
                    onChange={handleBidChange}
                    required
                />
            </Form.Group>
            {biddingError  &&
                <div className="form-group">
                    <div className="alert alert-danger mt-2" role="alert">
                        {biddingError}
                    </div>
                </div>
            }
            <Button variant="dark" type="submit" className="mt-2" disabled={isLoading} >
                {isLoading && (
                    <span className="spinner-border spinner-border-sm"></span>
                )}
                Place Bid
            </Button>
            {requestState.reqSent && (
                <div className="form-group">
                    <div className={requestState.isError ? "alert alert-danger" : "alert alert-success"} role="alert">
                        {requestState.resMessage}
                    </div>
                </div>
            )}
        </Form>
    );
};

export default AuctionBid;


