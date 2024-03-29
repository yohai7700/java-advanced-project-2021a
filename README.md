# Advanced Programming Workshop on Java
## Team
* Yohai Mazuz
* Dvir Dov
* Ofek Marks

## Installation
In order to run the application, please use an Apache Tomcat application server.
The database used in the application is a PostgreSQL server which must be run on the server that runs the application on port 5433, the PostgreSQL server needs to be configured with a database named `workshop` and a user with the credentials (for development purposes):
* username: `postgres`
* password: `password`

The application uses external libraries using Maven, resolve them by the `pom.xml` file.

|                  |Software			   |Version                      |
|------------------|-------------------------------|-----------------------------|
|Application Server|Apache Tomcat                  |9.0.41+ 	                 |
|Database	       |PostgreSQL                     |13.2+                        |

> Note: you can use a different application server such as GlassFish 4, but the application was tested on Tomcat only so please Tomcat.
> A different SQL Server can be used instead of PostgreSQL as long as the matching driver is configured and the database and login credentials are configured properly as described above.

## Links
* [PostresSQL](https://www.postgresql.org/)
* [Apache Tomcat](http://tomcat.apache.org/)
