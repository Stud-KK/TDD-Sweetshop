import { createContext, useContext, useState, useEffect } from 'react';
import { authAPI } from '../services/api';
import jwtDecode from 'jwt-decode';

const AuthContext = createContext(null);

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within AuthProvider');
  }
  return context;
};

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [token, setToken] = useState(localStorage.getItem('token'));
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (token) {
      try {
        const decoded = jwtDecode(token);
        setUser({
          email: decoded.sub,
          role: decoded.role || 'USER',
        });
      } catch (error) {
        console.error('Invalid token:', error);
        // Try to extract email from subject if role extraction fails
        try {
          const decoded = jwtDecode(token);
          setUser({
            email: decoded.sub || decoded.email || '',
            role: decoded.role || 'USER',
          });
        } catch (e) {
          logout();
        }
      }
    }
    setLoading(false);
  }, [token]);

  const login = async (email, password) => {
    try {
      const response = await authAPI.login(email, password);
      const newToken = response.data;
      localStorage.setItem('token', newToken);
      setToken(newToken);
      const decoded = jwtDecode(newToken);
      setUser({
        email: decoded.sub,
        role: decoded.role || 'USER',
      });
      return { success: true };
    } catch (error) {
      return {
        success: false,
        error: error.response?.data?.message || 'Login failed',
      };
    }
  };

  const register = async (name, email, password) => {
    try {
      await authAPI.register(name, email, password);
      return { success: true };
    } catch (error) {
      return {
        success: false,
        error: error.response?.data?.message || 'Registration failed',
      };
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    setToken(null);
    setUser(null);
  };

  const isAdmin = () => {
    return user?.role === 'ADMIN';
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        token,
        login,
        register,
        logout,
        isAdmin: isAdmin(),
        loading,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

