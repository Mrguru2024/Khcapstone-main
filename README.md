Keycode Help Backend (Capstone Project)
This repository contains the backend development for the Keycode Help project, part of the capstone for the Per Scholas Full Stack Java Developer program. The project is currently in its development phase and represents the foundational logic and core features of the application.

Note: The project is focused on building and testing backend features, preparing for the official project start on October 25, 2024, when development will transition to a new repository.

Table of Contents
Project Overview
Project Goals
Technologies Used
Project Structure
Features Implemented
Technical Architecture Diagram (TAD)
Future Development
Getting Started
Project Overview

Keycode Help is a web-based solution that simplifies the process of managing automotive keycode requests. The platform allows users to input VINs (Vehicle Identification Numbers) and request key codes for their vehicles. The backend system manages users, authentication, keycode requests, and subscription services.

The Keycode Help Backend serves as the core engine to process all user data, VIN lookups, and other business logic needed to handle keycode requests efficiently.

Project Goals
The main goal of this project is to lay the foundation for the full Keycode Help solution, with a focus on the backend development, security, and integration of third-party APIs. The following are key objectives:

User Registration and Authentication: Implement secure user registration and login mechanisms with role-based access control.
Keycode Request Handling: Enable users to request key codes by submitting VINs, with processing and storage in a database.
VIN Lookup: Develop functionality for VIN lookup services.
Subscription Service: Allow users to subscribe to keycode services for periodic access and manage their subscriptions.
Admin Dashboard: Develop an admin interface to manage users, subscriptions, and keycode requests.
Technologies Used
Java 17: The primary language for backend development.
Spring Boot: To build the application using Spring’s robust ecosystem.
Spring Security: To implement user authentication and authorization.
Hibernate & JPA: For ORM and database interactions.
MySQL: To manage the database for storing users, VIN requests, and keycodes.
Thymeleaf: For template rendering on frontend pages.
TailwindCSS: To style the frontend templates with a clean, futuristic design.
JUnit: For unit and integration testing.

Project Structure
bash
Copy code
Khcapstone-main/
├── src/
│   ├── main/
│   │   ├── java/com/keycodehelp/
│   │   │   ├── config/               # Security and other configurations
│   │   │   ├── controller/           # Controllers for handling HTTP requests
│   │   │   ├── dto/                  # Data transfer objects
│   │   │   ├── entities/             # JPA entities representing tables
│   │   │   ├── repositories/         # Interfaces for database operations
│   │   │   ├── services/             # Business logic services
│   │   │   └── utils/                # Utility classes (e.g., for JWT)
│   ├── resources/
│   │   ├── templates/                # Thymeleaf templates for frontend
│   │   └── static/css/               # TailwindCSS and other static assets
└── test/                             # Unit and integration tests

Features Implemented
1. User Authentication and Authorization
   Sign Up: Users can register for an account with username, email, and password.
   Login: Users authenticate using Spring Security, with JWT tokens for session management.
   Role-Based Access Control: Roles such as USER and ADMIN ensure only authorized users can access specific parts of the application.
2. Keycode Requests
   Users can submit a VIN via a web form to request the corresponding keycode.
   The backend validates and processes the request, saving it in the database via KeycodeRequestRepository.
   Admins can view and manage keycode requests in a dashboard.
3. Subscription Service
   Users can subscribe to services that grant them access to periodic VIN lookups.
   Subscription status is stored and managed via the SubscriptionRepository.
4. Admin Dashboard
   Admin users can manage other users, view keycode requests, and handle subscriptions.
   Technical Architecture Diagram (TAD)
   The following diagram outlines the basic architecture of the Keycode Help backend system:

plaintext
Copy code
+---------------------------------------------------------+
|                        Frontend                         |
|      Thymeleaf Templates, Tailwind CSS for Styling       |
+---------------------+-------------------+---------------+
/|\                  |  
|                   |
+----------------------|-------------------|---------------+
|                   Spring Boot Application                  |
|   Controllers, Services, Repositories, Security (JWT, RBAC) |
+----------------------+----------------+-------------------+
/|\               |
|                |
+----------------------------+  |  +----------------------------+
|         MySQL Database      |  |  | External VIN Lookup Service |
| (Users, Requests, Keycodes) |  |  +----------------------------+
+----------------------------+  |
+--+  
Frontend: Thymeleaf is used to render dynamic HTML pages, styled with TailwindCSS.
Backend: Spring Boot application handles all logic related to user authentication, VIN requests, and keycode lookups.
Database: MySQL stores data related to users, keycode requests, subscriptions, and more.
External Services: In future phases, an external VIN lookup API will be integrated.
Future Development
This repository focuses on building and testing the core logic of the Keycode Help project. The following tasks will be prioritized in the next development phase (starting on October 25, 2024):

Front-End Framework: React.js or Next.js will be integrated into a new repository for a more interactive front-end experience.
API Integration: Connecting to real-world VIN lookup services for accurate keycode retrieval.
Payment Integration: Implement subscription payments using services like Stripe or PayPal.
Enhanced Security: Additional features like 2FA (Two-Factor Authentication) and OAuth integration.
Getting Started
To run the project locally, follow these steps:

Prerequisites
Java 17 or higher installed
MySQL installed and running
Maven installed
Node.js and npm (for TailwindCSS)
Setup Steps
Clone this repository:

bash
Copy code
git clone https://github.com/Mrguru2024/Khcapstone-main.git
cd Khcapstone-main
Set up your MySQL database by importing the provided SQL files:

bash
Copy code
mysql -u root -p < keycodehelpdb.sql
Modify the application.properties file with your database credentials:

properties
Copy code
spring.datasource.url=jdbc:mysql://localhost:3306/keycodehelpdb
spring.datasource.username=root
spring.datasource.password=yourpassword
Install TailwindCSS:

bash
Copy code
npm install
Build the project:

bash
Copy code
mvn clean install
Run the application:

bash
Copy code
mvn spring-boot:run
Access the application at http://localhost:8081.

License
This project is licensed under the MIT License. See the LICENSE file for details.

