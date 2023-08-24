import React, { useState, useEffect } from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import AuctionCard from "./AuctionCard.component.jsx"

const AuctionList = ({auctions}) => {

    return (
        <Container>
            <Row>
                {auctions.map((auction) => (
                    <Col key={auction.id} md={4} className="mb-4">
                        <AuctionCard auction={auction} />
                    </Col>
                ))}
            </Row>
        </Container>
    );
};

export default AuctionList;

