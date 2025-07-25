# E-Commerce Microservices Project Documentation

## Table of Contents
1. [Overview](#overview)
2. [Architecture](#architecture)
3. [Services](#services)
4. [Technical Stack](#technical-stack)
5. [Setup and Installation](#setup-and-installation)
6. [API Documentation](#api-documentation)
7. [Configuration](#configuration)
8. [Deployment](#deployment)

## Overview
This project is a microservices-based e-commerce platform built using Spring Cloud. It provides a scalable and resilient architecture for handling e-commerce operations such as product management, user management, and order processing.

## Architecture

                        ┌────────────────────┐
                        │   Config Server    │
                        └────────┬───────────┘
                                 │
                        ┌────────▼────────┐
                        │  Eureka Server   │
                        └────────┬────────┘
                                 │
                        ┌────────▼────────┐
                        │  API Gateway     │
                        └────────┬────────┘
           ┌─────────────────────┼─────────────────────┐
           │                     │                     │
     ┌─────▼─────┐         ┌─────▼─────┐         ┌─────▼─────┐
     │ Product   │         │  User     │         │  Order     │
     │ Service   │         │  Service  │         │  Service   │
     └───────────┘         └───────────┘         └───────────┘
                                      │
                               ┌──────▼───────┐
                               │ Additional   │
                               │  Services    │
                               └──────────────┘


## Services

### 1. Gateway Service
- **Port**: 8080
- **Description**: API Gateway that handles routing and cross-cutting concerns
- **Features**:
    - Circuit breaker implementation using Resilience4j
    - Route configuration for all microservices
    - Load balancing
    - Path rewriting
    - Eureka client integration

### 2. Service Registry (Eureka Server)
- **Port**: 8761
- **Description**: Service discovery server
- **Features**:
    - Service registration
    - Service discovery
    - Health monitoring

### 3. Config Server
- **Port**: 8888
- **Description**: Centralized configuration management
- **Features**:
    - External configuration management
    - Environment-specific configurations
    - Dynamic configuration updates

### 4. Product Service
- **Description**: Manages product-related operations
- **Endpoints**:
    - `/api/products/**`

### 5. User Service
- **Description**: Handles user management and authentication
- **Endpoints**:
    - `/api/users/**`

### 6. Order Service
- **Description**: Manages orders and shopping cart
- **Endpoints**:
    - `/api/orders/**`
    - `/api/cart/**`

## Technical Stack
- **Java Version**: 24
- **Framework**: Spring Boot, Spring Cloud
- **Service Discovery**: Eureka
- **Gateway**: Spring Cloud Gateway
- **Circuit Breaker**: Resilience4j
- **Build Tool**: Maven/Gradle
- **Containerization**: Docker

## Setup and Installation

### Prerequisites
- JDK 24
- Docker and Docker Compose
- Maven/Gradle

### Running the Application

1. Start the Config Server:
2. Start the Eureka Server:
3. Start the Gateway:
4. Start other services:

### Docker Deployment
Use the provided `docker-compose.yml` to run all services:


## Configuration

### Gateway Configuration


### Circuit Breaker Monitoring
- Health indicators are registered for circuit breakers
- Metrics are available through actuator endpoints
- Circuit breaker states are exposed via health endpoints

## Security Considerations
1. Implement authentication and authorization
2. Secure sensitive endpoints
3. Use HTTPS in production
4. Implement rate limiting
5. Set up proper CORS configuration

## Best Practices
1. Use circuit breakers for external service calls
2. Implement proper fallback mechanisms
3. Monitor service health and metrics
4. Use proper logging and tracing
5. Implement proper error handling
6. Use environment-specific configurations

This documentation provides a high-level overview of the project structure and setup. Each service may have additional specific configurations and requirements that should be documented separately.
