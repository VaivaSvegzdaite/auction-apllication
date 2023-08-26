import AuctionsListComponent from "./AuctionsList.component.jsx";
import SearchComponent from "./Search.Component.jsx";
import {useEffect, useState} from "react";
import AuctionService from "../../services/auction.service.jsx";

export default function HomeComponent() {
    const [searchQuery, setSearchQuery] = useState('');
    const [auctions, setAuctions] = useState([]);
    const [originalAuctions, setOriginalAuctions] = useState([]);

    useEffect(() => {
        AuctionService.getAllAuctions()
            .then(response => {
                console.log(response.data)
                const auctionData = response.data;
                setAuctions(auctionData);
                setOriginalAuctions(auctionData);
            })
            .catch(error => {
                console.error('Error fetching auction data:', error);
            });
    }, []);

    const handleSearch = (query) => {
        setSearchQuery(query);
    };

    useEffect(() => {
        if (searchQuery === '') {
            setAuctions(originalAuctions);
        } else {
            const filteredAuctions = originalAuctions.filter(auction =>
                auction.product.name.toLowerCase().includes(searchQuery.toLowerCase())
            );
            setAuctions(filteredAuctions);
        }
    }, [searchQuery, originalAuctions]);

    return (
        <div className="container">
            <div className="row">
                <div className="col-md-6 mt-5">
                    <SearchComponent onSearch={handleSearch}/>
                </div>
            </div>
            <AuctionsListComponent auctions={auctions} />
        </div>
    );
}
