Alternative rest api for cities from geoplaces org, based on publicly available txt files.

Start with:
Local:
 mvn spring-boot:run
Docker local:
 mvn package
 docker build -t sgeo:latest .
 docker run -p 8080:8080 sgeo:latest

See api documentation on
 http://localhost:8080/v1/geo/swagger-ui.html

System requirements:
 Should work on most systems with jdk 8 and maven 3.6.1 with internet access.
Tested on
 1. ubuntu 19.10, jdk 1.8.0_241/hotspot, maven 3.6.1
 2. ubuntu 16.04, jdk 1.8.0_251/ibm j9 vm, maven 3.6.3