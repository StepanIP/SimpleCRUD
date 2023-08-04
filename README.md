# Simple CRUD for Jewelry Shop Automation

## Description

SimpleCRUD is a Java web application designed for jewelry shop automation, enabling users to perform CRUD (Create, Read, Update, Delete) operations on a database. The project utilizes Servlets for handling HTTP requests and responses, JDBC for database connectivity, and Java 15 for core logic. Its primary purpose is to provide an intuitive and interactive user interface for efficient data management.

## Technologies Used

- Java 15
- Servlets
- JDBC (Java Database Connectivity)
- Gradle
- Lombok

## Design Patterns Used

- Builder
- Singleton
- Factory Method
- Proxy
- Object Pool
- Model-View-Controller (MVC)
- Template Method

## Database Tables

- cart
- cart_product (junction table)
- category
- category_product (junction table)
- detail_information (junction table)
- orders
- payment
- product
- promotions
- reviews
- suppliers
- tags
- tags_product (junction table)
- user
- wishlist

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 15 or above installed
- Gradle installed

### Installation

1. Clone or download the project from GitHub.
2. Set up the database and configure connection parameters in the application.
3. Run the following command to build the project:
4. Run the application using a web server (e.g., Tomcat, Jetty).

### Usage

1. Access the application through your web browser by navigating to the appropriate URL.
2. Use the user-friendly interface to manage data in the database.
3. Perform CRUD operations on different tables.

The primary goal of this project is to learn and apply various design patterns, and it successfully implements the following patterns: builder, singleton, factory method, proxy, object pool, MVC, and template method. Additionally, the project incorporates Lombok for simplifying Java code and reducing boilerplate.



