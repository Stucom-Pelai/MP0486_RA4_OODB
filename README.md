# Java CRUD with ObjectDB (OODB)
This is a simple **Java CRUD application using ObjectDB (Object-Oriented Database)** and **JPA**.

To run this project you need:

- Java JDK (17 or compatible)
- Maven
- ObjectDB (for database visualization with Object Explorer)

> No database server is required. ObjectDB works as an **embedded database** using `.odb` files.

---

## About the project
The project demonstrates the **four basic CRUD operations** using ObjectDB:

- **Create**: Persist new objects into the database  
- **Read**: Retrieve objects and perform queries  
- **Update**: Modify stored objects using JPQL  
- **Delete**: Remove objects from the database  


---

## Database location
The ObjectDB database is stored as a file inside the project folder objects/points.odb

## Viewing the database 
To inspect the database visually:
0. Install objectdb-2.9.2 https://www.objectdb.com/download/2.9.2
1. Open **ObjectDB Object Explorer** from folder objectdb-2.9.2\bin>java -jar explorer-2.9.2.jar
2. Select `File → Open`
3. Open the file:
<img width="800" height="441" alt="image" src="https://github.com/user-attachments/assets/224a9738-1a23-4bb6-9043-397604b7a02c" />
