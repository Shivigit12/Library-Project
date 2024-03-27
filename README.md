## Library Project – Detailed Documentation

### 1. High‑level overview

**Purpose**: This is a Spring Boot 3 application that models a simple library management system. It lets you:
- **Manage books**: create, list, search, and delete books.
- **Manage students**: create, view, update, and delete students.
- **Track authors**: store authors and automatically reuse existing authors when adding books.
- **Issue and return books**: associate books with students through transactions, and compute fines for late returns.

The application exposes **REST APIs** and uses **Spring Data JPA** with **PostgreSQL** as the main database (and a MySQL driver is present but not used in `application.properties`).

---

### 2. Tech stack and dependencies

- **Language & runtime**
  - **Java 17** (`maven.compiler.source/target`).
- **Framework**
  - **Spring Boot 3.2.0** (`org.springframework.boot:spring-boot-starter-parent`).
  - **spring-boot-starter-web** – REST controllers and web stack.
  - **spring-boot-starter-data-jpa** – JPA/Hibernate integration for database access.
  - **spring-boot-starter-aop** + **aspectjweaver** – for cross‑cutting concerns (currently not heavily used in the visible code).
  - **spring-boot-starter-validation** – bean validation annotations (e.g. `@NotBlank` in DTOs).
- **Persistence**
  - **PostgreSQL** JDBC driver (`org.postgresql:postgresql`, runtime scope).
  - **Spring Data JPA** repositories (`JpaRepository` interfaces).
  - **Hibernate** as the JPA provider.
- **Other libraries**
  - **Lombok** (builder, getters/setters, constructors).
  - **log4j 1.2.17** (legacy logging library, declared as a dependency).
- **Build**
  - Maven with **spring-boot-maven-plugin** for packaging and running.

Entry point:
- `org.example.Main` annotated with `@SpringBootApplication`, which boots the entire app.

---

### 3. Application configuration

**File**: `src/main/resources/application.properties`

- **Database (PostgreSQL)**:
  - `spring.datasource.url=jdbc:postgresql://localhost:5432/library_project`
  - `spring.datasource.username=postgres`
  - `spring.datasource.password=Localhost@123`
  - `spring.datasource.driver-class-name=org.postgresql.Driver`
- **JPA/Hibernate**:
  - `spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect`
  - `spring.jpa.hibernate.ddl-auto=create`  
    - On each application start, Hibernate **drops and recreates** tables (good for dev, destructive for prod).
  - `spring.jpa.show-sql=true` and `spring.jpa.properties.hibernate.format_sql=true` – logs SQL statements.
- **Custom business configuration**:
  - `student.issue.max_books=3` – maximum books a student can have issued at once.
  - `student.issue.number_of_days=15` – allowed days before fine calculation starts for a borrowed book.

These custom properties are injected into `TransactionService` with `@Value`.

---

### 4. Layered architecture

The project follows a **typical Spring layered architecture**:

- **Controller layer (`org.example.controller`)**
  - Receives HTTP requests.
  - Performs simple validations (null checks, ID range checks).
  - Delegates business logic to services.
  - Returns `ResponseEntity` or domain objects directly.

- **Service layer (`org.example.service`)**
  - Contains business logic for:
    - Books (`BookService`)
    - Students (`StudentService`)
    - Authors (`AuthorService`)
    - Transactions (issuing/returning books) (`TransactionService`)
  - Uses repositories to persist and retrieve entities.

- **Repository layer (`org.example.repository`)**
  - Spring Data `JpaRepository` interfaces for:
    - `BookRepository`
    - `StudentRepository`
    - `AuthorRepository`
    - `TransactionRepository`
  - Uses method naming conventions and custom `@Query` annotations to define queries.

- **Domain / Entity layer (`org.example.entity`)**
  - JPA entities:
    - `Book`, `Student`, `Author`, `Card`, `Transaction`
  - Enums:
    - `Genre`, `CardStatus`, `TransactionStatus`, `TransactionType`
  - ORM relationships between entities (e.g. `@ManyToOne`, `@OneToMany`, `@OneToOne`).

