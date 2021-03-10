# ms-person

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Application URL Swagger](#Application-URL-Swagger)
* [Cloudfoundry](#Cloudfoundry)
* [Kubernetes](#Kubernetes)
* [Additional Information-Optional](#Additional-Information-Optional)

## General info
This project for Person microservice and used lasted version spring boot, swagger(OpenAPI),Code Generator swagger.

#### Technologies

 * Java: 11
 * Spring boot/data jpa/Swagger(OpenAPI)
 * In-memory database: H2 database
 * Groovy-Spock(Junit test)
 * Karate framework(Integration-test)
 * Maven 3.6.3
 * CI/CD-Jekins Pipeline

#### Project Structure
    src
      integration-test
      main
      test
   
## Setup

#### Clone the project
   
   
        git clone https://github.com/chandrakumar001/ms-person

#### Running Tests

  You can run tests by invoking the following command. 

        cd ms-person
        mvn clean install
        mvn verify
    
#### Packaging
        
  You can package by invoking the following command. 
        
        mvn package -Dmaven.test.skip=true

## Application-URL-Swagger:
  Perform all operation like create, update,delete and show all list.
  
  Local Swagger:
  <http://localhost:8080/swagger-ui.html>
   
  IBM cloud:
   <https://ms-person.mybluemix.net/swagger-ui.html>

    
    Sample Output: please refer output folder

#### Database:

 To Store the data into table, I used `person_id` primary key as UUID format
 after create any object,if would you like `update/delete/getById` we should pass primary key
  
 
 To identity the object is unique, I used `email_id` as candidate key
 
In Order to track object,I Implemented Audit model such `createdBy,creationDate,lastModifiedBy,lastModifiedDate and action(created,deleted,updated)`

##### Mock database data scripts:

    src/main/resources/data.sql
    src/main/resources/schema.sql

##### In-memory-Database URL:


https://ms-person.mybluemix.net/h2-console/
    
    url: jdbc:h2:mem:testdb
    username: sa
    password: <empty>
      
POST: https://ms-person.mybluemix.net/v1/people

please change email id

    {
      "emailId": "test@gmail.com",
      "personName": {
        "firstName": "test",
        "lastName": "k"
      },
      "age": "19",
      "favouriteColour": "blue"
    }

output:
    
    {
      "personId": "9ff7233a-9c63-4dbd-ab33-0d539aae0905",
      "data": {
        "emailId": "test@gmail.com",
        "personName": {
          "firstName": "test",
          "lastName": "k"
        },
        "age": "19",
        "favouriteColour": "blue"
      }
    }

GET   https://ms-person.mybluemix.net/v1/people/9ff7233a-9c63-4dbd-ab33-0d539aae0905
   
    {
     "personId": "9ff7233a-9c63-4dbd-ab33-0d539aae0905",
     "data": {
       "emailId": "test@gmail.com",
       "personName": {
         "firstName": "test",
         "lastName": "k"
       },
       "age": "19",
       "favouriteColour": "blue"
     }
    }
  
PUT  https://ms-person.mybluemix.net/v1/people/9ff7233a-9c63-4dbd-ab33-0d539aae0905

    {
      "emailId": "test@gmail.com",
        "personName": {
          "firstName": "test1",
          "lastName": "k"
        },
        "age": "19",
        "favouriteColour": "red"
    }
 
 output:
    
    {
      "personId": "9ff7233a-9c63-4dbd-ab33-0d539aae0905",
      "data": {
        "emailId": "test@gmail.com",
        "personName": {
          "firstName": "test1",
          "lastName": "k"
        },
        "age": "19",
        "favouriteColour": "red"
      }
    }

Delete: https://ms-person.mybluemix.net/v1/people/9ff7233a-9c63-4dbd-ab33-0d539aae0905
     
     refer output folder :: "delete-person.PNG"

GET  https://ms-person.mybluemix.net/v1/people
    show all list of person
    
    refer output folder :: "get-person.PNG"
    
#### Error Message
    
    src/main/resources/messages.properties
        
    
## Cloudfoundry

    ibmcloud login -a https://cloud.ibm.com -u passcode -p <passcode>
    ibmcloud target --cf
    ibmcloud cf push  -f cloudfoundry/manifest.yml  --vars-file cloudfoundry/dev-vars.yml

## Kubernetes
    
  At first time, application setup execture: 'kubectl-execute.bat'
  
  This bat file having all configuration and apply the kubernetes cluster, this will step the such like <b>deployment,service,ingress,HPA,VPA and network policy</b>
   
<b>This application will be deploy kubernetes cluster via CI/CD pipleline</b>  

refer: output folder

         kubernate-pods.png


<b>Unit-Test case report:</b>

    refer: output/unitest-case-report.PNG
    
<b>Integration-Test case report: </b>

         refer: output/cucumber-report-it-test.PNG

     
## Additional-Information-Optional
#### Jenkins CD/CD pipeline step

 - [x] A completed task Locally Download and install jenkins 
 - [x] A completed task Access the  below endpoint
 
     http://localhost:9090/
     
refer: output folder
     
     cicd-pipeline.png
     
Install Jenkins Plugin:

       `Cucumber reports
        Github integration plugin'
        
####   SonarQube:
    
   Code quality for application
    
        http://localhost:9000/projects
    
##### Application Name:

     ms --->    means Microservice
     person --> Application Name
     Example: ms-person     
  
  
##### Spring data JPA:

Soft Delete: 
    
    It means that you are flagging a record as deleted in a particular table, instead of actually being deleting the record. 
    
Hard Delete: 
    
    It means you are completely removing the record from the table        

##### Application Scaling:

Scaling Horizontally:

Incoming requests to your application are automatically load balanced across all instances of your application, and each instance handles tasks in parallel with every other instance. 

    ibmcloud cf scale ms-person -i 2

Scaling Vertically:

Vertically scaling an application changes the disk space limit or memory limit that Cloud Foundry applies to all instances of the application


-k DISK to change the disk space limit applied to all instances of your application

-m MEMORY must be an integer followed by either an M, for megabytes, or G, for gigabytes


    ibmcloud cf scale ms-person -k 512M
    ibmcloud cf scale ms-person -m 1G
    
Show all apps:
        
        ibmcloud cf ms-person
        ibmcloud cf logs ms-person
        
##### Request tracking and logging by using below component 

logging: Clean up activity for before request and after request by using mdc approach 

Tracking: Will be hold all request meta data such as jwt,x-request and x-businessTxId and externalId
 
 - [x] X-BusinessTx-ID: meaning perform current execution for request
 - [x] X-Request-ID: where the request is originated
 - [x] app-name: application name
 
Components | ORDER
-----------| -------------
logging    | 0
Tracking   | 1

Example:

    2021-03-10 13:28:05.175  INFO [ app-name=ms-person, X-BusinessTx-ID=6f17d90c-3964-4e07-8b7b-638b7eb69aec, X-Request-ID=1c0386cf-dfa7-4260-b6a8-1273d26aa57b ] ::: [080-exec-1] c.c.m.a.p.s.DefaultPersonQueryService     : called getAllPerson begin
    Hibernate: select person0_.person_id as person_i1_0_, person0_.created_by as created_2_0_, person0_.creation_date as creation3_0_, person0_.last_modified_by as last_mod4_0_, person0_.last_modified_date as last_mod5_0_, person0_.action as action6_0_, person0_.age as age7_0_, person0_.email_id as email_id8_0_, person0_.favourite_colour as favourit9_0_, person0_.first_name as first_n10_0_, person0_.last_name as last_na11_0_ from person.person person0_ where person0_.action<>'DELETED' limit ? offset ?
    Hibernate: select count(person0_.person_id) as col_0_0_ from person.person person0_ where person0_.action<>'DELETED'
    2021-03-10 13:28:05.523  INFO [ app-name=ms-person, X-BusinessTx-ID=6f17d90c-3964-4e07-8b7b-638b7eb69aec, X-Request-ID=1c0386cf-dfa7-4260-b6a8-1273d26aa57b ] ::: [080-exec-1] c.c.m.a.p.s.DefaultPersonQueryService     : called getAllPerson end


##### Enable  OWASP’s for this application
 
In the pom.xml file, just the remove the comment block


                    <plugin>
                           <groupId>org.owasp</groupId>
                           <artifactId>dependency-check-maven</artifactId>
                           <version>6.1.1</version>
                           <executions>
                               <execution>
                                   <goals>
                                       <goal>check</goal>
                                   </goals>
                               </execution>
                           </executions>
                       </plugin>
                       
##### Enable Sonarqube for this application

                        <plugin>
                           <groupId>org.sonarsource.scanner.maven</groupId>
                           <artifactId>sonar-maven-plugin</artifactId>
                           <version>3.3.0.603</version>
                           <executions>
                               <execution>
                                   <phase>verify</phase>
                                   <goals>
                                       <goal>sonar</goal>
                                   </goals>
                               </execution>
                           </executions>
                       </plugin>             
                
Refer URL: 
    
    https://springbootdev.com/2018/03/13/spring-data-jpa-auditing-with-createdby-createddate-lastmodifiedby-and-lastmodifieddate/
    https://piotrminkowski.com/2020/02/20/microservices-api-documentation-with-springdoc-openapi/
    https://docs.run.pivotal.io/appsman-services/autoscaler/using-autoscaler-cli.html
    