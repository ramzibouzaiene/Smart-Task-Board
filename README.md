# Smart Task Board

## Overview
Smart Task Board is a full-stack task management application designed to enhance team collaboration. It allows users to manage projects, create and assign tasks, comment on tasks, and receive real-time notifications.

## Features
- **User Authentication & Authorization** :Secure user login and registration with JWT-based authentication and role-based access control.

- **Project Management** :Create, update, and delete projects. Each project has an owner, a list of tasks, and assigned team members.

- **Task Management** : Create, update, and delete tasks. Tasks include title, description, status, due date, and priority. Tasks can be assigned to one or more users.

- **Commenting System** : Enable users to add comments on tasks for enhanced collaboration.

- **Role-Based Access Control (RBAC)** – Secure endpoints with role-based access.

- **Notifications** : Receive notifications on task assignments, updates, and new comments. Real-time notifications can be integrated using WebSockets.

## Tech Stack
### Frontend:
- **React (TypeScript)**

### Backend:
- **Java**
- **Spring Boot**
- **Spring WebSockets**
- **JWT (JSON Web Tokens)**
- **Spring Security**
- **Swagger API** 

### Database:
- **PostgreSQL**

### Architecture:
- **Controller Layer** – Exposes RESTful API endpoints to manage users, projects, tasks, comments, and notifications.
- **Service Layer** – Contains business logic for task assignment, comment management, and project updates.
- **Repository Layer** – Handles data persistence using Spring Data JPA.
- **Domain Model** – Represents core entities like `User`, `Project`, `Task`, `Comment`, and `Notification`.
- **Notification System** – Event-driven architecture that triggers notifications for actions like task assignments and comments, with real-time updates via WebSockets.
