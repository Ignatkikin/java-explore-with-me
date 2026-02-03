# Explore With Me — Event Management REST API

## Tech stack
- Java 21
- Spring Boot
- Spring Data JPA (Hibernate)
- PostgreSQL
- Maven
- Docker / Docker Compose

## Description
This project is an event management platform where users can create events, 
browse published events, send participation requests, and leave comments.
The system includes event moderation, event states, and a separate statistics service 
for tracking event views.

## Architecture
- Main Service — core business logic and REST API
- Stats Service — collects and provides event view statistics
- REST communication between services

## Features
- CRUD operations for events, categories, and users
- Event moderation and publishing workflow
- Management of participation requests
- Viewing statistics via a separate service
- Data validation and error handling

