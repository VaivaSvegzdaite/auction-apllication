import React from 'react';
import AuctionBid from './AuctionBid.component.jsx';

const AuctionDetails = ({auction}) => {

    return (
        <div className="row">
            <AuctionBid auction={auction}/>
        </div>
    );
}
export default AuctionDetails;
