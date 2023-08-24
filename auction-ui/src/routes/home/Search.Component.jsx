import React, {useEffect, useState} from 'react';
import { Form, FormControl, Button } from 'react-bootstrap';

const SearchComponent = ({ onSearch }) => {
    const [searchQuery, setSearchQuery] = useState('');

    const [searchActive, setSearchActive] = useState(false);

    useEffect(() => {
        if (!searchActive) {
            onSearch(''); // Load unfiltered items
        }
    }, [searchActive, onSearch]);

    const handleSearch = () => {
        setSearchActive(true);
        onSearch(searchQuery);
    };

    const handleClear = () => {
        setSearchActive(false);
        setSearchQuery('');
    };

    return (

        <Form inline>
            <div className="d-flex">
            <FormControl
                type="text"
                placeholder="Search..."
                className="mr-sm-2"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
            />
                <Button variant="outline-success mr-2" onClick={handleSearch}>Search</Button>
                <Button variant="outline-dark" onClick={handleClear}>Clear</Button>
            </div>
        </Form>

    );
};

export default SearchComponent;
