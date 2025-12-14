# Sweet Shop Frontend

A modern React frontend for the Sweet Shop Management System.

## Features

- 🔐 User Authentication (Login/Register)
- 🍬 Sweet Shop Dashboard
- 🔍 Search and Filter Sweets
- 🛒 Purchase Functionality
- 👨‍💼 Admin Panel (Add/Edit/Delete/Restock sweets)
- 📱 Responsive Design

## Prerequisites

- Node.js (v16 or higher)
- npm or yarn
- Backend API running on `http://localhost:8080`

## Installation

1. Install dependencies:
```bash
npm install
```

## Running the Application

1. Start the development server:
```bash
npm run dev
```

2. The application will be available at `http://localhost:3000`

## Default Admin Credentials

- Email: `admin@sweetshop.com`
- Password: `admin123`

## Project Structure

```
src/
├── components/          # React components
│   ├── Login.jsx        # Login form
│   ├── Register.jsx     # Registration form
│   ├── Dashboard.jsx    # Main dashboard
│   ├── SweetCard.jsx    # Individual sweet card
│   ├── SearchBar.jsx    # Search and filter
│   └── AdminPanel.jsx   # Admin add/edit form
├── context/             # React context
│   └── AuthContext.jsx  # Authentication context
├── services/            # API services
│   └── api.js          # API client
└── App.jsx             # Main app component
```

## API Endpoints Used

- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `GET /api/sweets` - Get all sweets
- `GET /api/sweets/search` - Search sweets
- `POST /api/sweets` - Create sweet (Admin)
- `PUT /api/sweets/:id` - Update sweet (Admin)
- `DELETE /api/sweets/:id` - Delete sweet (Admin)
- `POST /api/sweets/:id/purchase` - Purchase sweet
- `POST /api/sweets/:id/restock` - Restock sweet (Admin)

## Technologies Used

- React 19
- React Router DOM
- Axios
- Vite
- JWT Decode

## Build for Production

```bash
npm run build
```

The built files will be in the `dist` directory.
