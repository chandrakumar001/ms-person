pipeline {
  agent any 
    stages {
        // Build
        stage('Build and Test') {
            steps {
                // sh mvn clean install
                bat 'mvn clean install'
            }
        }
        // Deploy
        stage('Deploy') {
            steps {
               echo "Running the Deploy..."
            }
        }
        //end
    }
  }
