# 💼 Project Manager Backend

**Project Manager** is a java spring boot backend project that essentially represents the server-side API for a social, team based kanban-style system for task management.

### Key Features
- **User authentication**: As a user you can provide credentials to register a new account as well as log in to an existing one.

- **Project creation**: Every user can create projects by providing information about the project scope, name, description, tags etc.

- **Collaboration requests**: Project team members (creator at first) can generate a project collaboration request code. Other users can redeem that code in order to accept the invitation and become a part of the project team.

- **Ticket creation**: Every project team member can create, assign, edit and delete project tickets. Each ticket represents a task, having its own deadline, tags, progress, importance, assignee and so on.

- **Ticket comments**: Each ticket has its own comment section. Every team member can post comments to let other people know what is on their mind regarding the ticket.

- **Discussions**: Each project has its own discussion attatched to it. Every team member can send messages to let other people know what is going on or ask questions.
<br><br>
### System Entity–relationship model:

<img width="1421" height="849" alt="Project Manager ERD" src="https://github.com/user-attachments/assets/deaea178-fdf3-4727-802d-e31ed44db515" />

### API Endpoints:

<img width="1418" height="847" alt="Postman Endpoints" src="https://github.com/user-attachments/assets/98c0c844-5e78-473c-a57a-7d8059034335" />

### Example project page wireframe:
<img width="1409" height="837" alt="Main Page (Wireframe)" src="https://github.com/user-attachments/assets/3f3eb431-cc85-4ffd-91c8-d71e10c8db67" />


### Example ticket page wireframe:
<img width="1307" height="781" alt="Ticket Page (Wireframe)" src="https://github.com/user-attachments/assets/c34838fb-2585-4d0d-a1e2-739d19cb7259" />

## Project Local Setup
To get started with the project locally, follow the instructions below. This will guide you through setting up the development environment and running the application on your machine.

### Prerequisites
Before setting up the project, ensure you have the following installed on your machine:

- [Java](https://www.oracle.com/java/technologies/downloads/) (>= JDK 23)
- [Maven](https://maven.apache.org/download.cgi) (>= v3.9.9)
- [MySQL](https://dev.mysql.com/downloads/installer/)

### 1. Clone the Repository

Start by cloning the repository to your local machine:

```cmd
git clone https://github.com/vasilev17/project-management-backend.git
cd project-name
```

### 2. Install Dependencies

Install the dependencies with:

```cmd
mvn clean install
```

### 3. Configure Applicaiton Properties
Create a new file named `application-dev.properties` inside the `/src/main/resources` directory. Afterwards provide appropriate values for the **SQL** and **JWT** configuration properties:

```properties
spring.datasource.username=<mySqlUser>
spring.datasource.password=<sqlPassword>
jwt.secret=<JwtSecretKey>
```
(Example JWT Secret for testing is provided in the `application.properties` file)

<br>

>Another way would be to remove the development environment property from `application.properties` and directly set the values there:
>```properties
>spring.profiles.active=dev
>```


### 4. Create Database
Create a new database named `project_manager`:

```sql
CREATE DATABASE project_manager;
```

## API Testing
One option is to use [OpenAPI Swagger](https://springdoc.org/) available at:
```
localhost:8082/swagger-ui/index.html
```

Alternatively you can use [Postman](https://www.postman.com/downloads/) by downloading and importing the provided [Postman Collection](docs/postman/ProjectManager.postman_collection.json)


