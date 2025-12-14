# Sweet Shop Management System

A full-stack application for managing a sweet shop with user authentication, inventory management, and purchase functionality.

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Node.js 16+
- MongoDB (running locally or connection string)

### Backend Setup

```bash
cd backend
./mvnw spring-boot:run
```

Backend runs on `http://localhost:8080`

### Frontend Setup

```bash
cd frontend
npm install
npm run dev
```

Frontend runs on `http://localhost:3000`

## 🔑 Default Admin Credentials

- **Email:** `admin@sweetshop.com`
- **Password:** `admin123`

## ✨ Features

- User registration and login with JWT authentication
- View all available sweets
- Search and filter sweets by name, category, and price
- Purchase sweets (decreases inventory)
- Admin panel to add, edit, delete, and restock sweets

## 🛠️ Tech Stack

**Backend:**
- Java 17
- Spring Boot 3.2.0
- MongoDB
- JWT Authentication

**Frontend:**
- React 19
- React Router
- Axios
- Vite

## 📝 API Endpoints

- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user
- `GET /api/sweets` - Get all sweets
- `GET /api/sweets/search` - Search sweets
- `POST /api/sweets` - Create sweet (Admin)
- `PUT /api/sweets/:id` - Update sweet (Admin)
- `DELETE /api/sweets/:id` - Delete sweet (Admin)
- `POST /api/sweets/:id/purchase` - Purchase sweet
- `POST /api/sweets/:id/restock` - Restock sweet (Admin)

## 🧪 Testing

Backend tests follow TDD approach:
```bash
cd backend
./mvnw test
```

## 📁 Project Structure

```
sweetshop/
├── backend/          # Spring Boot API
└── frontend/         # React SPA
```

## 👤 My AI Usage

I used AI tools (Cursor AI) to:
- Generate boilerplate code for React components
- Assist with API integration and error handling
- Help with styling and responsive design
- Debug and optimize code structure

AI was used as a coding assistant to accelerate development while maintaining code quality and following best practices.
