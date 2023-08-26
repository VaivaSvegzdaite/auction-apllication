import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import AuctionService from "../../services/auction.service.jsx";
import AuctionDetails from "./AuctionDetails.component.jsx";

export default function AuctionDetailsBid() {
    const {auctionId} = useParams();
    const [auction, setAuction] = useState(null);

    useEffect(() => {
        AuctionService.getAllAuctions()
            .then(response => {
                let auctions = response.data
                setAuction(auctions)
                const selected = auctions.find(auction => auction.id === Number(auctionId));
                console.log('Selected auction:', selected);
                setAuction(selected || null);
            })
            .catch(error => {
                console.error('Error fetching auction data:', error);
            });
    }, [auctionId]);

    return (
    auction !== null ? (
        <AuctionDetails auction={auction}/>
    ) : (
        <p>Loading or no auction found</p>
    )
    );
}

