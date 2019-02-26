# saveBookSpringBoot

Description:

A spring boot application to store book categories and books using spring data jpa

How to Run it:

1. mkdir app
2. cd app

3. gradle wrapper

4. git-clone or download the project

//in another terminal run the postgress database image (need for the tests)

5. sudo docker run -p 5432:5432 -e POSTGRES_DB=mydb -e POSTGRES_PASSWORD=mysecretpassword  postgres

6. ./gradlew build

7. stop the image running the postgress db

8. sudo docker-compose up --build
