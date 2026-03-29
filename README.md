# vaadin-lab

Hands-on Vaadin + Spring Boot lab focused on server-side UI development.

## What is this?

This repository is a personal lab used to explore how to build web UIs using Vaadin Flow and Java.

The goal is to understand component-based UI, event handling, and form interactions without relying on frontend frameworks.

## Current features

* Simple animal listing screen
* Search by name
* Modal dialog to create new animals
* Basic form handling
* In-memory data storage (no persistence)

## Tech stack

* Java 21
* Spring Boot 4
* Vaadin 25
* Maven

## Run locally

```bash
./mvnw spring-boot:run
```

Open:

```
http://localhost:8080
```

## Notes

* This is a learning project
* Data is stored in memory (lost on restart)
* Not production-ready
