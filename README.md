
# Tourism Agency Management System

## Description
Tourism Agency Promotional Video

Link : https://drive.google.com/drive/folders/1v4aX8t81EfQar818K_pRkrHmEdlIcigX?usp=sharing

This project aims to create a digital management system for Patika Tourism Agency to streamline its daily operations in the hotel sector and optimize customer reservation processes.


## Table of Contents

- [Description](#description)
- [Technologies](#technologies)
- [Project Overview](#project-overview)
- [Features](#features)
- [Installation](#installation)
    - [Clone](#clone)
    - [Permission Table](#permission-table)
- [Database Design](#database-design)
- [Coding Practices](#coding-practices)
- [Screenshots](#screenshots)

## Technologies

- Java 21
- Swing Framework
- PostgreSQL
- IntelliJ IDEA

## Project Overview

The project consists of 3 main parts: **Hotel Management**, **User Management** and **Reservation Management**.

## Features

- **User Management:**
    - Admin can add, delete, update, and filter users based on their roles (admin, staff).

- **Hotel Management:**
    - List hotels with details such as name, address, star rating, and amenities.
    - Add new hotels with information like email, phone, and location.
    - Specify accommodation types (pension types) offered by each hotel.

- **Room Management:**
    - List rooms for each hotel with details like type, features, and availability.
    - Add new rooms, specifying type, features, and stock quantity.

- **Season Management:**
    - Define historical seasons to adjust room pricing based on periods.
    - Specify start and end dates for each season.

- **Pricing Management:**
    - Set dynamic pricing for rooms based on seasons and accommodation types.
    - Differentiate prices for adults and children.

- **Room Search:**
    - Search available rooms based on city, date range, and hotel name.
    - Display relevant information about hotels and rooms that match the search criteria.

- **Reservation Operations:**
    - List existing reservations with details like check-in/out dates, guests, and total price.
    - Add new reservations by selecting available rooms and providing guest information.
    - Delete or update existing reservations.
- **Security:**
    - Users are required to log in to the system with their credentials.
    - 
## Installation

### Clone

1. Clone the repository: `git clone https://github.com/ahmedaliibrahim01/Tourism_Agency_System.git`
2. Navigate to the project directory: `cd Tourism_Agency_System`
3. Open the project in your IDE
4. Create a database named `tourismagencysystem` in PostgreSQL or change the database name in the `src/main/java/agency/core/Db.java` file
5. Import the `tourismagencysystem.sql`
6. Run the project

### Permission Table

### Admin

- User Management: Admin can list, add, delete, update, and filter users based on their roles (admin, staff).

### Employee (Agency Staff)

- Hotel Management: List and add hotels.
- Room Management: List and add rooms.
- Season Management: List and add seasons.
- Pricing Management.
- Room Search.
- Reservation Operations: List, add, delete, and update reservations.


## Database Design

The system uses a relational database with the following tables:

- `user`: Stores user information.
- `hotel`: Stores hotel information.
- `hotel_season`: Stores seasonal information for hotels.
- `hote_pension`: Stores pension types for hotels.
- `hotel_room`: Stores room information.
- `reservation`: Stores reservation details.

The project uses Swing for the graphical user interface, providing a user-friendly experience for both admins and employees.

### Coding Practices

- Variable and function names are clear and self-explanatory.
- Code readability and formatting are maintained with proper indentation.
- Comments and documentation are provided for better understanding.
- The code is modular and reusable.

## Screenshots

Include screenshots here to visually showcase the application's interface and features.
