pipeline {
  agent any 
    stages {
        stage('Code build') {
          steps {            
               sh "mvn --settings ms_settings.xml clean install -Dmaven.test.skip=true"
            }
        }
        stage('SonarQube analysis') {

           steps ('SonarQube analysis') {
               withSonarQubeEnv('sonarqube-server') {
                   sh 'mvn sonar:sonar'
               }
           }
        }
        stage('Cucumber Reports') {
                  steps{
                     cucumber buildStatus: "UNSTABLE",
                     fileIncludePattern: "**/feature.*.*.json",
                     jsonReportDirectory: 'target'
                  }
           }
        stage ('Deploy'){

            print "hello"
        }
    }
  }
