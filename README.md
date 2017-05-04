# pushb-example

Program to call the pushbullet API and send a push notification using Spring Boot.

How to run

Install (and run JUnit tests) : mvn install
Run application: java -jar /target/pushb-example-0.0.1-SNAPSHOT.jar

To register a new user, call POST : localhost:8080/users with Json :
{
	"userName" : "user007",
	"accessToken" : "token007"
}
and header: Content-Type:application/json

To retrieve all registered users, call GET : localhost:8080/users

To retrieve a single registered user by username, call GET : localhost:8080/users/userName

NOTE: This should be documented by Swagger.

How to pushbullet API to send a notification
Examples can be found in src/test/PushBulletTest
