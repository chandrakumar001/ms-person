# ms-person


    
    mvn install -DskipTests=true
    
    ibmcloud cf push  -f cloudfoundry/manifest.yml  --vars-file cloudfoundry/dev-vars.yml
    
    Jenkins
    
    http://localhost:9090/
    
    Jenkins Plugin: Cucumber reports
    
    SonarQube:
    
    http://localhost:9000/projects
    
    Build Triggers-->Poll SCM
    
    Next click the option to Add post-build option and choose the option of “Publish Junit test result report”

    
    
