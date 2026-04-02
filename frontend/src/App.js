import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Dashboard from './components/Dashboard';
import TimetableView from './components/TimetableView';
import BookingManagement from './components/BookingManagement';
import MemberManagement from './components/MemberManagement';
import ReviewsSection from './components/ReviewsSection';
import ReportsSection from './components/ReportsSection';
import './App.css';

function App() {
  const [activeTab, setActiveTab] = useState('dashboard');

  return (
    <Router>
      <div className="app-container">
        <header className="app-header">
          <div className="header-content">
            <h1>🏋️ FLC Booking System</h1>
            <p>Furzefield Leisure Centre - Group Exercise Booking Management</p>
          </div>
        </header>

        <nav className="app-navigation">
          <Link 
            to="/" 
            className={`nav-link ${activeTab === 'dashboard' ? 'active' : ''}`}
            onClick={() => setActiveTab('dashboard')}
          >
            Dashboard
          </Link>
          <Link 
            to="/timetable" 
            className={`nav-link ${activeTab === 'timetable' ? 'active' : ''}`}
            onClick={() => setActiveTab('timetable')}
          >
            Timetable
          </Link>
          <Link 
            to="/bookings" 
            className={`nav-link ${activeTab === 'bookings' ? 'active' : ''}`}
            onClick={() => setActiveTab('bookings')}
          >
            Bookings
          </Link>
          <Link 
            to="/members" 
            className={`nav-link ${activeTab === 'members' ? 'active' : ''}`}
            onClick={() => setActiveTab('members')}
          >
            Members
          </Link>
          <Link 
            to="/reviews" 
            className={`nav-link ${activeTab === 'reviews' ? 'active' : ''}`}
            onClick={() => setActiveTab('reviews')}
          >
            Reviews
          </Link>
          <Link 
            to="/reports" 
            className={`nav-link ${activeTab === 'reports' ? 'active' : ''}`}
            onClick={() => setActiveTab('reports')}
          >
            Reports
          </Link>
        </nav>

        <main className="app-main">
          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/timetable" element={<TimetableView />} />
            <Route path="/bookings" element={<BookingManagement />} />
            <Route path="/members" element={<MemberManagement />} />
            <Route path="/reviews" element={<ReviewsSection />} />
            <Route path="/reports" element={<ReportsSection />} />
          </Routes>
        </main>

        <footer className="app-footer">
          <p>&copy; 2026 Furzefield Leisure Centre. All rights reserved.</p>
        </footer>
      </div>
    </Router>
  );
}

export default App;
