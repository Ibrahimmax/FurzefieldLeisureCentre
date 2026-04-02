import React, { useState } from 'react';
import './TimetableView.css';

function TimetableView() {
  const [selectedDay, setSelectedDay] = useState('Saturday');
  const [filterExercise, setFilterExercise] = useState('All');

  const exerciseTypes = ['Yoga', 'Zumba', 'Aquacise', 'Box Fit', 'Body Blitz'];

  const timetableData = {
    Saturday: [
      { id: 1, time: '09:00 AM', exercise: 'Yoga', instructor: 'Sarah', capacity: 4, booked: 2, price: 15 },
      { id: 2, time: '01:00 PM', exercise: 'Zumba', instructor: 'Maria', capacity: 4, booked: 4, price: 18 },
      { id: 3, time: '06:00 PM', exercise: 'Box Fit', instructor: 'John', capacity: 4, booked: 3, price: 20 },
      { id: 4, time: '09:00 AM', exercise: 'Aquacise', instructor: 'Emma', capacity: 4, booked: 1, price: 16 },
      { id: 5, time: '01:00 PM', exercise: 'Body Blitz', instructor: 'Alex', capacity: 4, booked: 2, price: 19 },
      { id: 6, time: '06:00 PM', exercise: 'Yoga', instructor: 'Sarah', capacity: 4, booked: 3, price: 15 },
    ],
    Sunday: [
      { id: 7, time: '09:00 AM', exercise: 'Yoga', instructor: 'Sarah', capacity: 4, booked: 1, price: 15 },
      { id: 8, time: '01:00 PM', exercise: 'Box Fit', instructor: 'John', capacity: 4, booked: 2, price: 20 },
      { id: 9, time: '06:00 PM', exercise: 'Aquacise', instructor: 'Emma', capacity: 4, booked: 4, price: 16 },
      { id: 10, time: '09:00 AM', exercise: 'Zumba', instructor: 'Maria', capacity: 4, booked: 3, price: 18 },
      { id: 11, time: '01:00 PM', exercise: 'Body Blitz', instructor: 'Alex', capacity: 4, booked: 2, price: 19 },
      { id: 12, time: '06:00 PM', exercise: 'Box Fit', instructor: 'John', capacity: 4, booked: 1, price: 20 },
    ]
  };

  const filteredLessons = filterExercise === 'All' 
    ? timetableData[selectedDay]
    : timetableData[selectedDay].filter(lesson => lesson.exercise === filterExercise);

  const getAvailableSeats = (lesson) => lesson.capacity - lesson.booked;

  const getStatusColor = (available) => {
    if (available === 0) return 'full';
    if (available === 1) return 'limited';
    return 'available';
  };

  return (
    <div className="timetable-view">
      <h2>Class Timetable</h2>

      <div className="filter-section">
        <div className="filter-group">
          <label>Select Day:</label>
          <select value={selectedDay} onChange={(e) => setSelectedDay(e.target.value)}>
            <option>Saturday</option>
            <option>Sunday</option>
          </select>
        </div>

        <div className="filter-group">
          <label>Filter by Exercise:</label>
          <select value={filterExercise} onChange={(e) => setFilterExercise(e.target.value)}>
            <option>All</option>
            {exerciseTypes.map(exercise => (
              <option key={exercise}>{exercise}</option>
            ))}
          </select>
        </div>
      </div>

      <div className="legend">
        <span className="legend-item">
          <span className="legend-box available"></span> Available
        </span>
        <span className="legend-item">
          <span className="legend-box limited"></span> Limited Spots
        </span>
        <span className="legend-item">
          <span className="legend-box full"></span> Full
        </span>
      </div>

      <div className="lessons-container">
        {filteredLessons.length > 0 ? (
          filteredLessons.map(lesson => (
            <div key={lesson.id} className={`lesson-card ${getStatusColor(getAvailableSeats(lesson))}`}>
              <div className="lesson-time">
                <span className="time-value">{lesson.time}</span>
              </div>

              <div className="lesson-details">
                <h3>{lesson.exercise}</h3>
                <p className="instructor">👨‍🏫 By {lesson.instructor}</p>
                <p className="capacity">
                  Capacity: <strong>{lesson.booked}/{lesson.capacity}</strong> booked
                </p>
              </div>

              <div className="lesson-availability">
                <div className="availability-bar">
                  <div 
                    className="availability-fill"
                    style={{ width: `${(lesson.booked / lesson.capacity) * 100}%` }}
                  ></div>
                </div>
                <p className="available-text">
                  {getAvailableSeats(lesson)} spot{getAvailableSeats(lesson) !== 1 ? 's' : ''} available
                </p>
              </div>

              <div className="lesson-footer">
                <span className="price">£{lesson.price}</span>
                <button 
                  className="btn-primary"
                  disabled={getAvailableSeats(lesson) === 0}
                >
                  {getAvailableSeats(lesson) === 0 ? 'Full' : 'Book'}
                </button>
              </div>
            </div>
          ))
        ) : (
          <div className="no-data">
            <p>No lessons found for the selected filters.</p>
          </div>
        )}
      </div>

      <div className="timetable-info">
        <h3>Timetable Information</h3>
        <ul>
          <li>Lessons are available on Saturday and Sunday</li>
          <li>Each day has 3 time slots: Morning (9 AM), Afternoon (1 PM), Evening (6 PM)</li>
          <li>Maximum capacity: 4 members per lesson</li>
          <li>Members can book multiple lessons if no time conflicts exist</li>
          <li>Lesson prices vary by exercise type</li>
        </ul>
      </div>
    </div>
  );
}

export default TimetableView;
