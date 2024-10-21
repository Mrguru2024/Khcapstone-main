LOGICS.md


Below, you will find the logic of the development. 
1. Site Components Overview
   The Keycode Help application is composed of several core components that work together to provide a seamless user experience. Here’s a breakdown of the key components:

Frontend: Built using Next.js for server-side rendering and client-side interactivity.

Pages for user login, VIN lookup, user dashboards, and administration.
Responsive design via Material-UI for consistent, accessible UI components.
Backend: Built with Spring Boot (Java) for the main API services and business logic.

Core services include VIN to keycode lookup, user management, subscription processing, and audit logging.
Database: PostgreSQL and Firebase Firestore for handling structured and real-time data, respectively.

PostgreSQL handles user data, payments, subscriptions, and VIN requests.
Firestore is used for logging real-time events and tracking user actions.
Third-Party Integrations:

Stripe: Payment processing for membership plans and subscriptions.
Firebase Authentication: Multi-factor authentication for user logins.
2. Database Flow
   The database schema revolves around key entities like Users, VIN Requests, Keycodes, Subscriptions, Payments, and Audit Logs.

Users table holds basic user details and roles.
VIN Requests are linked to users and store the results of keycode lookups.
Subscriptions track user plans, which are tied to specific service levels (e.g., free, premium).
Payments store billing information processed via Stripe.
Audit Logs record all significant actions for compliance and monitoring.
Entity-Relationship Diagram:
The ERD should depict relationships:

One-to-many relationships between Users and VIN Requests.
One-to-many between Users and Subscriptions, and Users and Payments.
VIN Requests and Keycodes are related for mapping VIN lookups.
You can use dbdiagram.io or a tool like Miro to visualize the relationships (see database schema).

3. User Flow
   The user flow encompasses the entire journey, from registration to accessing services. This section details how users interact with the platform based on their roles.

3.1 User Registration & Login
Registration: Users sign up by providing their email, setting a password, and selecting their membership tier.
The email is verified, and MFA (Multi-Factor Authentication) is enforced using Firebase Authentication.
Login: Users authenticate using their credentials. Once authenticated, they are redirected to their dashboard, where their role determines available actions.
3.2 Dashboard & VIN Lookup
Users log in and are presented with a dashboard based on their role (e.g., locksmith, admin).
VIN Lookup: Users submit a VIN, which is sent to the backend API to retrieve the keycode.
Free Users: May have limited access (e.g., a limited number of lookups).
Premium Users: Have unlimited VIN lookups and priority support.
3.3 Subscription and Payment Flow
Users can upgrade their plan through the dashboard, where payment is processed via Stripe.
Upon successful payment, the Subscriptions table is updated, and users gain immediate access to premium features.
4. Authentication and Roles
   4.1 Role-Based Access Control (RBAC)
   The application employs role-based access control to manage user permissions. Each user is assigned one of the following roles:

Locksmith: Limited to keycode lookups, VIN submissions, and access to basic support.
Admin: Full access to manage users, view system logs, and access all services.
Support Agent: Can assist users and troubleshoot issues but cannot perform admin-level actions.
The backend validates user roles for each request via Spring Security, ensuring that users only access data and services permitted by their role.

4.2 Authentication Flow
Login Process:

User credentials are sent to Firebase for verification.
Upon successful login, a JWT token is issued, containing user role information.
Spring Security validates the JWT token for every API request.
MFA Process: After initial login, MFA is enforced using Firebase, ensuring higher security for accessing sensitive data.

5. API Use
   The Keycode Help platform offers various REST APIs for interacting with the backend services. Below is a breakdown of the core APIs and their use.

5.1 Authentication API
POST /auth/login: Authenticate user credentials and generate JWT tokens.
POST /auth/register: Registers new users and stores the user data in the PostgreSQL database.
5.2 VIN Lookup API
POST /vin/lookup: Submits a VIN to the system and returns the corresponding keycode if available.
Rate Limiting: Free-tier users are limited in the number of lookups they can perform per day.
5.3 Subscription API
GET /subscriptions: Retrieves the user’s current subscription status.
POST /subscriptions/upgrade: Handles upgrading subscriptions, integrates with Stripe for payments.
5.4 Payment API
POST /payments: Initiates a payment process with Stripe and updates the Payments and Subscriptions tables upon success.
GET /payments/history: Retrieves payment history for the authenticated user.
6. Advanced API Needs and Design Logics
   6.1 Audit Logging
   Real-Time Logging: Every user interaction (e.g., VIN lookups, payments) is logged to Firestore in real-time. This data is available for compliance audits and troubleshooting.

