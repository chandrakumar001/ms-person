pipeline {
  agent any 
    stages {
        stage('Build') {
            steps {
                // sh mvn clean install
                bat 'mvn clean install'
            }
        }
        stage('Test') {
            parallel {
                stage('Unit Test') {
                    steps {
                      echo "Running the unit test..."
                    }
                 }
                stage('Integration test') {
                    steps {
                      echo "Running the unit test..."
                    }
                 }
            }
        }
    }
  }
