# AuthApp

AuthApp is a Spring Boot-based authentication and authorization application that provides secure access control for web applications. It supports JWT (JSON Web Token) for authentication and role-based access control.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Contributing](#contributing)
- [License](#license)

## Features

- **User  Registration**: Allows new users to register with a username, name, and password.
- **User  Login**: Authenticates users and provides a JWT token for subsequent requests.
- **Role Management**: Supports role-based access control with different permissions for users and admins.
- **Secure Password Storage**: Passwords are securely hashed using BCrypt.
- **CORS Support**: Configured to allow requests from specified origins (e.g., Angular app).
- **JWT Authentication**: Uses JWT for stateless authentication, ensuring secure communication.
- **Profile Management**: Users can view and update their profiles.

## Technologies Used

- **Java**: Programming language used for the backend.
- **Spring Boot**: Framework for building the application.
- **Spring Security**: Provides authentication and authorization.
- **JPA/Hibernate**: For database interactions.
- **MySQL**: Database for storing user and role information.
- **Lombok**: To reduce boilerplate code.
- **JWT**: For token-based authentication.

## Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/authapp.git
   cd authapp

2. **Set up the database**:

Create a MySQL database and update the application.properties file with your database credentials.
Build the project:
Copy code
./mvnw clean install

3. **Run the application**:

Open In Editor
Edit
Copy code
./mvnw spring-boot:run

4. **Usage**
The application runs on ****http://localhost:8080**** by default.
Use tools like Postman or curl to interact with the API endpoints.


5 **API Endpoints**

1. ***Authentication***
   
  ****a. POST /auth/register****

Register a new user.
Request Body:
{
  "userName": "string",
  "name": "string",
  "password": "string",
  "roles": [{"roleName": "string"}]
}

  ****b. POST /auth/login****

Authenticate a user and return a JWT token.
Request Body:
{
  "username": "string",
  "password": "string"
}


***2. User Management***

  ****a. GET /users/profile****
  

Get the authenticated user's profile.
POST /users/update

{
  "updatedData": "string"
}

***3. Role Management***

  ****a. POST /roles/create****
  
Create a new role.
Request Body:
{
  "roleName": "string"
}


***4. Admin Management.***

    ****a. GET /admin/dashboard****
    
Access the admin dashboard (protected route).


**Contributing**
Contributions are welcome! Please feel free to submit a pull request or open an issue for any suggestions or improvements.