- **DTOs (`org.example.dto`)**
  - **CreateBookRequest** – payload to create books.
  - **CreateStudentRequest** – payload to create students.
  - **SearchBookRequest** – payload to search books by different keys/operators.
  - DTOs typically offer a `to()` method to map from DTO to entity.

- **Error handling (`org.example.controller.ExceptionControllerAdvice`, `org.example.exception`, `org.example.utility.ErrorInfo`)**
  - Centralized exception handling using `@RestControllerAdvice`.
  - Custom base exception `BaseException` with an `errorCode`.
  - Specific exceptions: `BookNotFoundException`, `CardNotFoundException`, `TransactionException`.
  - `ErrorInfo` DTO for consistent error responses.

---

### 5. Domain model (entities and relationships)

#### 5.1 Book

**Class**: `org.example.entity.Book`

- Fields:
  - `id` (primary key, auto‑generated).
  - `name`
  - `author` – `@ManyToOne` to `Author`.
  - `student` – `@ManyToOne` to `Student` (who currently holds the book).
  - `pages`
  - `language`
  - `available` (boolean flag; not yet fully used in logic).
  - `genre` – `@Enumerated(EnumType.STRING)` with values from `Genre` (FICTIONAL, HISTORY, etc.).
  - `ISBNNumber`
  - `createdOn`, `updatedOn` – automatically managed timestamps.
  - `transactions` – `@OneToMany(mappedBy = "book")` (history of issue/return transactions).
  - `card` – `@ManyToOne` link to a `Card`.

This entity captures both static properties (title, genre) and dynamic associations (which student/card is holding it, historical transactions).

#### 5.2 Student

**Class**: `org.example.entity.Student`

- Fields:
  - `studentId` (primary key, auto‑generated).
  - `age`, `name`, `country`, `email`, `phoneNumber`.
  - `createdOn`, `updatedOn`.
  - `card` – `@OneToOne` link to a `Card`.
  - `bookList` – `@OneToMany(mappedBy = "student")`: books currently assigned to the student.
  - `transactionList` – `@OneToMany(mappedBy = "student")`: all transactions for this student.
  - `validity` – card/account validity date.

#### 5.3 Author

**Class**: `org.example.entity.Author`

- Fields:
  - `id` (primary key, auto‑generated).
  - `name`, `email` (unique, non‑null), `age`, `country`.
  - `createdOn`, `updatedOn`.
  - `bookList` – `@OneToMany(mappedBy = "author")` (list of books by this author).
  - Uses `@JsonBackReference` to avoid circular JSON serialization with `Book`.

#### 5.4 Card

**Class**: `org.example.entity.Card`

- Fields:
  - `id` (primary key).
  - `student` – `@OneToOne(mappedBy = "card")`: owner student.
  - `cardStatus` – enum `CardStatus` (`ACTIVATED`, `DEACTIVATED`).
  - `email` – contact email.
  - `createdOn`, `updatedOn`.
  - `transactions` – `@OneToMany(mappedBy = "card")`: related transactions.
  - `books` – `@OneToMany(mappedBy = "card")`: books associated with this card.

This models a **library card** concept around the student, used for transactions and possibly for status (active/inactive).

#### 5.5 Transaction

**Class**: `org.example.entity.Transaction`

- Fields:
  - `id` (primary key).
  - `externalTxnId` – public, random UUID used to track the transaction externally.
  - `card` – `@ManyToOne` (currently not set in the existing service logic, but modeled).
  - `book` – `@ManyToOne`.
  - `student` – `@ManyToOne`.
  - `transactionStatus` – `TransactionStatus` (`PENDING`, `SUCCESS`, `FAILED`).
  - `transactionType` – `TransactionType` (`ISSUE`, `RETURN`).
  - `transactionTime` – timestamp when the transaction record is created.
  - `fineAmount` – fine charged upon return (if overdue).
  - `updatedOn`.

