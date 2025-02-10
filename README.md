# Pet Store API Automation Framework

## Overview
This is a REST API automation testing framework for the Pet Store API (https://petstore.swagger.io/). The framework is built using Java with TestNG and implements various best practices for API testing.

## API Endpoints

### Pet Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /pet | Add a new pet to the store |
| PUT | /pet | Update an existing pet |
| GET | /pet/findByStatus | Find pets by status (available, pending, sold) |
| GET | /pet/{petId} | Find pet by ID |
| POST | /pet/{petId} | Updates a pet in the store with form data |
| DELETE | /pet/{petId} | Deletes a pet |
| POST | /pet/{petId}/uploadImage | Uploads an image for a pet |


## Key Features
- **Environment Configuration**: Supports multiple environments (test, int, stg) through property files
- **Reporting**:
    - Allure Reports integration
    - Extent Reports with customizable themes
    - Automatic report opening after test execution
- **Test Data Generation**: Uses JavaFaker for dynamic test data creation
- **Retry Mechanism**: Supports retry for failed test cases
- **Custom Annotations**: Framework annotations for better test organization
- **Categories**: Test categorization (BVT, SANITY, REGRESSION, NEGATIVE)

## Configuration
The framework supports various configurations through property files:
- `override_reports`: Controls report file naming (yes/no)
- `request_response_details_in_reports`: Includes API request details in reports (yes/no)
- `retry_failed_tests`: Enables test retry mechanism (yes/no)
- `base_uri_api`: Base URL for API endpoints

## Test Categories
- BVT (Build Verification Tests)
- Sanity Tests
- Regression Tests
- Negative Tests

## Requirements
- Java 8 or higher
- Maven
- TestNG plugin for IDE
## Test Coverage
The framework includes tests for the following Pet Store API operations:
- Create Pet
- Update Pet
- Delete Pet
- Get Pet Details
- Validation Tests
    - Invalid Status
    - Invalid ID
    - Empty Name
    - Missing Required Fields

## Reports
Reports are generated in:
- `ExtentReports/`: Extent HTML reports
- `allure-results/`: Allure report data

## Best Practices Implemented
- Builder Pattern for test data creation
- Page Object Model adapted for API testing
- Separation of concerns (test data, configuration, test logic)
- Proper exception handling
- Comprehensive logging
- Clean code principles
- Reusable components
