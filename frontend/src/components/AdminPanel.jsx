import { useState } from 'react';
import { sweetsAPI } from '../services/api';
import './AdminPanel.css';

const AdminPanel = ({ sweet, onClose, onSuccess }) => {
  const [formData, setFormData] = useState({
    name: sweet?.name || '',
    category: sweet?.category || '',
    price: sweet?.price || '',
    quantity: sweet?.quantity || 0,
    description: sweet?.description || '',
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const sweetData = {
        name: formData.name,
        category: formData.category,
        price: parseFloat(formData.price),
        quantity: parseInt(formData.quantity),
        description: formData.description,
      };

      if (sweet) {
        await sweetsAPI.update(sweet.id, sweetData);
      } else {
        await sweetsAPI.create(sweetData);
      }
      onSuccess();
      onClose();
    } catch (error) {
      setError(error.response?.data?.message || 'Operation failed');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="admin-panel-overlay" onClick={onClose}>
      <div className="admin-panel" onClick={(e) => e.stopPropagation()}>
        <div className="admin-panel-header">
          <h2>{sweet ? 'Edit Sweet' : 'Add New Sweet'}</h2>
          <button className="close-button" onClick={onClose}>
            ✕
          </button>
        </div>
        <form onSubmit={handleSubmit} className="admin-form">
          {error && <div className="error-message">{error}</div>}
          <div className="form-group">
            <label htmlFor="name">Name *</label>
            <input
              type="text"
              id="name"
              name="name"
              value={formData.name}
              onChange={handleChange}
              required
              placeholder="Sweet name"
            />
          </div>
          <div className="form-group">
            <label htmlFor="category">Category *</label>
            <input
              type="text"
              id="category"
              name="category"
              value={formData.category}
              onChange={handleChange}
              required
              placeholder="e.g., Chocolate, Candy, Bakery"
            />
          </div>
          <div className="form-row">
            <div className="form-group">
              <label htmlFor="price">Price (₹) *</label>
              <input
                type="number"
                id="price"
                name="price"
                value={formData.price}
                onChange={handleChange}
                required
                min="0"
                step="0.01"
                placeholder="0.00"
              />
            </div>
            <div className="form-group">
              <label htmlFor="quantity">Quantity *</label>
              <input
                type="number"
                id="quantity"
                name="quantity"
                value={formData.quantity}
                onChange={handleChange}
                required
                min="0"
                placeholder="0"
              />
            </div>
          </div>
          <div className="form-group">
            <label htmlFor="description">Description</label>
            <textarea
              id="description"
              name="description"
              value={formData.description}
              onChange={handleChange}
              rows="3"
              placeholder="Optional description"
            />
          </div>
          <div className="form-actions">
            <button type="button" onClick={onClose} className="cancel-button">
              Cancel
            </button>
            <button type="submit" className="submit-button" disabled={loading}>
              {loading ? 'Saving...' : sweet ? 'Update' : 'Create'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default AdminPanel;