#### 5.6 Enums

- `Genre` – defines categories for books (FICTIONAL, NON_FICTIONAL, HISTORY, MATH, etc.).
- `CardStatus` – whether a card is `ACTIVATED` or `DEACTIVATED`.
- `TransactionStatus` – `PENDING`, `SUCCESS`, `FAILED`.
- `TransactionType` – `ISSUE`, `RETURN`.

---

### 6. Repositories (data access)

All repositories extend `JpaRepository`, so they get CRUD functionality for free.

- **BookRepository**
  - `List<Book> findByName(String name)` – search by book name.
  - `List<Book> findByGenre(Genre genre)` – search by enum genre.
  - `assignBookToStudent(int bookId, Student student)` – JPQL `update` to set the `student` field if it is currently `null`.
  - `unassignBook(int bookId)` – JPQL `update` to clear the `student` field.

- **StudentRepository**
  - `Optional<Student> findByEmail(String email)` – used to check existence.

- **AuthorRepository**
  - `Author findByEmail(String email)` – custom `@Query` to find authors by unique email.

- **TransactionRepository**
  - `findTopByStudentAndBookAndTransactionTypeAndTransactionStatusOrderByTransactionTimeDesc(...)`  
    - Returns the last successful **ISSUE** transaction for a given student and book.
    - Used to compute how long a book has been borrowed when returning it.

---

### 7. Service layer – main business logic

#### 7.1 BookService

**Class**: `org.example.service.BookService`

Main responsibilities:
- **Get all books**: `getAllBooks()` → `bookRepository.findAll()`.
- **Assign/unassign books to/from students**:
  - `assignBookToStudent(Book book, Student student)` → updates the `student` field via repository.
  - `unassignBookFromStudent(Book book)` → clears the `student` field.
- **Search books** (`search(SearchBookRequest)`):
  - Validates the request using `SearchBookRequest.validate()` (allowed keys and operators).
  - Switches over `searchKey`:
    - `"bookName"` → `findByName(value)`
    - `"genre"` → `findByGenre(Genre.valueOf(value))`
    - `"id"` → `findById(id)` and wraps in a single‑element list.
  - On unknown key, throws `BookNotFoundException`.
- **Add book** (`addBook(CreateBookRequest)`):
  - Converts DTO to `Book` via `createBookRequest.to()`.
  - Uses `AuthorService.createOrGet` to either fetch existing author (by email) or create a new one.
  - Persists the resulting `Book` via `bookRepository.save`.
- **Delete book** (`deleteTheBook(int id)`):
  - Checks if the book exists; if not, throws `BookNotFoundException`.
  - Deletes by ID and returns the deleted `Book`.

#### 7.2 StudentService

**Class**: `org.example.service.StudentService`

Responsibilities:
- **exists(String email)**:
  - Uses `studentRepository.findByEmail(email)` to check if a student with given email exists.
- **create(CreateStudentRequest)**:
  - Maps DTO to `Student` (`Student.builder()`) with `validity` set to 1 year from now.
  - Saves it via `studentRepository.save`.
- **get(int studentId)**:
  - Fetches student by ID (or returns `null`).
- **getAll()**:
  - Returns all students.
- **updateStudent(Student)**:
  - Loads existing student, throws runtime exception if not found.
  - Updates basic fields (name, age, country, email, phoneNumber, updatedOn).
  - Saves the updated entity.
- **deleteStudent(int id)**:
  - Uses `studentRepository.deleteById`.

#### 7.3 AuthorService

**Class**: `org.example.service.AuthorService`

- **getAllAuthors()**:
  - Returns an `ArrayList` of all authors.
- **createOrGet(Author author)**:
  - Searches an author by email.
  - If exists, returns that author.
  - Otherwise, saves the new author.

#### 7.4 TransactionService – issuing and returning books

**Class**: `org.example.service.TransactionService`

Injected properties:
- `maxBooksForIssuance` from `student.issue.max_books`.
- `numberOfDaysForIssuance` from `student.issue.number_of_days`.

