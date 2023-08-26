import React, { useState } from 'react';
import { Card, ListGroup, Form, Button } from 'react-bootstrap';
import {format, isAfter} from 'date-fns';
import BidService from "../../services/bid.service.jsx";
import AuctionService from "../../services/auction.service.jsx";

const AuctionBid = ({auction}) => {

    const [bidAmount, setBidAmount] = useState('');
    const [biddingError, setBiddingError] = useState('');
    const [ isLoading, setIsLoading] = useState(false);
    const [auctionUpdate, setAuctionUpdate] = useState('');

    const formattedStartTime = format(new Date(auction.startTime), 'MMM dd, yyyy hh:mm a');
    const formattedEndTime = format(new Date(auction.endTime), 'MMM dd, yyyy hh:mm a');

    const handleBidChange = (event) => {
        setBidAmount(event.target.value);
    };

    const handleBidSubmit = (event) => {
        event.preventDefault();

        if (bidAmount <= auction.startingPrice) {
            setBiddingError('Bid amount must be greater than the starting price.');
            return;
        }

        const currentTime = new Date();
        if (isAfter(currentTime, new Date(auction.endTime))) {
            setBiddingError('Auction has ended. Bidding is no longer allowed.');
            return;
        }

        const data = {
            price: bidAmount,
            productId: auction.product.id,
            userId: auction.user.id,
            auctionId: auction.id
        };
        setIsLoading(true);
        BidService.create(data).then(response => {
            setIsLoading(false);
            const newBidId = response.data.id;
            const bids = auction.bids;
            const updatedBids = [...bids, newBidId];
            const updatedAuction = {
                ...auction,
                startingPrice: data.price,
                bids: updatedBids
            };
            setBiddingError('');

            setAuctionUpdate(updatedAuction);

            AuctionService.updateAuction(auction.id, updatedAuction)
                .then(updatedAuctionResponse => {
                    console.log("Auction updated in backend:", updatedAuctionResponse.data);
                    window.location.reload();
                })
                .catch(error => {
                    console.error("Error updating auction:", error);
                });
        }).catch(error => {
            setIsLoading(false);
            setBiddingError('Error placing auction-details-bid');
        });
        setBidAmount('');
    };

    return (
        <Card style={{ width: '25rem' }}>
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
            <Card.Body className="text-center">
                <Form onSubmit={handleBidSubmit}>
                        <Form.Group>
                            <Form.Control
                                type="text"
                                placeholder="Enter your bid"
                                value={bidAmount}
                                onChange={handleBidChange}
                            />
                        </Form.Group>
                        <Button variant="dark" type="submit" className="mt-4">
                            Place Bid
                        </Button>
                        {biddingError &&  <div className="form-group">
                            <div className="alert alert-danger" role="alert">
                                {biddingError}
                            </div>
                        </div>}
                    </Form>
            </Card.Body>
            </ListGroup>
        </Card>
    );
};

export default AuctionBid;


