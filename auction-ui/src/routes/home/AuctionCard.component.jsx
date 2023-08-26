import React from 'react';
import {Card, ListGroup} from 'react-bootstrap';
import {format, isAfter} from "date-fns";

const AuctionCardComponent = ({ auction }) => {
    const formattedStartTime = format(new Date(auction.startTime), 'MMM dd, yyyy hh:mm a');
    const formattedEndTime = format(new Date(auction.endTime), 'MMM dd, yyyy hh:mm a');
// Check if the auction has ended
    const hasEnded = isAfter(new Date(), new Date(auction.endTime));

    return (
        <Card className={`auction-card ${hasEnded ? 'ended' : ''}`} style={{ width: '20rem' }}>
        <Card.Img variant="top" src={auction.product.url} />
        <Card.Body>
            <Card.Title>{auction.product.name}</Card.Title>
            <Card.Text>
                {auction.product.description}
            </Card.Text>
        </Card.Body>
        <ListGroup className="list-group-flush">
            <ListGroup.Item>Last price: ${auction.startingPrice}</ListGroup.Item>
            <ListGroup.Item>Starts: {formattedStartTime}</ListGroup.Item>
            <ListGroup.Item>Ends: {formattedEndTime}</ListGroup.Item>
            <ListGroup.Item> Bids: {auction.bids.length > 0 ? auction.bids.length : 0}</ListGroup.Item>

        </ListGroup>
            <Card.Body className="text-center">
                {hasEnded ? (
                    <p className="ended-message text-danger font-weight-bold">Auction Ended</p>
                ) : (
                    <Card.Link className="btn btn-dark" href={`/auctions/details/${auction.id}`}>View</Card.Link>
                )}
        </Card.Body>
    </Card>
    );
}

export default AuctionCardComponent