Injected services/repositories:
- `StudentService`, `BookService`, `TransactionRepository`.

##### 7.4.1 Issuing a book

Method: `issueBooks(String bookName, int studentId)`

Logical flow:
1. **Search the book**:
   - Builds a `SearchBookRequest` with `searchKey="bookName"` and `operator="="`.
   - Calls `bookService.search` to find matching books.
   - If search fails or list is empty → throws a generic `Exception("Book not available or not found")`.
2. **Validate student and limits**:
   - Fetches `Student` by `studentId`.
   - If `student.getBookList().size() >= maxBooksForIssuance` → throws `Exception("Book limit Reached")`.
3. **Create transaction**:
   - Takes first matching `Book`.
   - Builds a `Transaction`:
     - `externalTxnId` = random UUID.
     - `transactionType` = `ISSUE`.
     - `book` and `student` set.
     - `transactionStatus` = `PENDING`.
4. **Assign book and finalize transaction**:
   - In `try` block:
     - Set `book.setStudent(student)` and call `bookService.assignBookToStudent`.
     - On success, set `transactionStatus` to `SUCCESS`.
   - In `catch`: set status to `FAILED`.
   - In `finally`: save the transaction and **return its `externalTxnId`**.

> Note: The method wraps many errors as generic `Exception` messages; custom exceptions could be used for more precise error handling.

##### 7.4.2 Returning a book

Method: `returnBook(int bookId, int studentId)`

Logical flow:
1. **Fetch book**:
   - Uses `bookService.search` with key `"id"` to retrieve the `Book`.
   - If search fails, throws `Exception("Not able to fetch the book details.")`.
2. **Validate ownership**:
   - If the book’s `student` is `null` or does not match `studentId`, throws `Exception("Book is not assigned to the student")`.
3. **Create return transaction**:
   - Fetch student by `studentId`.
   - Creates a `Transaction` with:
     - `transactionType` = `RETURN`.
     - `transactionStatus` = `PENDING`.
   - Saves it immediately.
4. **Find original issue transaction**:
   - Uses `transactionRepository.findTopByStudentAndBookAndTransactionTypeAndTransactionStatusOrderByTransactionTimeDesc` to get the most recent successful ISSUE transaction.
5. **Compute fine**:
   - Computes the difference (in days) between:
     - `issueTransaction.transactionTime`
     - `System.currentTimeMillis()`
   - If `timeDifferInDays > numberOfDaysForIssuance`, fine = (days overdue) * `1.0`.
6. **Unassign book and finalize transaction**:
   - In `try`:
     - Clears `book.setStudent(null)` and calls `bookService.unassignBookFromStudent`.
     - Sets `transactionStatus` to `SUCCESS`.
     - Returns the transaction’s `externalTxnId`.
   - In `catch`: sets `transactionStatus` to `FAILED`.
   - In `finally`:
     - Sets `transaction.setFineAmount(fine)`.
     - Saves the transaction again and returns its `externalTxnId`.

This method both records the return transaction and computes/records any applicable fine, based on configured allowed days.

---

### 8. Controller layer – REST API endpoints

Below is a summary of the main endpoints, based on the controller code.

#### 8.1 BookController – `/books`

**Class**: `org.example.controller.BookController`

- `GET /books/search`
  - **Body**: `SearchBookRequest` JSON
    - `searchKey`: `"bookName"`, `"genre"`, or `"id"`.
    - `searchValue`: corresponding value.
    - `operator`: `"="` or other supported operators depending on key.
  - **Behavior**: Delegates to `bookService.search`, returns a list of `Book`.

- `GET /books/getAllBooks`
  - **Behavior**: Returns list of all books (`bookService.getAllBooks()`).

- `POST /books/create`
  - **Body**: `CreateBookRequest` JSON.
  - **Validations**:
    - If request is `null` → `400 BAD_REQUEST`.
  - **Behavior**:
    - Creates/gets the `Author`, then saves the new `Book`.
    - Returns created book with `201 CREATED`.