GET /admin/audit-logs: Allows admins to retrieve the logs of user actions, providing a detailed history of service usage.

6.2 Caching and Optimization
Keycode Caching: Frequently requested VINs should be cached in memory to reduce database load and improve performance. Consider using Redis for caching frequently queried VINs.

Rate Limiting: APIs such as /vin/lookup and /auth/login must have rate limiting to prevent abuse and ensure system stability. This can be implemented at the API gateway level (e.g., Nginx, Spring Cloud Gateway).

6.3 Error Handling and Resilience
All APIs should return meaningful error codes (e.g., 404 for not found, 401 for unauthorized access).
Implement retries with exponential backoff for database connections and external API calls (e.g., Stripe payment processing) to ensure resilience.
7. API Design Considerations
   7.1 API Versioning
   All APIs should be versioned (e.g., /api/v1/auth/login) to allow for future upgrades without breaking existing clients.
   7.2 Security
   JWT Authentication: Every request to the API should include a JWT token for authentication, validated by Spring Security.
   Rate Limiting: Ensure protection against DDoS and brute-force attacks with rate limiting on sensitive endpoints like login and VIN lookup.
   7.3 Performance and Scalability
   Use Pagination for APIs that return large datasets (e.g., /admin/audit-logs).
   Optimize SQL queries to prevent N+1 query issues and slow database performance.
8. Design Logic for Frontend
   Next.js will use SSG (Static Site Generation) for the public-facing pages and SSR (Server-Side Rendering) for user dashboards and dynamic content.
   Global State Management will be handled by Redux, with React Query managing API responses and caching on the frontend.
   Material-UI will provide a responsive, accessible UI design system across the application.
9. Conclusion
   This document outlines the technical and architectural logic behind the Keycode Help application, including user flows, database interactions, role-based authentication, and API design. The goal is to provide a scalable, secure, and performant system that meets the needs of automotive locksmith professionals while ensuring ease of use and robust backend operations.

Collaborators should follow these guidelines to ensure consistent development and high code quality throughout the project.

10. Data Protection Logic
    Ensuring data privacy and security is crucial for the Keycode Help platform, particularly because the platform deals with sensitive information such as user credentials, VINs, keycodes, and payment details. Below are the key data protection strategies that will be implemented to safeguard both the data and the application.

