# 🎓 GCETTS – College Teacher & Student Management System

## 🚀 Overview

GCETTS (Government College of Engineering & Textile Technology – System) is a **college management web application fully designed and developed by Soumili Samanta** to manage students, teachers, academic records, and internal operations in a centralized and secure way.

The system helps institutions to:

- Manage **Student & Teacher profiles**
- Maintain **academic records, marks, and results**
- Provide **role-based login** (Admin / Teacher / Student)
- Allow teachers to **upload marks** securely
- Allow students to **view profiles & results**
- Simplify college administration through a web-based dashboard

---

## 🛠️ Built With

- **Backend:** Spring Boot (Java)
- **Frontend:** HTML, CSS, Thymeleaf
- **Database:** MySQL
- **ORM:** Spring Data JPA / Hibernate
- **Security:** Spring Security (Login & Role-based Access)
- **Build Tool:** Maven
- **Server:** Embedded Tomcat

---

## 📂 Project Structure

GCETTS_PROJECT/
│── src/main/java/
│ └── com/gcetts/
│ ├── controller/ # Controllers (Student, Teacher, Admin)
│ ├── service/ # Business logic
│ ├── repository/ # JPA Repositories
│ ├── entity/ # Entity classes (Student, Teacher, Result)
│ └── GcettsApplication.java
│
│── src/main/resources/
│ ├── templates/ # Thymeleaf HTML files
│ ├── static/ # CSS, JS, Images
│ └── application.properties
│
│── pom.xml # Maven dependencies
│── README.md # Project documentation

---

## ⚙️ Features

- ✅ Student Registration & Login  
- ✅ Teacher Login & Dashboard  
- ✅ Admin Management Panel  
- ✅ Marks Upload & Result Generation  
- ✅ Profile View (Student / Teacher)  
- ✅ Role-based Authentication  
- ✅ Secure Database Integration (MySQL)

---

## 🔑 User Roles

### 👨‍🎓 Student
- Login securely
- View profile details
- View academic results

### 👩‍🏫 Teacher
- Login securely
- Upload student marks
- View assigned students

### 🧑‍💼 Admin
- Manage students & teachers
- Maintain system data

---

## 📸 Application Workflow

1️⃣ Home Page  
2️⃣ Login Page (Student / Teacher / Admin)  
3️⃣ Dashboard (Role-based)  
4️⃣ Marks Entry (Teacher)  
5️⃣ Result View (Student)

---
### 👩‍💻 Project Author

Soumili Samanta – Sole Developer & Project Owner

This project has been entirely designed, developed, and implemented by Soumili Samanta as part of an academic and learning-focused initiative.

Future contributors are welcome 🚀

## 📌 Installation & Setup

### 🔹 Prerequisites

- Java 17+
- Maven
- MySQL
- IDE (IntelliJ / Eclipse / VS Code/ STS)
### 🔹 Backend Setup (Spring Boot)

```bash
 Clone the repository
git clone https://github.com/Soumili0/GCETTS_PROJECT.git

# Open project directory
cd GCETTS_PROJECT

# Build project
mvn clean install

# Run application
mvn spring-boot:run
---
Backend runs at: http://localhost:8080
spring.datasource.url=jdbc:mysql://localhost:3306/gcetts_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
mvn test
[ User ] ⇆ [ Thymeleaf Frontend ] ⇆ [ Spring Boot Backend ] ⇆ [ MySQL Database ]

