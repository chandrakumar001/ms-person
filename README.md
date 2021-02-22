# ms-person


## Programming Language
   1.Java
   
           

    Go to the Github repo -> Settings -> Webhooks
    http://<public-url>/github-webhook
    
    
    mvn install -DskipTests=true
    mvn package -Dmaven.test.skip=true
    
    Cloudfoundry:
    -------------
    ibmcloud login -a https://cloud.ibm.com -u passcode -p <passcode>
    ibmcloud target --cf
    ibmcloud cf push  -f cloudfoundry/manifest.yml  --vars-file cloudfoundry/dev-vars.yml
    
    Jenkins:    
    http://localhost:9090/
    
    Jenkins Plugin: Cucumber reports,Github integration plugin
    
    SonarQube:
    
    http://localhost:9000/projects
    
    Build Triggers-->Poll SCM
    
    Next click the option to Add post-build option and choose the option of “Publish Junit test result report”


Attempt | #1 | #2 | #3 | #4 | #5 | #6 | #7 | #8 | #9 | #10 | #11
--- | --- | --- | --- |--- |--- |--- |--- |--- |--- |--- |---
Seconds | 301 | 283 | 290 | 286 | 289 | 285 | 287 | 287 | 272 | 276 | 269

Soft Delete: 
    
    It means that you are flagging a record as deleted in a particular table, instead of actually being deleting the record. 
    
Hard Delete: 
    
    It means you are completely removing the record from the table        
    
refer: 
    
    https://springbootdev.com/2018/03/13/spring-data-jpa-auditing-with-createdby-createddate-lastmodifiedby-and-lastmodifieddate/

Application URL(Swagger):
  
    Local
    http://localhost:8080/swagger-ui.html
    
    IBM cloud
    https://ms-person.mybluemix.net/swagger-ui.html
     
    