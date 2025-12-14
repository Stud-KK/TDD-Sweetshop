import { useState } from 'react';
import './SearchBar.css';

const SearchBar = ({ onSearch }) => {
  const [filters, setFilters] = useState({
    name: '',
    category: '',
    minPrice: '',
    maxPrice: '',
  });

  const handleChange = (field, value) => {
    setFilters((prev) => ({ ...prev, [field]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const searchParams = {
      name: filters.name || undefined,
      category: filters.category || undefined,
      minPrice: filters.minPrice ? parseFloat(filters.minPrice) : undefined,
      maxPrice: filters.maxPrice ? parseFloat(filters.maxPrice) : undefined,
    };
    onSearch(searchParams);
  };

  const handleReset = () => {
    setFilters({
      name: '',
      category: '',
      minPrice: '',
      maxPrice: '',
    });
    onSearch({});
  };

  return (
    <div className="search-bar">
      <form onSubmit={handleSubmit} className="search-form">
        <div className="search-group">
          <input
            type="text"
            placeholder="Search by name..."
            value={filters.name}
            onChange={(e) => handleChange('name', e.target.value)}
            className="search-input"
          />
        </div>
        <div className="search-group">
          <input
            type="text"
            placeholder="Category..."
            value={filters.category}
            onChange={(e) => handleChange('category', e.target.value)}
            className="search-input"
          />
        </div>
        <div className="search-group">
          <input
            type="number"
            placeholder="Min Price"
            value={filters.minPrice}
            onChange={(e) => handleChange('minPrice', e.target.value)}
            className="search-input"
            min="0"
            step="0.01"
          />
        </div>
        <div className="search-group">
          <input
            type="number"
            placeholder="Max Price"
            value={filters.maxPrice}
            onChange={(e) => handleChange('maxPrice', e.target.value)}
            className="search-input"
            min="0"
            step="0.01"
          />
        </div>
        <div className="search-buttons">
          <button type="submit" className="search-button">
            🔍 Search
          </button>
          <button type="button" onClick={handleReset} className="reset-button">
            Reset
          </button>
        </div>
      </form>
    </div>
  );
};

export default SearchBar;

