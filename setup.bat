@echo off
REM FLC Booking System - Quick Start Script for Windows

echo =========================================
echo FLC Booking System - Backend Setup
echo =========================================
echo.

REM Check Java
echo Checking Java installation...
java -version >nul 2>&1
if errorlevel 1 (
    echo [X] Java is not installed. Please install Java 17 or higher.
    pause
    exit /b 1
)
for /f "tokens=*" %%i in ('java -version 2^>^&1 ^| findstr /R "version"') do set JAVA_VERSION=%%i
echo [OK] Java found: %JAVA_VERSION%
echo.

REM Check Maven
echo Checking Maven installation...
mvn -v >nul 2>&1
if errorlevel 1 (
    echo [X] Maven is not installed. Please install Maven 3.8.0 or higher.
    pause
    exit /b 1
)
for /f "tokens=*" %%i in ('mvn -v 2^>^&1 ^| findstr /R "Apache Maven"') do set MVN_VERSION=%%i
echo [OK] Maven found: %MVN_VERSION%
echo.

REM Build backend
echo Building backend...
cd backend
call mvn clean install -DskipTests
if errorlevel 1 (
    echo [X] Backend build failed!
    cd ..
    pause
    exit /b 1
)
echo [OK] Backend build successful!
cd ..
echo.

echo =========================================
echo [OK] Setup Complete!
echo =========================================
echo.
echo To start the backend, run:
echo   cd backend
echo   mvn spring-boot:run
echo.
echo Or run the JAR directly:
echo   java -jar backend\target\flc-booking-system-1.0.0.jar
echo.
echo To start the frontend, run:
echo   cd frontend
echo   npm install
echo   npm start
echo.
echo API will be available at: http://localhost:8080/api
echo Docs: Check README.md for API endpoints
echo.
pause
