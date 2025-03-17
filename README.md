# Notice PDF Generation System

## Overview
The **Notice PDF Generation System** is a Spring Boot application that allows users to create notices, associate them with templates, and generate PDF documents asynchronously. The application supports high-load environments for performance optimization.

## Features
- Create and manage notices and templates.
- Generate PDF notices asynchronously.
- Handle exceptions globally using `@RestControllerAdvice`.

## Technologies Used
| Technology | Purpose                   |
|------------|---------------------------|
| **Spring Boot** | REST APIs                 |
| **Lombok** | Reducing boilerplate code |
| **MySQL** | Database                  |
| **OpenHTMLToPDF** | PDF Generation            |


## Architecture
The system follows a layered architecture:
1. **Controller Layer**: Handles API requests (`NoticeController`, `PdfController`, `NoticeTemplateController`).
2. **Service Layer**: Contains business logic (`NoticeService`, `PdfService`, `TemplateService`, `NoticeTemplateService`, `PdfGeneratorService`).
3. **Repository Layer**: Interfaces with the database using JPA (`NoticeRepository`, `NoticeTemplateRepository`).
4. **Exception Handling**: Centralized exception handling (`GlobalExceptionHandler`).

## Setup and Installation
### Prerequisites
- JDK 17+
- Maven 3+
- MySQL (or any compatible database)

### Steps to Run
1. Clone the repository:
   ```sh
   git clone https://github.com/vrohit939/notice-pdf-generator.git
   cd notice-pdf-generator
   ```
2. Configure the database in `application.yml`:
   ```properties
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/notice_db
       username: root
       password: yourpassword
     jpa
       hibernate:
         ddl-auto: update
   ```
3. Build and run the application:
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```
4. Access APIs via Postman or cURL.

## API Sequence Diagram
For detailed sequence diagrams, refer to [API Sequence Diagrams](docs/API_SEQUENCE_DIAGRAMS.md).

## API Documentation

### 1. Create Notice Template
**Endpoint:**
```http
POST /api/v1/templates
```

**Description:** Creates a new notice template.

**Request Body:**
```json
{
  "templateName": "Payment Due Notice",
  "htmlContent": "<h1>Payment Due</h1><p>Dear [[${name}]], your payment is due on [[${dueDate}]].</p>"
}
```
**Response:**
```json
{
  "id": 1,
  "templateName": "Payment Due Notice",
  "htmlContent": "<h1>Payment Due</h1><p>Dear [[${name}]], your payment is due on [[${dueDate}]].</p>"
}
```

### 2. Create Notice
**Endpoint:**
```http
POST /api/v1/notices
```
**Description:** Creates a new notice.

**Request Body:**
```json
{
  "recipientName": "John Doe",
  "referenceNumber": "123456",
  "address": "123 Street, City",
  "phone": "9876543210",
  "dueDate": "2025-04-01",
  "template": {
    "id": 1
  }
}
```
**Response:**
```json
{
  "id": 1,
  "recipientName": "John Doe",
  "address": "123 Street, City",
  "phone": "9876543210",
  "referenceNumber": "123456",
  "dueDate": "2025-04-01",
  "template": {
    "id": 1,
    "templateName": "Payment Due Notice"
  }
}
```


### 3. Generate Notice PDF
**Endpoint:**
```http
GET /api/v1/pdf/generate/{noticeId}
```
**Description:** Generates a PDF file for the given `noticeId` and returns it as a downloadable attachment.

**Request Example:**
```sh
curl -X GET "http://localhost:8080/api/v1/pdf/generate/1" -H "Accept: application/pdf" -o notice_1.pdf
```
**Response:**
- **Content Type:** `application/pdf`
- **Headers:**
  ```http
  Content-Disposition: attachment; filename=notice_1.pdf
  Content-Type: application/pdf
  ```
- **Body:** Binary PDF file (Downloadable)

## Error Handling
This API follows a structured error-handling approach using `@RestControllerAdvice`.

Example error response:
```json
{
  "status": 404,
  "message": "Notice not found for ID: 1",
  "path": "/api/v1/pdf/generate/1"
}
```

## Exception Handling
The system uses `GlobalExceptionHandler` to manage exceptions:
- `NoticeNotFoundException` → 404 NOT FOUND
- `BadRequestException` → 400 BAD REQUEST
- `PdfGenerationException` → 500 INTERNAL SERVER ERROR

