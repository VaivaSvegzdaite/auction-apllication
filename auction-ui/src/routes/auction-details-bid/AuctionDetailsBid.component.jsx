import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import AuctionService from "../../services/auction.service.jsx";
import AuctionDetails from "./AuctionDetails.component.jsx";

export default function AuctionDetailsBid() {
    const {auctionId} = useParams();
    const [auction, setAuction] = useState(null);

    useEffect(() => {
        AuctionService.getAuctionById(auctionId)
            .then(response => {
                setAuction(response.data)
            })
            .catch(error => {
                console.error('Error fetching auction', error);
            });
    }, []);

    return (
    auction !== null ? (
        <AuctionDetails auction={auction} setAuction={setAuction}/>
    ) : (
        <p>Loading or no auction found</p>
    )
    );
}

