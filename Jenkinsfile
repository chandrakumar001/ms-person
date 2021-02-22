pipeline {
  agent any 
    stages {
        // Build
        stage('Build') {
            steps {
                // sh mvn clean install
                bat 'mvn clean compile'
            }
        }
        // Build
        stage('Build and Test') {
            steps {
                // sh mvn clean install
                bat 'mvn test'
            }
            post{
                junit "**/target/surefire-reports/TEST-*.xml"
            }
        }
        // Deploy
        stage('Deploy') {
            steps {
               bat 'mvn clean install'
            }
        }
        //end
    }
  }
