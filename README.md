# E-commerce Order Management API

A beginner learning project that provides REST APIs for managing products and customer orders. It uses Spring Boot and PostgreSQL.

## What the project does

- Creates, lists, views, updates, and deletes products.
- Places customer orders for products.
- Checks available stock before accepting an order.
- Reduces stock when an order is placed.
- Validates product and order input.
- Returns useful error responses for invalid requests and missing records.
- Provides interactive API documentation through Swagger UI.

## Technology used

- **Java 25** — the programming language used for the application.
- **Spring Boot 4.1.2** — starts the application and provides its web and data features.
- **Maven** — downloads project libraries and builds and runs the application.
- **Spring Data JPA and Hibernate** — connect Java objects and database operations.
- **PostgreSQL 18.6** — stores products and orders.
- **Swagger UI (Springdoc OpenAPI)** — displays and lets you try the API endpoints.
- **Visual Studio Code** — the code editor used during development.
- **Git and GitHub** — track and store the project code.

## How a request moves through the project

```text
Browser or Swagger
        ↓
Controller — receives the API request
        ↓
Service — applies the business rules
        ↓
Repository — reads or saves database records
        ↓
Hibernate / JPA
        ↓
PostgreSQL
```

For example, when a customer places an order, the order controller receives the request, the order service checks stock and calculates the total, and the repositories save the order and updated stock.

## Main project folders

```text
src/main/java/com/example/order_management/
├── controller/  Receives HTTP requests and returns responses
├── dto/         Request and response data shapes
├── entity/      Java objects stored as database tables
├── exception/   Custom errors and API error handling
├── repository/  Database access interfaces
└── service/     Product and order business rules

src/main/resources/
└── application.properties  Application and database configuration

src/test/java/
└── Automated tests
```

## API endpoints

The application uses `http://localhost:8080` as its local address.

| Method | Endpoint | Purpose |
|---|---|---|
| `GET` | `/api/hello` | Check that the API is running |
| `GET` | `/api/products` | List all products |
| `GET` | `/api/products/{id}` | View one product |
| `POST` | `/api/products` | Create a product |
| `PUT` | `/api/products/{id}` | Update a product |
| `DELETE` | `/api/products/{id}` | Delete a product |
| `POST` | `/api/orders` | Place an order |
| `GET` | `/api/orders` | List all orders |
| `GET` | `/api/orders/{id}` | View one order |

`{id}` is a placeholder for a record's ID. For example, `/api/products/2` requests product ID 2.

## Example: create a product

Send a `POST` request to `/api/products` with this JSON body:

```json
{
  "name": "Mouse",
  "price": 799.00,
  "stock": 15
}
```

Product names are required, prices must be greater than zero, and stock cannot be negative.

## Example: place an order

Send a `POST` request to `/api/orders` with this JSON body:

```json
{
  "customerName": "Kavin",
  "items": [
    {
      "productId": 3,
      "quantity": 1
    }
  ]
}
```

The product ID must refer to an existing product. The quantity must be at least 1 and cannot exceed the available stock. The order total is calculated from the product price and quantity.

Each successful `POST /api/orders` request creates a new order and updates stock. Use `GET` endpoints when you only want to view data.

## Error responses

The API uses HTTP status codes to describe the result:

- **400 Bad Request** — the submitted data did not pass validation.
- **404 Not Found** — the requested product or order does not exist.
- **409 Conflict** — there is not enough stock for the requested order.
- **201 Created** — a new product or order was created successfully.

Errors are returned as JSON with details such as the status, message, and request path.

## Run the application on Windows

### Requirements

- Java installed.
- PostgreSQL installed and running.
- A PostgreSQL database named `order_management`.

Create the database in pgAdmin or with PostgreSQL tools if it does not already exist.

The database password is read from the `DB_PASSWORD` environment variable. Set it in PowerShell if it is not already configured for your terminal:

```powershell
$env:DB_PASSWORD = "YOUR_POSTGRES_PASSWORD"
```

Replace `YOUR_POSTGRES_PASSWORD` with your PostgreSQL password. Keep real passwords out of source files and GitHub.

From the project folder, start the application:

```powershell
.\mvnw.cmd spring-boot:run
```

Wait for the log message:

```text
Started OrderManagementApplication
```

The API is then available at `http://localhost:8080`.

## Swagger UI

With the application running, open:

```text
http://localhost:8080/swagger-ui.html
```

Swagger UI shows the available endpoints. Expand an endpoint, click **Try it out**, fill in any required values, and click **Execute** to send a request.

Be aware that executing a `POST` request creates a real record in the local database. Executing it multiple times creates multiple records.

## Run the automated tests

From the project folder, run:

```powershell
.\mvnw.cmd test
```

The tests check that the Spring application context starts and that product request validation accepts valid data and rejects invalid data.

## How the project was built

1. Created a Spring Boot project with Spring Initializr.
2. Connected the application to PostgreSQL.
3. Created the product entity, repository, service, and controller.
4. Added product input validation and consistent API error responses.
5. Created order entities, a repository, service, and controller.
6. Added stock checks, order total calculation, and stock reduction.
7. Tried the API with PowerShell and Swagger UI.
8. Added automated tests for product request validation.
9. Used Git to commit the code and GitHub to store the project.

## Current scope

This is a learning project. It currently focuses on core product and order operations. Features such as user login, payment processing, and deployment to a public server have not been added.