- `DELETE /books/delete/{id}`
  - **Path variable**: `id` (int).
  - **Validations**:
    - If `id` outside `1–99999999` → `400 BAD_REQUEST`.
  - **Behavior**:
    - Calls `bookService.deleteTheBook(id)`.
    - On success returns `"Book Id deleted successfully"` with `200 OK`.

#### 8.2 AuthorController – `/author`

**Class**: `org.example.controller.AuthorController`

- `GET /author/getAllAuthors`
  - **Behavior**:
    - Fetches list of authors.
    - If `authorsList == null`, returns `404 NOT_FOUND` with `"No authors Present"`.
    - Otherwise returns list of `Author` with `200 OK`.

#### 8.3 StudentController – `/student`

**Class**: `org.example.controller.StudentController`

- `POST /student/create`
  - **Body**: `CreateStudentRequest` JSON.
  - **Behavior**:
    - If body is `null` → `400 BAD_REQUEST`.
    - If student with same contact (treated as email in `StudentService.exists`) already exists → `409 CONFLICT` with `"Student already exists"`.
    - Otherwise creates the student and returns `"student created"` with `200 OK`.

- `GET /student/getStudent/{studentId}`
  - Returns the `Student` with specified ID and `200 OK` (or `null` if not found).

- `GET /student/getAllStudents`
  - Returns a `List<Student>` of all students.

- `PUT /student/update`
  - **Body**: `Student` JSON.
  - **Behavior**:
    - Delegates to `studentService.updateStudent`.
    - Returns `"Student updated"` with `200 OK`.

- `DELETE /student/delete/{id}`
  - Deletes student by ID and returns `204 NO_CONTENT`.

#### 8.4 TransactionController – `/transact`

**Class**: `org.example.controller.TransactionController`

- `POST /transact/issueBook`
  - **Query parameters**:
    - `bookName` – name of the book to issue.
    - `studentId` – ID of the student.
  - **Behavior**:
    - Calls `transactionService.issueBooks(bookName, studentId)`.
    - Returns `"Book has been issued"` with `200 OK` on success.
  - **Errors**: Throws `Exception` or `BookNotFoundException`, which are handled by `ExceptionControllerAdvice`.

- `POST /transact/returnBook`
  - **Query parameters**:
    - `bookId` – numeric ID of the book.
    - `studentId` – ID of the student returning the book.
  - **Behavior**:
    - Calls `transactionService.returnBook(bookId, studentId)`.
    - Returns a simple string (`bookId + " " + studentId + " "`) with `200 OK` on success.

---

### 9. Exception handling strategy

**Central handler**: `ExceptionControllerAdvice`

- Uses `@RestControllerAdvice` and `@ExceptionHandler` methods to customize error responses.
- Handled exception types:
  - `Exception` (catch‑all)
    - Builds an `ErrorInfo` with a generic message from `environment.getProperty("General.EXCEPTION_MESSAGE")`.
    - Returns `500 INTERNAL_SERVER_ERROR`.
  - `TransactionException`
    - Sets message to `"Book is not found"` (despite the name, used as a generic transaction error).
    - Returns `404 NOT_FOUND`.
  - `CardNotFoundException`
    - Sets message to `"Card is not active"`.
    - Returns `404 NOT_FOUND`.

**Custom base exception**: `BaseException`
- Extends `RuntimeException`.
- Adds a structured `errorCode` string.
- Specific exceptions:
  - `BookNotFoundException` → error code `"NO_BOOK_FOUND"`.
  - `CardNotFoundException` → error code `"CARD_NOT_FOUND"`.
  - `TransactionException` → error code `"TRANSACTION_ERROR"`.

**Error response**: `ErrorInfo`
- Fields:
  - `errorMessage`
  - `errorCode` (HTTP status code value)
  - `timeStamp` (`LocalDateTime` when error occurred)

---

### 10. Typical flows

#### 10.1 Creating a book

