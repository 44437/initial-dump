```.
|-- db
|   |-- run-mysql-for-local.sh
|   `-- users.sql
|-- src
|   |-- main
|   |   |-- java
|   |   |   `-- com
|   |   |       `-- u44437
|   |   |           `-- initial_dump
|   |   |               |-- config
|   |   |               |   |-- CaffeineConfiguration.java
|   |   |               |   |-- EnvConfiguration.java
|   |   |               |   |-- EnvSystem.java
|   |   |               |   `-- UsersConfiguration.java
|   |   |               |-- controller
|   |   |               |   |-- ResponseMap.java
|   |   |               |   `-- UsersController.java
|   |   |               |-- error
|   |   |               |   `-- users
|   |   |               |       `-- UserNotFound.java
|   |   |               |-- model
|   |   |               |   `-- users
|   |   |               |       |-- UserDB.java
|   |   |               |       |-- UserReq.java
|   |   |               |       `-- UserRes.java
|   |   |               |-- repository
|   |   |               |   |-- UsersDao.java
|   |   |               |   `-- UsersRepository.java
|   |   |               |-- service
|   |   |               |   |-- UsersService.java
|   |   |               |   `-- UsersServiceImpl.java
|   |   |               `-- InitialDumpApplication.java
|   |   `-- resources
|   |       |-- application-dev.properties
|   |       |-- application-prod.properties
|   |       |-- application.properties
|   |       `-- logback.xml
|   `-- test
|       `-- java
|           `-- com
|               `-- u44437
|                   `-- initial_dump
|                       |-- controller
|                       |   |-- ResponseMapTests.java
|                       |   `-- UsersControllerTests.java
|                       |-- repository
|                       |   `-- UsersRepositoryTests.java
|                       |-- service
|                       |   `-- UsersServiceTests.java
|                       `-- InitialDumpApplicationTests.java
|-- Dockerfile
|-- Makefile
|-- README.md
|-- file-structure.md
|-- mvnw
|-- mvnw.cmd
`-- pom.xml
```
27 directories, 37 files
