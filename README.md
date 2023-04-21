# Book-My-Show-Backend_Application
- Book My Show is a movie ticket booking system project
- Its a backend code written using Spring-Boot
- It consists of Different Layers like Controller, Service, Repository , DTO(Data Transfer Object)
- These layers in the project increases its code maintanablity, understadability and readablity

# Design of the Application
![login](https://github.com/Akash-Hajare/Book-My-Show-Backend_Application/blob/master/Screenshots/Design-1.png)
![login](https://github.com/Akash-Hajare/Book-My-Show-Backend_Application/blob/master/Screenshots/Design-2.png)
## SCHEMA
![login](https://github.com/Akash-Hajare/Book-My-Show-Backend_Application/blob/master/Screenshots/Schema.jpg)

## API's
![login](https://github.com/Akash-Hajare/Book-My-Show-Backend_Application/blob/master/Screenshots/Apis-1.png)
![login](https://github.com/Akash-Hajare/Book-My-Show-Backend_Application/blob/master/Screenshots/Apis-2.png)

## Assumptions
### For the simplicity of system, I have made following assumptions while implementing the solution -
- Single User Model - One user will use at once. No locking implemented for seat selection.
- Single Screen Theaters - No multiple screen handling for a theater has been done. However an option is given for future scope.
- No Payment flow used.

## Setup the Application
- Create a database bookmyshow using the sql file bookmyshow.sql provided in src/main/resources.
- Open src/main/resources/application.properties and change spring.datasource.username and spring.datasource.password properties as per your MySQL installation.
- Type mvn spring-boot:run from the root directory of the project to run the application.

## Verifying the results from DB
### Login to your MySQL and go to bookmyshow DB
- SELECT * FROM bookmyshow.users; to see all registered users.
- SELECT * FROM bookmyshow.movies; to see all movies.
- SELECT * FROM bookmyshow.theaters; to see all theaters.
- SELECT * FROM bookmyshow.theater_seats; to see all theater's seats.
- SELECT * FROM bookmyshow.shows; to see all shows for movies in theaters.
- SELECT * FROM bookmyshow.show_seats; to see all show's seats by type.
- SELECT * FROM bookmyshow.tickets; to see all booked tickets.
