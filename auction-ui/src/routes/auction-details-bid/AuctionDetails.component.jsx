import React from 'react';
import {format} from 'date-fns';
import AuctionBid from './AuctionBid.component.jsx';
import { Link } from "react-router-dom";

const AuctionDetails = ({auction, setAuction}) => {
    
    const formattedStartTime = format(new Date(auction.startTime), 'EEE, dd MMMM yyyy - HH:mm');
    const formattedEndTime = format(new Date(auction.endTime), 'EEE, dd MMMM yyyy - HH:mm');
    const getHighestBid = () => {
        let max = auction.startingPrice;
        auction.bids?.forEach(bid => {
            if (bid.price > max) {
                max = bid.price;
            } 
        })
        return max;
    }
    const lastPrice = auction.bids.length < 0 ? auction.startingPrice : getHighestBid()

    return (
        <div className="row">
            <div className="container">
            <div className="card mt-4">
                <div className="row">
                    <div className="col-md-4">
                        <img src={auction.product.url} className="card-img" alt=""/>
                    </div>
                    <div className="col-md-8">
                        <div className="row">
                            <div className="col-md-12 mb-3">
                                <h5 className="card-title mb-0">{auction.product.name}</h5> 
                                <p className="card-text">by {auction.user.username}</p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-6">
                                <p className="card-text">{auction.product.description}</p> 
                                <p className="card-text"><strong>Start:</strong> {formattedStartTime}</p>
                                <p className="card-text"><strong>End:</strong> {formattedEndTime}</p> 
                                <p className="card-text"><strong>Bids:</strong> {auction.bids.length > 0 ? auction.bids.length : 0}</p>
                            </div>
                            <div className="col-md-6">
                                <div className="row justify-content-left pl-2">
                                    <div className="col-md-4">
                                        <p className="card-text"><strong>Last price:</strong></p>
                                    </div>
                                    <div className="col-md-4">
                                        <h5 className="card-text"><strong>{lastPrice} €</strong></h5>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="card-body">
                                        <AuctionBid auction={auction} setAuction={setAuction} lastPrice={lastPrice}/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>  
        <Link to="/auctions" className="link text-dark">Back to auctions</Link>
    </div>
        </div>
    );
}
export default AuctionDetails;
