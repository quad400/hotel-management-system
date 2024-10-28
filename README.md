### **Hotel Management System API**

The Hotel Management System API is designed to streamline and manage various aspects of hotel operations. This API provides endpoints to manage bookings, guests, rooms, services, and staff, allowing seamless integration for front-end applications or external systems.

**Core Features:**

1. **User Management:**
    
    - Register, authenticate, and authorize different types of users, including hotel staff, administrators, and guests. Each user role has distinct permissions to access and modify resources.
        
    - Handles user profiles, login sessions, and password recovery.
        
2. **Room Management:**
    
    - Create, update, and delete room listings.
        
    - Manage room details such as room number, type (single, double, suite), price, status (available, occupied, under maintenance), and amenities.
        
    - Filter rooms based on availability, type, and other parameters.
        
3. **Reservation Management:**
    
    - Book rooms by specifying check-in and check-out dates, selecting rooms, and adding guest details.
        
    - Modify or cancel reservations, view reservation history, and check reservation status.
        
    - Search reservations by guest information, reservation status, or booking date.
        
4. **Guest and Service Management:**
    
    - Manage guest profiles, services provided, and payment details.
        
    - Keep track of guest requests and orders, like room service, spa appointments, laundry services, and meals.
        
    - Each service can have unique pricing based on room type, guest category, and additional features.
        

#### Technologies used

- **Backend Framework:** Spring Boot, Hibernate/JPA
    
- **Database:** PostgreSQL
    
- **Authentication & Authorization:** JWT for secure authentication and role-based access control
    
- **API Documentation:** Postman
    
- **Architecture:** Monolithic as needed
    

**How to Use:**

1. **Authentication:** Start by registering or logging in to retrieve an authentication token (JWT).
    
2. **Access Endpoints:** Use the JWT token in headers (e.g., `Authorization: Bearer` ) to access authorized endpoints.
    
3. **Reservations & Order Services:** Reserve rooms, order additional services, and manage existing bookings.
    

**Postman Collection:**  
All API endpoints are documented in a Postman collection, with details on request parameters, headers, and example responses for each endpoint. The collection includes test examples and descriptions to guide developers in integrating with the API.

**Project Objectives:**  
The main goal is to provide a complete backend solution for managing hotel operations efficiently, enhancing guest experience through automated bookings, service tracking, and streamlined payment processing.

| **Business Error Code** | **Business Error Name** |
| --- | --- |
| 301 | Conflict |
| 302 | Bad Request |
| 303 | Unauthorrized |
| 304 | Not Found |
| 305 | Unprocessable Entity |
