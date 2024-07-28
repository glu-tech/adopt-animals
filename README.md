# Adoption Animals Project

## Overview

The Adoption Animals project is a Spring Boot application that manages animal adoption processes. It supports creating, listing, retrieving, and updating animal records, including uploading images to AWS S3.

## Installation and Setup

### Prerequisites

- **Java Development Kit (JDK)**: Ensure you have JDK 11 or above installed.
- **Gradle**: Ensure you have Gradle installed. You can use the Gradle Wrapper provided with the project.
- **Amazon Web Services (AWS)**: Ensure you have AWS credentials and access to an S3 bucket.
- **PostgreSQL**: Ensure you have PostgreSQL and create database animalAdopt

### Clone the Repository

Clone the repository to your local machine:

```bash
git clone [https://your-repo-url](https://github.com/glu-tech/adopt-animals).git
cd adopt-animals
```

## Configure application.properties
### Database Configuration: Configure your database settings in src/main/resources/application.properties:
```bash
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.url=jdbc:postgresql://localhost:5432/animalAdopt
```

## Configure S3Config
### AWS Configuration: Configure your AWS account in src/main/java/com.glutech.adoptanimals/config/S3Config.java:
```bash
BasicAWSCredentials awsCreds = new BasicAWSCredentials("YOUR_ACCESS_KEY", "YOUR_SECRET_KEY");
```

## Build the project
### Use Gradle to build the project:
```bash
./gradlew build
```

## Run the Application
### Start the Spring Boot application:
```bash
./gradlew bootRun
```
The application will start on http://localhost:8080. And the Swagger run in http://localhost:8080/swagger-ui/index.html

## Testing

### Unit Tests
Unit tests are located in the src/test/java/com/glutech/adoptanimals/service directory. To run the tests, use Gradle:
```bash
./gradlew test
```

### Create Animal Success
Description: Tests successful creation of an animal with an image.
Test Method: createAnimal_Success

### List Animals Success
Description: Tests successful listing of animals with pagination.
Test Method: listAnimals_Success

### Get Animal Success
Description: Tests successful retrieval of an animal by ID.
Test Method: getAnimal_Success

### Get Animal Not Found
Description: Tests retrieval of a non-existing animal.
Test Method: getAnimal_NotFound

### Change Status Success
Description: Tests successful status change of an animal.
Test Method: changeStatus_Success

### Change Status Animal Not Found
Description: Tests status change for a non-existing animal.
Test Method: changeStatus_AnimalNotFound

## Conclusion
This documentation provides a comprehensive guide to installing, configuring, running, and testing the Adoption Animals project. For further details or troubleshooting, refer to the Spring Boot documentation and AWS S3 documentation.
