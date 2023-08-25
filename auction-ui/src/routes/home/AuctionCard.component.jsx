import React from 'react';
import {Card, ListGroup} from 'react-bootstrap';
import {format} from "date-fns";
import { Link } from "react-router-dom";

const AuctionCardComponent = ({ auction }) => {
    const {id: auctionId, startTime, endTime, startingPrice, user, type, product, bids} = auction;
    const {name, description, category, userId, url} = product;
    const {id: auctionUserId, username, email, password, roles} = user;

    const formattedStartTime = format(new Date(startTime), 'MMM dd, yyyy hh:mm a');
    const formattedEndTime = format(new Date(endTime), 'MMM dd, yyyy hh:mm a');


    return (
        <div className="card pb-2 my-4">
            <div className="row g-0">
                <div className="col-md-4">
                    <img src={url} className="card-img" alt=""/>
                </div>
                <div className="col-md-4">
                    <div className="card-body">
                        <h5 className="card-title">{auction.product.name}</h5>
                        <p className="card-text">{description}</p>
                       
                    </div>
                    <div className="card-body">
                        <Link to="" className="btn btn-dark">View</Link>
                    </div>             
                </div>
                <div className="col-md-4">
                    <ul className="list-group list-group-flush">   
                        <li className="list-group-item">Starts: {formattedStartTime}</li>
                        <li className="list-group-item">Ends: {formattedEndTime}</li>
                        <li className="list-group-item">Price: <strong>{startingPrice} €</strong></li>
                        <li className="list-group-item">Bids: {bids.length > 0 ? bids.length : 0}</li>
                    </ul>
                </div>
            </div>
        </div>  
    );
}

export default AuctionCardComponent
