# pet-adoption-ui

Vaadin + Spring Boot application for managing a simple pet adoption flow using server-side UI and MySQL persistence.

## Overview

This project explores building web applications using Vaadin Flow, where the UI is fully defined and managed on the server.

Unlike client-heavy approaches (React, Angular), this model keeps state and interaction logic in Java, with automatic synchronization between server and browser.

The application implements a basic pet adoption workflow, including listing animals, managing their data, and handling contact requests.

## Features

* Animal listing with search by name
* Create, edit and delete animals
* View detailed information for each animal
* Contact/adoption request flow
* Modal dialogs for confirmation and feedback
* Persistent storage using MySQL and JPA

## Tech stack

* Java 21
* Spring Boot 4
* Vaadin Flow
* Spring Data JPA
* MySQL
* Docker & Docker Compose
* Maven

## Architecture

The project follows a simple layered structure:

* `entity` — JPA entities
* `repository` — data access
* `service` — business logic
* `ui` — Vaadin views and components

The focus is on clarity and separation of concerns without over-engineering.

## Running locally

### 1. Start the database

```bash
docker compose up -d
```

### 2. Run the application

```bash
./mvnw spring-boot:run
```

### 3. Access the app

http://localhost:8080

## Notes

* This is a focused exploration of server-side UI using Vaadin
* The project prioritizes simplicity and clarity over advanced architecture
* Validation is implemented manually to keep control over UI behavior

## Purpose

This repository exists to evaluate the server-driven UI model and its trade-offs compared to modern client-side frameworks.

It is not intended as a production-ready system, but as a practical exploration of how far this approach can go for real-world applications.
