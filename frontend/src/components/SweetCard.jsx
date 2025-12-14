import { useState } from 'react';
import { sweetsAPI } from '../services/api';
import './SweetCard.css';

const SweetCard = ({ sweet, onRefresh, onEdit, isAdmin }) => {
  const [purchaseQuantity, setPurchaseQuantity] = useState(1);
  const [restockQuantity, setRestockQuantity] = useState(10);
  const [loading, setLoading] = useState(false);
  const [showRestock, setShowRestock] = useState(false);

  const handlePurchase = async () => {
    if (sweet.quantity === 0) return;
    
    setLoading(true);
    try {
      await sweetsAPI.purchase(sweet.id, purchaseQuantity);
      onRefresh();
    } catch (error) {
      alert(error.response?.data?.message || 'Purchase failed');
    } finally {
      setLoading(false);
    }
  };

  const handleRestock = async () => {
    setLoading(true);
    try {
      await sweetsAPI.restock(sweet.id, restockQuantity);
      onRefresh();
      setShowRestock(false);
    } catch (error) {
      alert(error.response?.data?.message || 'Restock failed');
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async () => {
    if (!window.confirm(`Are you sure you want to delete ${sweet.name}?`)) {
      return;
    }
    setLoading(true);
    try {
      await sweetsAPI.delete(sweet.id);
      onRefresh();
    } catch (error) {
      alert(error.response?.data?.message || 'Delete failed');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={`sweet-card ${sweet.quantity === 0 ? 'out-of-stock' : ''}`}>
      <div className="sweet-header">
        <h3 className="sweet-name">{sweet.name}</h3>
        {isAdmin && (
          <div className="admin-actions">
            <button
              className="icon-button edit-btn"
              onClick={() => onEdit && onEdit(sweet)}
              title="Edit"
            >
              ✏️
            </button>
            <button
              className="icon-button delete-btn"
              onClick={handleDelete}
              disabled={loading}
              title="Delete"
            >
              🗑️
            </button>
          </div>
        )}
      </div>
      <div className="sweet-category">{sweet.category}</div>
      {sweet.description && (
        <p className="sweet-description">{sweet.description}</p>
      )}
      <div className="sweet-details">
        <div className="sweet-price">₹{parseFloat(sweet.price).toFixed(2)}</div>
        <div className={`sweet-quantity ${sweet.quantity === 0 ? 'zero' : ''}`}>
          Stock: {sweet.quantity}
        </div>
      </div>
      <div className="sweet-actions">
        {sweet.quantity > 0 ? (
          <>
            <div className="quantity-selector">
              <label>Quantity:</label>
              <input
                type="number"
                min="1"
                max={sweet.quantity}
                value={purchaseQuantity}
                onChange={(e) => setPurchaseQuantity(Math.max(1, Math.min(sweet.quantity, parseInt(e.target.value) || 1)))}
                className="quantity-input"
              />
            </div>
            <button
              className="purchase-button"
              onClick={handlePurchase}
              disabled={loading}
            >
              {loading ? 'Processing...' : 'Purchase'}
            </button>
          </>
        ) : (
          <div className="out-of-stock-message">Out of Stock</div>
        )}
        {isAdmin && (
          <button
            className="restock-button"
            onClick={() => setShowRestock(!showRestock)}
          >
            {showRestock ? 'Cancel' : 'Restock'}
          </button>
        )}
      </div>
      {showRestock && isAdmin && (
        <div className="restock-form">
          <input
            type="number"
            min="1"
            value={restockQuantity}
            onChange={(e) => setRestockQuantity(Math.max(1, parseInt(e.target.value) || 1))}
            className="restock-input"
            placeholder="Quantity to add"
          />
          <button
            className="restock-submit"
            onClick={handleRestock}
            disabled={loading}
          >
            Add Stock
          </button>
        </div>
      )}
    </div>
  );
};

export default SweetCard;

