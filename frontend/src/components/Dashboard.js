import React, { useState, useEffect } from 'react';
import './Dashboard.css';

function Dashboard() {
  const [stats, setStats] = useState({
    totalMembers: 10,
    totalBookings: 45,
    totalLessons: 48,
    totalReviews: 20,
    upcomingBookings: 12,
    averageRating: 4.2
  });

  const [recentBookings, setRecentBookings] = useState([
    { id: 1, memberName: 'John Doe', lesson: 'Yoga', day: 'Saturday', time: 'Morning', status: 'Confirmed' },
    { id: 2, memberName: 'Jane Smith', lesson: 'Zumba', day: 'Sunday', time: 'Afternoon', status: 'Confirmed' },
    { id: 3, memberName: 'Mike Johnson', lesson: 'Box Fit', day: 'Saturday', time: 'Evening', status: 'Pending' },
  ]);

  return (
    <div className="dashboard">
      <h2>Dashboard</h2>
      
      <div className="stats-grid">
        <div className="stat-card">
          <div className="stat-icon">👥</div>
          <div className="stat-content">
            <h3>Total Members</h3>
            <p className="stat-value">{stats.totalMembers}</p>
          </div>
        </div>
        
        <div className="stat-card">
          <div className="stat-icon">📅</div>
          <div className="stat-content">
            <h3>Total Bookings</h3>
            <p className="stat-value">{stats.totalBookings}</p>
          </div>
        </div>
        
        <div className="stat-card">
          <div className="stat-icon">🏃</div>
          <div className="stat-content">
            <h3>Total Lessons</h3>
            <p className="stat-value">{stats.totalLessons}</p>
          </div>
        </div>
        
        <div className="stat-card">
          <div className="stat-icon">⭐</div>
          <div className="stat-content">
            <h3>Avg. Rating</h3>
            <p className="stat-value">{stats.averageRating}</p>
          </div>
        </div>
        
        <div className="stat-card">
          <div className="stat-icon">📝</div>
          <div className="stat-content">
            <h3>Total Reviews</h3>
            <p className="stat-value">{stats.totalReviews}</p>
          </div>
        </div>
        
        <div className="stat-card">
          <div className="stat-icon">📈</div>
          <div className="stat-content">
            <h3>Upcoming Bookings</h3>
            <p className="stat-value">{stats.upcomingBookings}</p>
          </div>
        </div>
      </div>

      <div className="dashboard-section">
        <h3>Recent Bookings</h3>
        <table className="table">
          <thead>
            <tr>
              <th>Member</th>
              <th>Lesson Type</th>
              <th>Day</th>
              <th>Time</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {recentBookings.map((booking) => (
              <tr key={booking.id}>
                <td>{booking.memberName}</td>
                <td>{booking.lesson}</td>
                <td>{booking.day}</td>
                <td>{booking.time}</td>
                <td>
                  <span className={`badge badge-${booking.status === 'Confirmed' ? 'success' : 'warning'}`}>
                    {booking.status}
                  </span>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <div className="dashboard-section">
        <h3>Quick Actions</h3>
        <div className="action-buttons">
          <button className="btn-primary">New Booking</button>
          <button className="btn-primary">Add Member</button>
          <button className="btn-primary">View Reports</button>
          <button className="btn-primary">View Timetable</button>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;
