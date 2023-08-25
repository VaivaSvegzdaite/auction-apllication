import React from 'react';
import {Card, ListGroup} from 'react-bootstrap';
import {format} from "date-fns";

const AuctionCardComponent = ({ auction }) => {
    const {id: auctionId, startTime, endTime, startingPrice, user, type, product, bids} = auction;
    const {name, description, category, userId, url} = product;
    const {id: auctionUserId, username, email, password, roles} = user;

    const formattedStartTime = format(new Date(startTime), 'MMM dd, yyyy hh:mm a');
    const formattedEndTime = format(new Date(endTime), 'MMM dd, yyyy hh:mm a');


    return (
    <Card style={{ width: '20rem' }}>
        <Card.Img variant="top" src={url} />
        <Card.Body>
            <Card.Title>{auction.product.name}</Card.Title>
            <Card.Text>
                {description}
            </Card.Text>
        </Card.Body>
        <ListGroup className="list-group-flush">
            <ListGroup.Item>Start price: ${startingPrice}</ListGroup.Item>
            <ListGroup.Item>Starts: {formattedStartTime}</ListGroup.Item>
            <ListGroup.Item>Ends: {formattedEndTime}</ListGroup.Item>
            <ListGroup.Item> Bids: {bids.length > 0 ? bids.length : 0}</ListGroup.Item>

        </ListGroup>
        <Card.Body className="text-center">
            <Card.Link className="btn btn-dark" href="#">View</Card.Link>
        </Card.Body>
    </Card>
    );
}

export default AuctionCardComponent
