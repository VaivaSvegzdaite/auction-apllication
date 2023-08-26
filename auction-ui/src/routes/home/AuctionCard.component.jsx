import React from 'react';
import {Card, ListGroup} from 'react-bootstrap';
import {format, isAfter} from "date-fns";
import { Link } from "react-router-dom";

const AuctionCardComponent = ({ auction }) => {

    const formattedStartTime = format(new Date(auction.startTime), 'EEE, dd MMMM yyyy - HH:mm');
    const formattedEndTime = format(new Date(auction.endTime), 'EEE, dd MMMM yyyy - HH:mm');

    const hasEnded = isAfter(new Date(), new Date(auction.endTime));

    return (
        <div className={`card my-4  ${hasEnded ? 'ended' : ""}`}>
            <div className="row g-0">
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
                            <h5 className="card-text"><strong>{auction.startingPrice} €</strong></h5>
                            <p className="card-text">{auction.bids.length > 0 ? auction.bids.length : 0} bids</p>
                            {hasEnded ? (
                                <p className="ended-message text-danger font-weight-bold">Auction Ended</p>
                            ) : (
                                <Link to={`/auctions/details/${auction.id}`} className="btn btn-dark">View</Link>
                            )}
                            
                        </div>
                        <div className="col-md-6">
                            <p className="card-text">Starts: {formattedStartTime}</p>
                            <p className="card-text">Ends: {formattedEndTime}</p>
                        </div>        
                    </div>
                </div>
            </div>
        </div>  
    );
}

export default AuctionCardComponent