10.1 Encryption
10.1.1 Data at Rest
User Passwords: Passwords will be hashed using a strong hashing algorithm like bcrypt before being stored in the database.
Hashing logic: Passwords are hashed using bcrypt with a cost factor (e.g., 12). No password is ever stored in plain text.
User Table Example:
sql
Copy code
CREATE TABLE users (
user_id SERIAL PRIMARY KEY,
email VARCHAR(255) UNIQUE NOT NULL,
password_hash VARCHAR(255) NOT NULL,  -- bcrypt-hashed password
...
);
Sensitive Data: Any personally identifiable information (PII) such as user addresses or VINs, if sensitive, will be encrypted using AES-256 encryption before being stored in the database.
Encryption logic: Data is encrypted using AES-256 before being written to the PostgreSQL database.
Keycode Table Example:
sql
Copy code
CREATE TABLE keycodes (
vin VARCHAR(17) PRIMARY KEY,
keycode VARCHAR(255) ENCRYPTED,  -- AES-256 encryption
...
);
10.1.2 Data in Transit
SSL/TLS: All data transmitted between the frontend (Next.js) and backend (Spring Boot API) will be secured using TLS 1.2/1.3 encryption to prevent eavesdropping or man-in-the-middle attacks.
Frontend: Ensure HTTPS is enforced on all public-facing endpoints.
Backend: All backend API requests must be served over HTTPS only.
10.1.3 Payment Data Protection
Stripe Integration: Payment data will never be stored directly on the Keycode Help platform. Instead, the platform integrates with Stripe, which handles all payment processing and securely stores sensitive payment details (such as credit card information).
PCI Compliance: As a PCI-compliant payment processor, Stripe ensures that all payment information is protected and handled according to industry standards.
Tokenization: The Keycode Help platform will only store tokens (e.g., stripe_payment_id) that refer to payment transactions within Stripe, ensuring no sensitive financial data is exposed.
10.2 Secure Authentication and Authorization
10.2.1 JWT Authentication
JWT Token: Once authenticated, the system will generate a JSON Web Token (JWT) that contains user information (such as user ID and roles). This token will be used for subsequent API requests to authenticate users.
Token Security:
JWTs will be signed with a secure HMAC SHA-256 algorithm, ensuring tokens are tamper-proof.
Expiration: JWTs will have a short expiration time (e.g., 1 hour) to minimize the risk of token theft.
Refresh Tokens: A refresh token mechanism will be implemented to allow users to stay logged in without having to re-authenticate frequently.
Token Storage: On the client-side, tokens will be stored securely in HttpOnly cookies to prevent Cross-Site Scripting (XSS) attacks.
10.2.2 Role-Based Access Control (RBAC)
User roles (e.g., locksmith, admin, support) will be embedded in the JWT and verified on every API request.
Access Control:
Spring Security will enforce access controls at the API level, ensuring that users can only perform actions allowed by their role.
Custom Annotations: Secure endpoints using custom annotations (e.g., @PreAuthorize("hasRole('ADMIN')")) to restrict access to certain resources based on the user’s role.
10.3 Data Masking and Minimization
10.3.1 VIN and Keycode Data Masking
Masking Logic: VIN numbers and keycodes are sensitive data and should not be displayed in their entirety in logs, responses, or UI elements unless necessary. For example, only the last four characters of the VIN or keycode will be shown in UI elements or logs.
Example:
java
Copy code
public String maskKeycode(String keycode) {
if (keycode.length() > 4) {
return "****" + keycode.substring(keycode.length() - 4);
}
return keycode;
}
10.3.2 Data Minimization
Only essential data for each operation should be collected, stored, and processed. For example:
VIN Requests: Only store the VIN, keycode, and lookup status (e.g., pending, completed). Avoid collecting unnecessary data (e.g., vehicle owner's name).
User Data: Collect the minimal set of personal data required for authentication and billing (email, hashed password, payment details via Stripe). Avoid storing redundant or sensitive data not required for the platform's functionality.
10.4 API Security and Rate Limiting
10.4.1 API 
Rate Limiting Logic: Rate limiting will be enforced on sensitive endpoints (such as login and VIN lookup) to prevent brute-force attacks and ensure fair use of resources.
Example: Limit VIN lookups to 100 requests per day for free-tier users, and higher limits for paid-tier users.
Implementation: Use rate-limiting middleware (e.g., Spring Cloud Gateway or Nginx) to track API request counts per user or IP address.
10.4.2 API Gateway and Throttling
API Gateway: Implement an API gateway (such as Spring Cloud Gateway) to enforce security measures like rate limiting, logging, and monitoring across all API services.
Throttling Logic: Slow down requests to log in or VIN lookup endpoints after repeated failed attempts, using exponential backoff to prevent brute force attempts.
10.5 Backup and Disaster Recovery
10.5.1 Regular Database Backups
Automated Backups: PostgresSQL databases will be backed up daily with encrypted backups stored in Google Cloud Storage (GCS) or another secure backup solution.
Backup Frequency: Full backups will be taken daily, with incremental backups every 6 hours.
Data Retention: Backup data will be retained for 30 days, after which older backups will be purged.
10.5.2 Disaster Recovery Plan
In case of system failure, a disaster recovery plan will be in place to restore the platform within a defined Recovery Time Objective (RTO) and Recovery Point Objective (RPO).
Recovery Time Objective (RTO): Target recovery time within 1 hour after a system failure.
Recovery Point Objective (RPO): Max allowable data loss is 15 minutes, meaning backups and transaction logs will be synced every 15 minutes.
10.6 Compliance and Privacy
10.6.1 GDPR and Data Privacy
User Rights: In compliance with GDPR, users will have the right to request access to their personal data, request data deletion (Right to be Forgotten), and request correction of inaccurate data.
Data Retention Policy: Personal data will only be stored for as long as necessary to fulfill the purposes for which it was collected, in line with data minimization principles.
10.6.2 Auditing and Logs
Audit Logs: All actions (e.g., VIN lookups, subscription changes, payments) will be logged in Firestore and accessible only to admins. Logs will include metadata such as timestamps, user IDs, and actions taken.
Immutable Logs: Logs should be immutable and tamper-proof, ensuring that no one can modify or delete logs without authorization.
10.7 Monitoring and Alerts
Real-Time Monitoring: Use Google Cloud Monitoring and Firebase Analytics to monitor system health and user behavior.
Security Alerts: Set up real-time alerts for suspicious activity, such as failed login attempts, unusual API usage, or potential data breaches.
11. Conclusion
    By implementing strong data protection strategies, Keycode Help ensures the security of user data and compliance with industry standards. These measures — from encryption and secure authentication to API protection and disaster recovery — provide a robust foundation for safeguarding sensitive information and maintaining user trust.
