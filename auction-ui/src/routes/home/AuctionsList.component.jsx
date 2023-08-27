import React from 'react';
import { Container } from 'react-bootstrap';
import AuctionCard from "./AuctionCard.component.jsx"

const AuctionList = ({auctions}) => {

    return (
        <Container>
            <div className="row justify-content-center">
                <div className="col-10">
                    {auctions.map((auction) => (
                        <AuctionCard auction={auction} key={auction.id} />
                    ))}
                </div>
            </div>
        </Container>
    );
};

export default AuctionList;

