#!/bin/bash

# FLC Booking System - Quick Start Script

echo "========================================="
echo "FLC Booking System - Backend Setup"
echo "========================================="
echo ""

# Check Java
echo "Checking Java installation..."
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 17 or higher."
    exit 1
fi
JAVA_VERSION=$(java -version 2>&1 | head -n 1)
echo "✓ Java found: $JAVA_VERSION"
echo ""

# Check Maven
echo "Checking Maven installation..."
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed. Please install Maven 3.8.0 or higher."
    exit 1
fi
MVN_VERSION=$(mvn -v 2>&1 | head -n 1)
echo "✓ Maven found: $MVN_VERSION"
echo ""

# Check PostgreSQL
echo "Checking PostgreSQL connection..."
if command -v psql &> /dev/null; then
    if psql -U postgres -d postgres -c "SELECT 1;" &> /dev/null; then
        echo "✓ PostgreSQL is running"
        # Create database if not exists
        psql -U postgres -d postgres -tc "SELECT 1 FROM pg_database WHERE datname = 'flc_booking_db'" | grep -q 1 || psql -U postgres -d postgres -c "CREATE DATABASE flc_booking_db"
        echo "✓ Database 'flc_booking_db' is ready"
    else
        echo "⚠ PostgreSQL not responding. Please ensure PostgreSQL is running."
        echo "  Hint: Use 'docker-compose up -d' to start PostgreSQL in Docker"
    fi
else
    echo "⚠ psql not found. Assuming PostgreSQL is running (Docker or remote)."
fi
echo ""

# Build backend
echo "Building backend..."
cd backend
mvn clean install -DskipTests
if [ $? -eq 0 ]; then
    echo "✓ Backend build successful!"
else
    echo "❌ Backend build failed!"
    exit 1
fi
echo ""

echo "========================================="
echo "✓ Setup Complete!"
echo "========================================="
echo ""
echo "To start the backend, run:"
echo "  cd backend"
echo "  mvn spring-boot:run"
echo ""
echo "Or run the JAR directly:"
echo "  java -jar backend/target/flc-booking-system-1.0.0.jar"
echo ""
echo "To start the frontend, run:"
echo "  cd frontend"
echo "  npm install"
echo "  npm start"
echo ""
echo "API will be available at: http://localhost:8080/api"
echo "Docs: Check README.md for API endpoints"
echo ""
