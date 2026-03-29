# vaadin-lab

Experimental Vaadin + Spring Boot project exploring server-side UI development as an alternative to client-heavy frontend architectures.

## Overview

This project explores a different approach to building web UIs using Vaadin Flow, where the UI is defined and managed entirely on the server.

Instead of relying on client-side frameworks (React, Angular, etc.), Vaadin allows building interactive applications using Java components, with automatic client-server synchronization.

## Context

Coming from a frontend-heavy background, this lab focuses on understanding:

* Trade-offs between server-side UI and client-side rendering
* State management handled on the server vs in the browser
* Developer experience compared to modern frontend stacks
* How far this model can go for real applications

## Tech stack

* Java 21
* Spring Boot 4
* Vaadin Flow
* Maven

## Current features

* Simple UI composition using Vaadin components
* Basic search interaction
* Modal dialog with form input
* In-memory data handling

## Run locally

```bash
./mvnw spring-boot:run
```

Open:

http://localhost:8080

## Notes

* This is an experimental project
* Focused on architectural exploration, not production concerns
* Data is not persisted