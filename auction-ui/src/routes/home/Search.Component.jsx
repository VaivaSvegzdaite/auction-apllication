import React, {useEffect, useState} from 'react';
import { InputGroup,  FormControl, Button } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons'
import { faXmark } from '@fortawesome/free-solid-svg-icons'

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
        <InputGroup>
            <FormControl
                type="text"
                placeholder="Search..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
            />
            <Button variant="light" onClick={handleSearch}><FontAwesomeIcon icon={faMagnifyingGlass} /></Button>
            <Button variant="light" onClick={handleClear}><FontAwesomeIcon icon={faXmark} /></Button>
        </InputGroup>
    );
};

export default SearchComponent;
