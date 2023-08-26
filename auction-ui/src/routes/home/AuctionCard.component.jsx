import React from 'react';
import {Card, ListGroup} from 'react-bootstrap';
import {format, isAfter} from "date-fns";
import { Link } from "react-router-dom";

const AuctionCardComponent = ({ auction }) => {
    const {id: auctionId, startTime, endTime, startingPrice, user, type, product, bids} = auction;
    const {name, description, category, userId, url} = product;
    const {id: auctionUserId, username, email, password, roles} = user;

    const formattedStartTime = format(new Date(startTime), 'EEE, dd MMMM yyyy - HH:mm');
    const formattedEndTime = format(new Date(endTime), 'EEE, dd MMMM yyyy - HH:mm');
    // Check if the auction has ended
    const hasEnded = isAfter(new Date(), new Date(auction.endTime));

    return (
        <div className={`card my-4  ${hasEnded ? 'ended' : ""}`}>
            <div className="row g-0">
                <div className="col-md-4">
                    <img src={url} className="card-img" alt=""/>
                </div>
                <div className="col-md-8">
                    <div className="row">
                        <div className="col-md-12 mb-3">
                            <h5 className="card-title mb-0">{auction.product.name}</h5> 
                            <p className="card-text">by {username}</p>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-6">
                            <h5 className="card-text"><strong>{startingPrice} €</strong></h5>
                            <p className="card-text">{bids.length > 0 ? bids.length : 0} bids</p>
                            <Link to="" className="btn btn-dark">View</Link> 
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
