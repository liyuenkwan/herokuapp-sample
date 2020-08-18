## Deploy the application
1. run `mvn package`
2. copy the herokuapp-sample-api-server.jar to a folder
3. cd to the folder
4. run `java -jar herokuapp-sample-api-server.jar`

## Scale the system / Highly available 
To scale the system, we can make it into a docker image. Then we can deploy the application to multiple servers.
We can then place a load balancer / API Gateway in front of these applications, and we can call the application with a single domain name.

Another way to scale the system is to use Kubernetes. It makes us easy to control how many instances we want. And similiar to the previous method, we can use portType or load balancer to make our application be called by single domain name. 

## Protect it
We can protect our application by spring-security. We can simply use username-password model. Or we can apply oauth, ldap, or other mechanism with spring-security easily.