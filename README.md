# Instruction

## ERD Diagram
### Tables and Relationships

#### Users
- **Primary Key (PK)**: `user_id`
- **Attributes**:
  - `email`
  - `password`
  - `profile_image`

#### Otps
- **Primary Key (PK)**: `otp_id`
- **Attributes**:
  - `otp_code`
  - `issued_at`
  - `expiration`
  - `verify`
- **Foreign Key (FK)**: `user_id` (references `Users.user_id`)

#### Categories
- **Primary Key (PK)**: `category_id`
- **Attributes**:
  - `name`
  - `description`
- **Foreign Key (FK)**: `user_id` (references `Users.user_id`)

#### Expenses
- **Primary Key (PK)**: `expense_id`
- **Attributes**:
  - `amount`
  - `description`
  - `date`
- **Foreign Keys (FK)**:
  - `user_id` (references `Users.user_id`)
  - `category_id` (references `Categories.category_id`)

### Relationships
- **Users to Otps**: One-to-Many (A user can have multiple OTPs).
- **Users to Categories**: One-to-Many (A user can have multiple categories).
- **Users to Expenses**: One-to-Many (A user can have multiple expenses).
- **Categories to Expenses**: One-to-Many (Each category can be associated with multiple expenses).

## Building the RESTful API
There are four main RestControllers:
1. **Authentication RestController**
2. **Category RestController**
3. **Expense RestController**
4. **FileUpload RestController**

## Bonus

### Using UUID

### Objective
- Understanding CRUD with Rest Controllers using MyBatis
- JWT Authentication
- Exception Handler
- File Upload
- Thymeleaf
