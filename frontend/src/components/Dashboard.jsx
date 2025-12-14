import { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { sweetsAPI } from '../services/api';
import SweetCard from './SweetCard';
import SearchBar from './SearchBar';
import AdminPanel from './AdminPanel';
import './Dashboard.css';

const Dashboard = () => {
  const { user, logout, isAdmin } = useAuth();
  const [sweets, setSweets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showAdminPanel, setShowAdminPanel] = useState(false);
  const [editingSweet, setEditingSweet] = useState(null);
  const [searchParams, setSearchParams] = useState({});

  useEffect(() => {
    fetchSweets();
  }, []);

  const fetchSweets = async () => {
    setLoading(true);
    try {
      let response;
      if (Object.keys(searchParams).length === 0 || 
          Object.values(searchParams).every(v => v === undefined || v === '')) {
        response = await sweetsAPI.getAll();
      } else {
        response = await sweetsAPI.search(searchParams);
      }
      setSweets(response.data);
    } catch (error) {
      console.error('Error fetching sweets:', error);
      alert('Failed to load sweets');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchSweets();
  }, [searchParams]);

  const handleSearch = (params) => {
    setSearchParams(params);
  };

  const handleAddSweet = () => {
    setEditingSweet(null);
    setShowAdminPanel(true);
  };

  const handleEditSweet = (sweet) => {
    setEditingSweet(sweet);
    setShowAdminPanel(true);
  };

  const handleCloseAdminPanel = () => {
    setShowAdminPanel(false);
    setEditingSweet(null);
  };

  const handleAdminSuccess = () => {
    fetchSweets();
  };

  return (
    <div className="dashboard">
      <header className="dashboard-header">
        <div className="header-content">
          <h1 className="dashboard-title">🍬 Sweet Shop</h1>
          <div className="header-actions">
            {isAdmin && (
              <button className="add-button" onClick={handleAddSweet}>
                ➕ Add Sweet
              </button>
            )}
            <div className="user-info">
              <span className="user-email">{user?.email}</span>
              {isAdmin && <span className="admin-badge">ADMIN</span>}
            </div>
            <button className="logout-button" onClick={logout}>
              Logout
            </button>
          </div>
        </div>
      </header>

      <div className="dashboard-content">
        <SearchBar onSearch={handleSearch} />
        
        {loading ? (
          <div className="loading">Loading sweets...</div>
        ) : sweets.length === 0 ? (
          <div className="empty-state">
            <p>No sweets found. {isAdmin && 'Add some sweets to get started!'}</p>
          </div>
        ) : (
          <div className="sweets-grid">
            {sweets.map((sweet) => (
              <SweetCard
                key={sweet.id}
                sweet={sweet}
                onRefresh={fetchSweets}
                onEdit={handleEditSweet}
                isAdmin={isAdmin}
              />
            ))}
          </div>
        )}
      </div>

      {showAdminPanel && (
        <AdminPanel
          sweet={editingSweet}
          onClose={handleCloseAdminPanel}
          onSuccess={handleAdminSuccess}
        />
      )}
    </div>
  );
};

export default Dashboard;

