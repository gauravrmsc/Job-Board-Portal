# Job Board Portal

Job Board Portal is a web application that connects job seekers and Recruiters. This Spring Boot project will allow Job Recruiters to post jobs and will also allow job seekers to view jobs posted on the website


## Getting Started

### Dependencies

* [Java SE Development Kit 8+](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Gradel](https://gradle.org/)
* [MySQL Server 8](https://dev.mysql.com/downloads/mysql/) (or another standalone SQL instance)


Part of this project involves configuring a Spring application to connect to an external data source. Before beginning this project, you must install a database to connect to. Here are [instructions for installing MySQL 8](https://dev.mysql.com/doc/refman/8.0/en/installing.html).

After installing the Server, you will need to create a user that your application will use to perform operations on the server. You should create a user that has all permissions on localhost using the sql command found [here](https://dev.mysql.com/doc/refman/8.0/en/creating-accounts.html).


### Installation

1. Clone or download this repository.
2. Create database and user for the application in mysql
3. Configure Database Name, User Name and Password in application.properties file present in src -> main -> resources folder  
4. From the Project root folder run ./gradlew test to run the test cases
5. From the Project root folder run ./gradlew bootRun to run the Server. The server will start at 8082 port (Port configuration can be changed from application.properties file)
6. Open a browser and navigate to the url: [http://localhost:8082](http://localhost:8082)

You should see the application home page in your browser.

## URL of the application
1. Post a job: [http://localhost:8082/post-job.html](http://localhost:8082/post-job.html)
2. Search for a job: [http://localhost:8082/post-job.html](http://localhost:8082/post-job.html)

## Application Structure
####1. Java Files
src -> main -> java
####2. Test Files
src -> test -> java
####3. Properties File
src -> main -> resources -> application.properties
####4. Html Filessrc -> main -> resources -> application.properties


src -> main -> resources -> static