1. Client sends `POST /books/create` with `CreateBookRequest` JSON:
   - Includes `name`, `genre`, `pages`, `authorName`, `authorCountry`, `authorEmail`.
2. `BookController` validates and forwards to `BookService.addBook`.
3. `CreateBookRequest.to()` builds a `Book` with an embedded `Author` definition.
4. `AuthorService.createOrGet` either reuses an existing author (matched by email) or persists a new one.
5. The `Book` is saved via `BookRepository`, and the created entity is returned.

#### 10.2 Registering a student

1. Client sends `POST /student/create` with `CreateStudentRequest` JSON (name + contact).
2. `StudentController` checks:
   - Request is not null.
   - `StudentService.exists(contact)` is false (no duplicate).
3. `StudentService.create`:
   - Builds a new `Student` with one‑year validity (`validity = now + 31536000000 ms`).
   - Saves and returns it.

#### 10.3 Issuing a book to a student

1. Client calls `POST /transact/issueBook?bookName=...&studentId=...`.
2. `TransactionService.issueBooks`:
   - Searches books with given name.
   - Validates that at least one is available and the student hasn’t exceeded `maxBooksForIssuance`.
   - Creates a `Transaction` with type `ISSUE`, status `PENDING`.
   - Assigns the book to the student and updates DB.
   - On success, marks transaction as `SUCCESS` and persists.
3. Returns an external transaction ID (UUID) to the client.

#### 10.4 Returning a book

1. Client calls `POST /transact/returnBook?bookId=...&studentId=...`.
2. `TransactionService.returnBook`:
   - Fetches the book and validates that it is currently assigned to that student.
   - Creates a `RETURN` transaction.
   - Locates the last successful `ISSUE` transaction to determine borrow duration.
   - Computes fine based on days > `numberOfDaysForIssuance`.
   - Unassigns the book from the student and persists the final transaction with `fineAmount`.

---

### 11. How to run the project locally

1. **Prerequisites**
   - Java 17 installed.
   - Maven installed.
   - PostgreSQL running locally with:
     - Database: `library_project`
     - User: `postgres`
     - Password: `Localhost@123`
   - (Optionally adjust these values in `application.properties`.)

2. **Database setup**
   - Create the database in PostgreSQL:
     - `CREATE DATABASE library_project;`
   - No need to create tables manually; Hibernate will create them because `spring.jpa.hibernate.ddl-auto=create`.

3. **Run the application**
   - From the project root:
     - `mvn spring-boot:run`
   - The app will start on the default Spring Boot port (`8080`) unless overridden.

4. **Test some endpoints (examples)**
   - Create a student:
     - `POST http://localhost:8080/student/create`
   - Create a book:
     - `POST http://localhost:8080/books/create`
   - Issue a book:
     - `POST http://localhost:8080/transact/issueBook?bookName=SomeTitle&studentId=1`
   - Return a book:
     - `POST http://localhost:8080/transact/returnBook?bookId=1&studentId=1`

---

### 12. Possible improvements and notes

This section is not required to run the project but helps you understand current limitations and future directions:

- **Validation and error messaging**
  - Use more specific exceptions instead of generic `Exception` in services.
  - Leverage bean validation (`@Valid`) on controller method parameters and DTOs.

- **Status and availability**
  - The `Book.available` field is not fully used in issue/return logic yet; it could be aligned with `student` assignment.
  - `Card` is present in the model but not fully wired into `TransactionService`; future improvements might enforce `CardStatus` checks when issuing books.

- **Configuration**
  - `spring.jpa.hibernate.ddl-auto=create` is destructive; for production you’d likely use `update`, `validate`, or migrations (Flyway/Liquibase).
  - Externalize secrets (`username`, `password`) instead of hard‑coding in `application.properties`.

Overall, the project is a **clean, layered Spring Boot application** that demonstrates common patterns (REST controllers, DTOs, JPA entities, repositories, and service‑layer business logic) around a library domain: books, students, authors, and borrowing/returning with fines.

